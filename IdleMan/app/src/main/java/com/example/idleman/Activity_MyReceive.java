package com.example.idleman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.HashMap;
import java.util.Map;

public class Activity_MyReceive extends AppCompatActivity {
    String message;//页面传参，记录任务ID
    TextView state,publisher,title,content,begin_time,end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_receive);

        Intent intent=getIntent();
        message=intent.getLongExtra("taskID",0)+"";//taskID
        initView();
    }

    //查看逻辑
    private void initView(){
        state = findViewById(R.id.info_text_state);
        publisher = findViewById(R.id.info_text_name);
        title = findViewById(R.id.info_text_title);
        content = findViewById(R.id.info_text_content);
        begin_time = findViewById(R.id.info_text_begin_time);
        end_time= findViewById(R.id.info_text_end_time);

        String url="http://1.117.239.54:8080/task?operation=getByTaskID&index="+message+"&key=";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.getTargetData(url);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                String data = temp.getJSONArray("data").getString(0);//数组第一个元素的字符串值
                JSONObject show=JSON.parseObject(data);//转化为json对象
                title.setText("  标题:"+show.getString("taskTitle"));
                content.setText("  详情："+show.getString("taskContent"));
                begin_time.setText("发布时间："+show.getString("publishTime"));
                end_time.setText("截止时间："+show.getString("deadline"));
                state.setText("  任务状态："+show.getString("taskState"));
                publisher.setText("  发布者："+show.getString("username"));
            }
        }).start();

    }

}
