package com.example.idleman;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.HashMap;
import java.util.Map;

public class Fragment_Myself_Info extends Fragment {

    private ItemGroup id,level,coin,creditScore,name, per_label, region,QQ,wechat,tel_num;
    private ImageButton Edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_myself_info,container,false);
        initView(view);
        Edit= (ImageButton)view.findViewById(R.id.edit);
        Edit.setOnClickListener(new View.OnClickListener(){
            //为找到的button设置监听
            @Override
            //重写onClick函数
            public void onClick(View v){
                Intent intent = new Intent(Fragment_Myself_Info.this.getActivity(), Activity_Modify_Info.class);
                startActivity(intent);
            }
        });
        return view;
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

        String url="http://1.117.239.54:8080/user?operation=getMe&index="+Data.getId();

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
                Data.setName(show.getString("username"));
                name.getContentEdt().setText(Data.getName());
                Data.setLabel(show.getString("personlabel"));
                per_label.getContentEdt().setText(Data.getLabel());
                Data.setPlace(show.getString("place"));
                region.getContentEdt().setText(Data.getPlace());
                Data.setQq(show.getString("qqnumber"));
                QQ.getContentEdt().setText(Data.getQq() );
                Data.setWeChat(show.getString("wechatnumber"));
                wechat.getContentEdt().setText(Data.getWeChat() );
                Data.setTelNumber(show.getString("telnumber"));
                tel_num.getContentEdt().setText(Data.getTelNumber() );
            }
        }).start();
    }

}