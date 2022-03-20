package com.example.newsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener {
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
    }

    @Override
    public void onClick(View v) {
        if (v==btn_login){
            AlertDialog.Builder alertDialog =new AlertDialog.Builder(this).setTitle("注册");
            alertDialog.setPositiveButton("取消",null);
            alertDialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(login.this,"注册成功",Toast.LENGTH_LONG).show();
                }
            });
            alertDialog.setIcon(R.drawable.title);
            alertDialog.setMessage("是否注册");
            alertDialog.show();
        }
        else{
            Toast.makeText(this,"返回登录页面成功 ",Toast.LENGTH_LONG).show();
        }
    }
}