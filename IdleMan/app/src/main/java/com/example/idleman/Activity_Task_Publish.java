package com.example.idleman;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

//import com.example.myteamapplication.R;


//public class Activity_Task_Publish extends AppCompatActivity implements SensorEventListener {
//
//
//    private SensorManager sensorManager;
//    private Sensor defaultSensor;
//    private CardView cv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //去掉顶部标题
//        getSupportActionBar().hide();
//        //去掉最上面时间、电量等
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
//                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_task_publish);
//        initView();
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获得传感器管理
//        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);//设置类型
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        sensorManager.registerListener(this, defaultSensor, SensorManager.SENSOR_DELAY_GAME);//注册传感器
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        sensorManager.unregisterListener(this);//注销传感器
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        changeLocation(event.values[1], event.values[2]);
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
//
//
//    private void changeLocation(float y, float z) {
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) cv.getLayoutParams();
//        layoutParams.setMargins((int) z * 5, (int) y * 5, 0, 0);//乘2的作用是为了让效果明显点
//        cv.setLayoutParams(layoutParams);
//
//    }
//
//
//
//    private void initView() {
//        cv = (CardView) findViewById(R.id.cv);
//    }
//
//}

public class Activity_Task_Publish extends AppCompatActivity {
//
//    private SensorManager sensorManager;
//    private Sensor defaultSensor;
    private CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_publish);
//        initView();
    }
//
//    private void initView() {
//        rvImages.setLayoutManager(new GridLayoutManager(this, 3));
//        adapter = new NineGridAdapter(MainActivity.this, mSelectList, rvImages);
//        adapter.setMaxSize(maxNum);
//        rvImages.setAdapter(adapter);
//        adapter.setOnAddPicturesListener(new OnAddPicturesListener() {
//            @Override
//            public void onAdd() {
//                pickImage();  Q
//            }
//        });
//    }
//
//    private void pickImage() {
//        MultiImageSelector selector = MultiImageSelector.create(context);
//        selector.showCamera(true);
//        selector.count(maxNum);
//        selector.multi();
//        selector.origin(mSelectList);
//        selector.start(instans, REQUEST_IMAGE);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE) {
//            if (resultCode == RESULT_OK) {
//                List<String> select = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//                mSelectList.clear();
//                mSelectList.addAll(select);
//                adapter.notifyDataSetChanged();
//            }
//        }
//    }


}
