package com.example.idleman;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Ing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Ing extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<TaskItem> list = new ArrayList<>();
    private int space;
    //要展示的对应item的数据，imgs是上方的图片/视图
    //titles是标题，headsIcon是头像，username是用户名
    private int[] imgs;
    private String[] titles;
    private int[] headsIcon;
    public static String[] taskText = new String[1000];
    public static Long[] taskID = new Long[1000];
    public static String[] usernames = new String[1000];
    public static String[] taskTag = new String[1000];
    public static int taskCount;
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            HomeAdapter homeAdapter = new HomeAdapter(getActivity(), list);//创建适配器对象
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));//设置为表格布局，列数为2（这个是最主要的，就是这个来展示陈列式布局）
            recyclerView.addItemDecoration(new space_item(space));//给recycleView添加item的间距
            recyclerView.setAdapter(homeAdapter);//将适配器添加到recyclerView
            return false;
        }
    });

    public Fragment_Ing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Fragment_Message.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Ing newInstance(String param1) {
        Fragment_Ing fragment = new Fragment_Ing();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__ing, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.ing_item);//创建recyclerView对象，并初始化其xml文件

        //设置RecyclerView保持固定大小,这样可以优化RecyclerView的性能
        recyclerView.setHasFixedSize(true);

        initData();
        //System.out.println(list.size()+"1234567");
        return view;
    }

    //弹窗处理
    Handler handler1 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            new AlertDialog.Builder(
                     getActivity())
                    .setMessage(msg.getData().getString("key"))
                    .setPositiveButton("确定", null)
                    .show();
            return false;
        }
    });


    private void initData() {
        //this.list=TaskFactory.createItem();
        String url = "http://1.117.239.54:8080/task?operation=getByTaskState&index="+Data.getId()+"&key=";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.getTargetData(url);
                JSONObject temp = JSON.parseObject(result);//结果转化为json对象
                String judge = temp.getJSONObject("meta").getString("status");//获取状态码
                if(!judge.equals("2011"))//没有东西
                {
                    //创建Message
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","你暂无正在进行中的发布任务");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler1.sendMessage(msg);
                    return;
                }

                JSONArray array = temp.getJSONArray("data");
                taskCount = array.size();
                for (int i = 0; i < taskCount; i++) {

                    int id = 1;
                    Log.d("task id", "task is NO." + id);
                    taskText[i] = JSON.parseObject(array.getString(i)).getString("taskTitle");
                    taskID[i] = JSON.parseObject(array.getString(i)).getLong("taskID");
                    usernames[i] = JSON.parseObject(array.getString(i)).getString("username");
                    taskTag[i] = JSON.parseObject(array.getString(i)).getString("tag");
                }
                String data = temp.getJSONArray("data").getString(0);//数组第一个元素的字符串值
                JSONObject show = JSON.parseObject(data);//转化为json对象
                list = new ArrayList<>();
                for (int i = 0; i < taskCount; i++) {
                    String text = taskText[i];
                    Long id = taskID[i];
                    String username = usernames[i];
                    String tag = taskTag[i];
                    list.add(new TaskItem(text, id, username, tag));
                }
                Message msg = Message.obtain();
                handler.sendMessage(msg);
            }
        }).start();
    }

    class space_item extends RecyclerView.ItemDecoration {
        //设置item的间距
        private int space = 5;

        public space_item(int space) {
            this.space = space;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
            outRect.top = space;
        }
    }
}
