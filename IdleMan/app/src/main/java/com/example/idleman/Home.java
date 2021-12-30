package com.example.idleman;


import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Home extends AppCompatActivity {

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        //资源集合，mipmap和selector都可以
        int[] drawables = {R.drawable.homedemo_home, R.drawable.homedemo_message, R.drawable.homedemo_myself};
        RadioButtonImgUtil.setRadioButtonImg(this,drawables,100,findViewById(R.id.bt_home),findViewById(R.id.bt_message),findViewById(R.id.bt_myself));

        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.bt_home, new Fragment_Home());
        mFragmentSparseArray.append(R.id.bt_message, Fragment_Message.newInstance("message"));
        mFragmentSparseArray.append(R.id.bt_myself, new Fragment_Myself());
        mTabRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    mFragmentSparseArray.get(checkedId)).commit();
        });
        // 默认显示第一个
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.bt_home)).commit();

    }
    //用户点击publish按钮之后的响应
    public void taskPublish(View view) {
        Intent intent = new Intent(Home.this, Activity_Task_Publish.class);
        startActivity(intent);
    }

    public void taskCheck(View view){
        Intent intent = new Intent(Home.this, Activity_Task_Check.class);
        startActivity(intent);
    }
}

