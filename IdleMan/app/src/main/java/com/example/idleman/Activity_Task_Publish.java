package com.example.idleman;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Activity_Task_Publish extends AppCompatActivity {

    private EditText deadline,publish_title,publish_deadline,publish_content;
    private CardView cv;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_publish);

        //日期选择
        deadline = (EditText) findViewById(R.id.publish_deadline);
        deadline.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        deadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
    }

    //日期选择函数
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Task_Publish.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Activity_Task_Publish.this.deadline.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //弹窗处理
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            new AlertDialog.Builder(
                    Activity_Task_Publish.this)
                    .setMessage(msg.getData().getString("key"))
                    .setPositiveButton("确定", null)
                    .show();
            return false;
        }
    });

    //任务类型选定
    public void tag1(View view){tag="1";}
    public void tag2(View view){tag="2";}
    public void tag3(View view){tag="3";}
    public void tag4(View view){tag="4";}

    //退出逻辑,退回首页
    public void cancel(View view){
        Intent intent = new Intent(Activity_Task_Publish.this, Home.class);
        startActivity(intent);
    }

    //发布逻辑
    public void publish_task(View view){
        //获取任务标题、截止日期、内容
        publish_title = (EditText) findViewById(R.id.publish_title);
        publish_deadline = (EditText) findViewById(R.id.publish_deadline);
        publish_content = (EditText) findViewById(R.id.publish_content);
        String Publish_title=publish_title.getText().toString();
        String Publish_deadline=publish_deadline.getText().toString();
        String Publish_content=publish_content.getText().toString();
        String id=Data.getId().toString();

        String url="http://192.168.43.134:8080/task?operation=add";
        Map<String, Object> content=new HashMap<String,Object>();
        content.put('"'+"publisherID"+'"','"'+id+'"');
        content.put('"'+"tag"+'"','"'+tag+'"');
        content.put('"'+"taskTitle"+'"','"'+Publish_title+'"');
        content.put('"'+"deadline"+'"','"'+Publish_deadline+'"');
        content.put('"'+"taskContent"+'"','"'+Publish_content+'"');
        String a="{"+OkHttpConnectHelper.getMapToString(content)+"}";//发送过去的数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.postTargetData(url,a);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                String judge = temp.getJSONObject("meta").getString("status");//获取状态码
                if(judge.equals("2001"))
                {
                    //创建Message
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","发布成功，可以退出");
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
                    bundle.putString("key","发布失败，请检查闲币数");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }


}
