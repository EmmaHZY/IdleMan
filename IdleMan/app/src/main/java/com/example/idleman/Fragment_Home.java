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

//public class Fragment_Home extends Fragment {
//    private View view;//定义view用来设置fragment的layout
//    public RecyclerView mCollectRecyclerView;//定义RecyclerView
//    //定义以goodsentity实体类为对象的数据集合
//    private ArrayList<GoodsEntity> goodsEntityList = new ArrayList<GoodsEntity>();
//    //自定义recyclerveiw的适配器
//    private CollectRecycleAdapter mCollectRecyclerAdapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //获取fragment的layout
//        view = inflater.inflate(R.layout.collect_page, container, false);
//        //对recycleview进行配置
//        initRecyclerView();
//        //模拟数据
//        initData();
//        return view;
//    }
//
//    /**
//     * TODO 模拟数据
//     */
//    private void initData() {
//        for (int i=0;i<10;i++){
//            GoodsEntity goodsEntity=new GoodsEntity();
//            goodsEntity.setGoodsName("模拟数据"+i);
//            goodsEntity.setGoodsPrice("100"+i);
//            goodsEntityList.add(goodsEntity);
//        }
//    }
//
//    /**
//     * TODO 对recycleview进行配置
//     */
//
//    private void initRecyclerView() {
//        //获取RecyclerView
//        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView);
//        //创建adapter
//        mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), goodsEntityList);
//        //给RecyclerView设置adapter
//        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
//        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
//        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        //设置item的分割线
//        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
//        mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, GoodsEntity data) {
//                //此处进行监听事件的业务处理
//                Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//}


public class Fragment_Home extends Fragment {

    private List<TaskItem> list=new ArrayList<>();
    private int space;
    //要展示的对应item的数据，imgs是上方的图片/视图
    //titles是标题，headsIcon是头像，username是用户名
    private int[] imgs;
    private String[] titles;
    private int[] headsIcon;
    private String[] usernames;
    public static String[] taskText = new String[1000];
    public static Long[] taskID = new Long[1000];
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
                            System.out.println("与仙女");
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

        recyclerView= (RecyclerView) view.findViewById(R.id.home_item);//创建recyclerView对象，并初始化其xml文件

        //设置RecyclerView保持固定大小,这样可以优化RecyclerView的性能
        recyclerView.setHasFixedSize(true);

        initData();
        //System.out.println(list.size()+"1234567");

//        LinearLayoutManager layoutManager = new LinearLayoutManager(context;
//        recyclerView.setLayoutManager(layoutManager);

//        HomeAdapter homeAdapter=new HomeAdapter(getActivity(), list);//创建适配器对象
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));//设置为表格布局，列数为2（这个是最主要的，就是这个来展示陈列式布局）
//        recyclerView.addItemDecoration(new space_item(space));//给recycleView添加item的间距
//        recyclerView.setAdapter(homeAdapter);//将适配器添加到recyclerView

//        //设置瀑布流布局为2列，垂直方向滑动
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//
//        StaggeredGridAdater staggeredGridAdater = new StaggeredGridAdater(this.getActivity(),homeList);
//
//        //创建分割线对象
//        recyclerView.addItemDecoration(new MyDecoration());
//
//        recyclerView.setAdapter(staggeredGridAdater);

        return view;
    }

    private void initData() {
     //this.list=TaskFactory.createItem();
        String url="http://1.117.239.54:8080/task?operation=getAll&index=sb";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.getTargetData(url);
                System.out.println("caonima");
                System.out.println(result);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                JSONArray array=temp.getJSONArray("data");
                taskCount=array.size();
                System.out.println("万惟佳"+taskCount);
                for (int i = 0; i < taskCount; i++) {
//            if(cursor.moveToFirst ()) {
//                cursor.move(i);
//                int id = cursor.getInt(cursor.getColumnIndex("id"));
//                Log.d("task id", "task is NO." + id);
//                taskText[i] = cursor.getString(cursor.getColumnIndex("text"));
//            }
                    int id = 1;
                    Log.d("task id", "task is NO." + id);
                    taskText[i] = JSON.parseObject(array.getString(i)).getString("taskTitle");
                    taskID[i]= JSON.parseObject(array.getString(i)).getLong("taskID");
                }
                String data = temp.getJSONArray("data").getString(0);//数组第一个元素的字符串值
                JSONObject show=JSON.parseObject(data);//转化为json对象
                for (int i=0;i < taskCount;i++){
                    String text = taskText[i];
                    Long id=taskID[i];
                    System.out.println(text);
                    list.add(new TaskItem(text,id));
                }
                Message msg = Message.obtain();
                msg.what = 0;
                //创建Bundle
                Bundle bundle = new Bundle();
                bundle.putString("key","两次密码输入不一致，请重新输入");
                //为Message设置Bundle数据
                msg.setData(bundle);
                //发送消息
                handler.sendMessage(msg);
            }
        }).start();
    }


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
