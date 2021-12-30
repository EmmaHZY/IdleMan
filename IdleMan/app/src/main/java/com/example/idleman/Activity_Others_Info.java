package com.example.idleman;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

public class Activity_Others_Info extends AppCompatActivity {
    String message1;
    private ItemGroup id,level,coin,creditScore,name, per_label, region,QQ,wechat,tel_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_others_info);
        View view=getWindow().getDecorView();
        Intent intent=getIntent();
        message1=intent.getStringExtra("receiverID")+"";//taskID
        initView(view);
    }

    private void initView(View view)  {
        id= (ItemGroup) view.findViewById(R.id.ig_id);
        level = (ItemGroup) view.findViewById(R.id.ig_level);
        coin = (ItemGroup) view.findViewById(R.id.ig_coin);
        creditScore = (ItemGroup) view.findViewById(R.id.ig_creditScore);
        name= (ItemGroup) view.findViewById(R.id.ig_name);
        per_label = (ItemGroup) view.findViewById(R.id.ig_per_label);
        region = (ItemGroup) view.findViewById(R.id.ig_region);
        QQ = (ItemGroup) view.findViewById(R.id.ig_qq);
        wechat = (ItemGroup) view.findViewById(R.id.ig_wechat);
        tel_num = (ItemGroup) view.findViewById(R.id.ig_tel_num);

        String url="http://1.117.239.54:8080/user?operation=getMe&index="+message1;
        System.out.println(message1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = OkHttpConnectHelper.getTargetData(url);
                JSONObject temp= JSON.parseObject(result);//结果转化为json对象
                String data = temp.getJSONArray("data").getString(0);//数组第一个元素的字符串值
                JSONObject show=JSON.parseObject(data);//转化为json对象
                id.getContentEdt().setText(show.getString("userID"));
                level.getContentEdt().setText(show.getString("level"));
                coin.getContentEdt().setText(show.getString("coin"));
                creditScore.getContentEdt().setText(show.getString("creditscore"));
                name.getContentEdt().setText(show.getString("username"));
                per_label.getContentEdt().setText(show.getString("personlabel"));
                region.getContentEdt().setText(show.getString("place"));
                QQ.getContentEdt().setText(show.getString("qqnumber") );
                wechat.getContentEdt().setText(show.getString("wechatnumber"));
                tel_num.getContentEdt().setText(show.getString("telnumber") );
            }
        }).start();
    }
}