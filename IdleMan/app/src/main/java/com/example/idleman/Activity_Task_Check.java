package com.example.idleman;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.ArrayList;
import java.util.List;

public class Activity_Task_Check extends AppCompatActivity {
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

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            HomeAdapter homeAdapter = new HomeAdapter(Activity_Task_Check.this, list);//创建适配器对象
            recyclerView.setLayoutManager(new GridLayoutManager(Activity_Task_Check.this, 2));//设置为表格布局，列数为2（这个是最主要的，就是这个来展示陈列式布局）
            recyclerView.addItemDecoration(new space_item(space));//给recycleView添加item的间距
            recyclerView.setAdapter(homeAdapter);//将适配器添加到recyclerView
            return false;
        }
    });

    public Activity_Task_Check() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_check);
        recyclerView = (RecyclerView) findViewById(R.id.all_task_item);//创建recyclerView对象，并初始化其xml文件

        //设置RecyclerView保持固定大小,这样可以优化RecyclerView的性能
        recyclerView.setHasFixedSize(true);

        initData();
        //System.out.println(list.size()+"1234567");
    }

    private void initData() {
        //this.list=TaskFactory.createItem();
        String url = "http://1.117.239.54:8080/task?operation=getAll&index=";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.getTargetData(url);
                JSONObject temp = JSON.parseObject(result);//结果转化为json对象
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
