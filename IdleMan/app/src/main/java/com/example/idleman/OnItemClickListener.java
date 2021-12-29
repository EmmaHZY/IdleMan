package com.example.idleman;

import android.content.ClipData;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 定义RecyclerView选项单击事件的回调接口
 */
public interface OnItemClickListener {//也可以不在这个activity或者是fragment中来声明接口，可以在项目中单独创建一个interface，就改成static就OK
    //参数（父组件，当前单击的View,单击的View的位置，数据）
    void onItemClick(RecyclerView parent, View view, int position, ClipData.Item item);
    // void onItemLongClick(View view);类似，我这里没用就不写了
    //
    //这个data是List中放的数据类型，因为我这里是private List<Map> mapList;这样一个
    //然后我的每个item是这样的：
    //        HashMap map =new HashMap();
    //        map.put("img",R.drawable.delete);
    //        map.put("text","x1");
    //所以我的是map类型的，那如果是item中只有text的话比如List<String>，那么data就改成String类型
}
