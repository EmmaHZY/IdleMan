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

public class Activity_Modify_Info extends AppCompatActivity {
    private EditText name,label,place,qq,wechat,tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modify_info);

        //框定位
        name=(EditText) findViewById(R.id.modify_name);
        label=(EditText) findViewById(R.id.modify_label);
        place=(EditText) findViewById(R.id.modify_place);
        qq=(EditText) findViewById(R.id.modify_QQ);
        wechat=(EditText) findViewById(R.id.modify_wechat);
        tel=(EditText) findViewById(R.id.modify_tel_num);

        //初始值设定
        name.setText(Data.getName());
        label.setText(Data.getLabel());
        place.setText(Data.getPlace());
        qq.setText(Data.getQq());
        wechat.setText(Data.getWeChat());
        tel.setText(Data.getTelNumber());
    }

    //弹窗处理
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            new AlertDialog.Builder(
                    Activity_Modify_Info.this)
                    .setMessage(msg.getData().getString("key"))
                    .setPositiveButton("确定", null)
                    .show();
            return false;
        }
    });

    //退出逻辑,退回首页
    public void exit(View view){
        Activity_Modify_Info.this.finish();
    }

    //点击确认修改后的响应
    public void changeMe(View view) {
        //修改传值
        String Name=name.getText().toString();
        String Label=label.getText().toString();
        String Place=place.getText().toString();
        String Qq=qq.getText().toString();
        String Wechat=wechat.getText().toString();
        String Tel=tel.getText().toString();
        String id=Data.getId().toString();

        String url="http://1.117.239.54:8080/user?operation=changeMe";
        Map<String, Object> content=new HashMap<String,Object>();
        content.put('"'+"userID"+'"','"'+id+'"');
        content.put('"'+"name"+'"','"'+Name+'"');
        content.put('"'+"personlabel"+'"','"'+Label+'"');
        content.put('"'+"Place"+'"','"'+Place+'"');
        content.put('"'+"QQ"+'"','"'+Qq+'"');
        content.put('"'+"weChat"+'"','"'+Wechat+'"');
        content.put('"'+"tel_num"+'"','"'+Tel+'"');
        String a="{"+ OkHttpConnectHelper.getMapToString(content)+"}";//发送过去的数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(a);
                String result = OkHttpConnectHelper.putTargetData(url,a);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                String judge = temp.getJSONObject("meta").getString("status");//获取状态码
                System.out.println(judge);
                if(judge.equals("2041"))//修改成功
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
            }
        }).start();

    }

    //点击修改密码后的响应
    public void modify_password(View view) {
        Intent intent = new Intent(Activity_Modify_Info.this, Activity_Modify_Password.class);
        startActivity(intent);
    }
}