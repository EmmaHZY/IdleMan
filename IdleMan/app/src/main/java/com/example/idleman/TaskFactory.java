package com.example.idleman;

import android.content.ClipData;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import connectHelper.OkHttpConnectHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskFactory {
    public static String[] taskText = new String[1000];
    public static int taskCount;
    public static int a;
    public static void queryDatabase(){
        String url="http://192.168.43.134:8080/task?operation=getByPublisherID&index=1952541";
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
                    taskText[i] = "test111";
                }
                String data = temp.getJSONArray("data").getString(0);//数组第一个元素的字符串值
                JSONObject show=JSON.parseObject(data);//转化为json对象

                List<ClipData.Item> items = new ArrayList<>();
                for (int i=0;i < taskCount;i++){
                    String text = taskText[i];
                    System.out.println(text);
                    System.out.println("fuck");
                    items.add(new ClipData.Item(text));
                }
            }
        }).start();
        String[] strings = new String[]{"text"};
//        Cursor cursor = db.query ("text",strings,null,null,null,null,null);
//        taskCount = cursor.getCount ();
    }

    public static List<ClipData.Item> createItem(){
        List<ClipData.Item> items = new ArrayList<>();
        queryDatabase ();
        System.out.println("张荣添");
        System.out.println(taskCount);
        for (int i=0;i < taskCount;i++){
            String text = taskText[i];
            System.out.println(text);
            System.out.println("fuck");
            items.add(new ClipData.Item(text));
        }
        return items;
    }
}
