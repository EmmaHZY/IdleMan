package com.example.idleman;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    //用户点击登录按钮之后的响应
    public void sendMessage(View view) {
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
    }
}
