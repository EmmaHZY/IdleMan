package com.example.idleman;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import connectHelper.OkHttpConnectHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Login extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("登录"));
        tabLayout.addTab(tabLayout.newTab().setText("注册"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTranslationY(300);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        tabLayout.setAlpha(0);
    }

    //弹窗处理
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            new AlertDialog.Builder(
                    Login.this)
                    .setMessage(msg.getData().getString("key"))
                    .setPositiveButton("确定", null)
                    .show();
            return false;
        }
    });

    //登录逻辑
    public void judgeLogin(View view){
        Intent intent = new Intent(Login.this, Home.class);
        //获取用户输入的账号和密码
        EditText account = (EditText) findViewById(R.id.account);
        EditText password = (EditText) findViewById(R.id.password);
        String Account=account.getText().toString();
        String Password=password.getText().toString();

        String url="http://192.168.43.134:8080/user?operation=login";
        Map<String, Object> content=new HashMap<String,Object>();
        content.put('"'+"userID"+'"','"'+Account+'"');
        content.put('"'+"password"+'"','"'+Password+'"');
        String a="{"+OkHttpConnectHelper.getMapToString(content)+"}";//发送过去的数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.postTargetData(url,a);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                String judge = temp.getJSONObject("meta").getString("status");//获取状态码
                if(judge.equals("2011"))
                {
                    startActivity(intent);
                }
                else
                {
                    //创建Message
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","账号或密码错误");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    //注册逻辑
    public void judgeSignup(View view){
        Intent intent = new Intent(Login.this, Home.class);
        //获取用户输入的账号、用户名、密码、密码确认
        EditText account_num = (EditText) findViewById(R.id.accountnum);
        EditText username = (EditText) findViewById(R.id.username);
        EditText pass = (EditText) findViewById(R.id.pass);
        EditText confirm = (EditText) findViewById(R.id.confirm);
        String Account_num=account_num.getText().toString();
        String Username=username.getText().toString();
        String Pass=pass.getText().toString();
        String Confirm=confirm.getText().toString();

        //密码一致判断
        if(!Confirm.equals(Pass))
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

        String url="http://192.168.43.134:8080/user?operation=register";
        Map<String, Object> content=new HashMap<String,Object>();
        content.put('"'+"userID"+'"','"'+Account_num+'"');
        content.put('"'+"name"+'"','"'+Username+'"');
        content.put('"'+"password"+'"','"'+Pass+'"');
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
                    bundle.putString("key","注册成功，可以进行登录");
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
                    bundle.putString("key","该账号已被注册！");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
}
