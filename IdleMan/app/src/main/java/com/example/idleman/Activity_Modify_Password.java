package com.example.idleman;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.HashMap;
import java.util.Map;

public class Activity_Modify_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modify_password);
    }

    //弹窗处理
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            new AlertDialog.Builder(
                    Activity_Modify_Password.this)
                    .setMessage(msg.getData().getString("key"))
                    .setPositiveButton("确定", null)
                    .show();
            return false;
        }
    });

    //退出逻辑
    public void modify_cancel(View view){
        Intent intent = new Intent(Activity_Modify_Password.this, Activity_Modify_Info.class);
        startActivity(intent);
    }

    //修改逻辑
    public void modify_pass(View view){
        //获取用户输入的原密码、新密码、新密码确认
        EditText old_pass = (EditText) findViewById(R.id.old_pass);
        EditText new_pass = (EditText) findViewById(R.id.new_pass);
        EditText confirm1= (EditText) findViewById(R.id.confirm1);
        String Old_pass=old_pass.getText().toString();
        String New_pass=new_pass.getText().toString();
        String Confirm1=confirm1.getText().toString();
        String id=Data.getId().toString();

        //密码一致判断
        if(!Confirm1.equals(New_pass))
        {
            //创建Message
            Message msg = Message.obtain();
            msg.what = 0;
            //创建Bundle
            Bundle bundle = new Bundle();
            bundle.putString("key","两次密码输入不一致，请重新输入");
            //为Message设置Bundle数据
            msg.setData(bundle);
            //发送消息
            handler.sendMessage(msg);
            return;//不一致，不发送请求
        }

        String url="http://192.168.43.134:8080/user?operation=changePassword";
        Map<String, Object> content=new HashMap<String,Object>();
        content.put('"'+"userID"+'"','"'+id+'"');
        content.put('"'+"passwordOld"+'"','"'+Old_pass+'"');
        content.put('"'+"passwordNew"+'"','"'+New_pass+'"');
        String a="{"+ OkHttpConnectHelper.getMapToString(content)+"}";//发送过去的数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.putTargetData(url,a);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                String judge = temp.getJSONObject("meta").getString("status");//获取状态码
                if(judge.equals("2051"))
                {
                    //创建Message
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","修改成功，可以退出");
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
                    bundle.putString("key","修改失败，请检查原密码是否正确");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
}