package com.example.idleman;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
//测试适配器
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fragment_Home extends Fragment {

    private List<TaskItem> list=new ArrayList<>();
    private int space;
    //要展示的对应item的数据，imgs是上方的图片/视图
    //titles是标题，headsIcon是头像，username是用户名
    private int[] imgs;
    private String[] titles;
    private int[] headsIcon;
    public static String[] taskText = new String[1000];
    public static Long[] taskID = new Long[1000];
    public static String[] usernames=new String[1000];
    public static String[] taskTag=new String[1000];
    public static int taskCount;
    RecyclerView recyclerView;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            HomeAdapter homeAdapter=new HomeAdapter(getActivity(), list);//创建适配器对象
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));//设置为表格布局，列数为2（这个是最主要的，就是这个来展示陈列式布局）
            recyclerView.addItemDecoration(new space_item(space));//给recycleView添加item的间距
            homeAdapter.setOnItemClickListener(
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position, ClipData.Item item) {
                            Intent intent = new Intent(requireContext(), Activity_Task_Publish.class);
                            startActivity(intent);
                        }


            });
            recyclerView.setAdapter(homeAdapter);//将适配器添加到recyclerView
            return false;
        }
    });

    public Fragment_Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        //欢迎语随机
        TextView welcomeText=(TextView)view.findViewById(R.id.home_welcometext) ;
        Random random=new Random();
        String text = null;
        int i=random.nextInt(7);
        switch (i){
            case 0:text="满堂花醉三千客，一剑霜寒十四州。";break;
            case 1:text="此身合是诗人未？细雨骑驴入剑门。";break;
            case 2:text="天下风云出我辈，一入江湖岁月催";break;
            case 3:text="叶底藏花一度，梦里踏雪几回。";break;
            case 4:text="事了拂衣去，深藏身与名。";break;
            case 5:text="纵死侠骨香，不惭世上英。";break;
            case 6:text="一箫一剑平生意，负尽狂名十五年。";break;
            case 7:text="一点浩然气，千里快哉风。";break;
            default:break;
        }
        welcomeText.setText(text);

        recyclerView= (RecyclerView) view.findViewById(R.id.home_item);//创建recyclerView对象，并初始化其xml文件

        //设置RecyclerView保持固定大小,这样可以优化RecyclerView的性能
        recyclerView.setHasFixedSize(true);

        initData();
        //System.out.println(list.size()+"1234567");


        return view;
    }

    private void initData() {
     //this.list=TaskFactory.createItem();
        String url="http://1.117.239.54:8080/task?operation=getMainTask&index="+Data.getId()+"&key=";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.getTargetData(url);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                JSONArray array=temp.getJSONArray("data");
                taskCount=array.size();
                if(taskCount==0)
                {
                    //创建Message
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","任务列表为空");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler1.sendMessage(msg);
                    return;
                }
                for (int i = 0; i < taskCount; i++) {

                    int id = 1;
                    Log.d("task id", "task is NO." + id);
                    taskText[i] = JSON.parseObject(array.getString(i)).getString("taskTitle");
                    taskID[i]= JSON.parseObject(array.getString(i)).getLong("taskID");
                    usernames[i]= JSON.parseObject(array.getString(i)).getString("username");
                    taskTag[i]= JSON.parseObject(array.getString(i)).getString("tag");
                }
                String data = temp.getJSONArray("data").getString(0);//数组第一个元素的字符串值
                JSONObject show=JSON.parseObject(data);//转化为json对象
                list=new ArrayList<>();
                for (int i=0;i < taskCount;i++){
                    String text = taskText[i];
                    Long id=taskID[i];
                    String username=usernames[i];
                    String tag=taskTag[i];
                    list.add(new TaskItem(text,id,username,tag));
                }
                Message msg = Message.obtain();
                handler.sendMessage(msg);
            }
        }).start();
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


    //用户点击publish按钮之后的响应
    public void taskPublish(View view) {
        Intent intent = new Intent(requireContext(), Activity_Task_Publish.class);
        startActivity(intent);

    }


}


class space_item extends RecyclerView.ItemDecoration{
   //设置item的间距
   private int space=5;
   public space_item(int space){
       this.space=space;
   }
   public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
       outRect.bottom=space;
       outRect.top=space;
   }

}
