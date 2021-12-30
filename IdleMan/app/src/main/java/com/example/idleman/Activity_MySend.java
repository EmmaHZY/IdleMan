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

public class Activity_MySend extends AppCompatActivity {
    String message;//页面传参，记录任务ID
    TextView state,receiver,title,content,begin_time,end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_send);

        Intent intent=getIntent();
        message=intent.getLongExtra("taskID",0)+"";//taskID
        initView();
    }

    //查看逻辑
    private void initView(){
        state = findViewById(R.id.info_text_state);
        receiver = findViewById(R.id.info_text_name);
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
                receiver.setText("  接收者："+show.getString("receiverName"));
            }
        }).start();

    }

    //弹窗处理
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            new AlertDialog.Builder(
                    Activity_MySend.this)
                    .setMessage(msg.getData().getString("key"))
                    .setPositiveButton("确定", null)
                    .show();
            return false;
        }
    });


    //撤销逻辑
    public void Cancel(View view){
        String ID = Data.getId();
        String url="http://1.117.239.54:8080/task?operation=cancel";
        Map<String, Object> content=new HashMap<String,Object>();
        content.put('"'+"publisherID"+'"','"'+ID+'"');
        content.put('"'+"taskID"+'"','"'+message+'"');
        String a="{"+ OkHttpConnectHelper.getMapToString(content)+"}";//发送过去的数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.putTargetData(url,a);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                String judge = temp.getJSONObject("meta").getString("status");//获取状态码
                if(judge.equals("2011"))
                {
                    //创建Message
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","撤销任务成功");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler.sendMessage(msg);
                }
                else
                {
                    //创建Message
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","该任务已无法撤销");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
}
