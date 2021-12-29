package com.example.idleman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent=getIntent();
        String message=intent.getLongExtra("taskID",0)+"";
        TextView textView=findViewById(R.id.textView2);
        textView.setText(message);
    }
}
