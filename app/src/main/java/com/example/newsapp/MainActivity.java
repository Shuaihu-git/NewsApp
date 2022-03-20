package com.example.newsapp;

import static java.sql.DriverManager.getConnection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button zhuce;
    private Button login;
    private static EditText username;
    private static EditText userpasswd;
    private String responseMsg = "";
    private static String user_ID_s;//数据库端用户ID
    private static String user_Passwd_s;//数据库用户密码
    private static String user_ID_a;
    private static String user_Passwd_a;
    private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟
    private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟
    private static final int LOGIN_OK = 1;
    boolean Success;
    NewsSQL newsSQL=new NewsSQL();
    Connection connection=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.E1);
        userpasswd = (EditText) findViewById(R.id.etp);
        zhuce = (Button) findViewById(R.id.zhuce);
        zhuce.setOnClickListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        user_ID_a=username.getText().toString().trim();//用户输入注册名称
        user_Passwd_a=userpasswd.getText().toString().trim();//用户输入密码
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection=newsSQL.getCon();
                Statement statement=null;
                String SQL;

            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection=newsSQL.getCon();
                Statement statement=null;
                String SQL;
                try {
                    String user_name = username.getText().toString().trim();
                    String passwd;
                    passwd = userpasswd.getText().toString().trim();
                    SQL="SELECT user_name,user_passwd from user where user_name ='"+user_name+"'";
                    statement=connection.createStatement();
                    ResultSet resultSet=statement.executeQuery(SQL);
                    while (resultSet.next()){
                        user_ID_s=resultSet.getString("user_name");
                        user_Passwd_s=resultSet.getString("user_passwd");
                    }
                    if (user_name.equals(user_ID_s)&&passwd.equals(user_Passwd_s)) {
                        Success=true;
                    } else {
                        Success=false;
                    }
                    Log.e("Success", String.valueOf(Success));

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }).start();
        if (v == zhuce) {
            Intent intent = new Intent(this, Zhuce.class);
            startActivity(intent);
            MainActivity.this.finish();
        } else  {
            AlertDialog.Builder alertDialog =new AlertDialog.Builder(this).setTitle("登录");
            alertDialog.setPositiveButton("取消",null);
            alertDialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                        if (Success){
                            Intent intent = new Intent(MainActivity.this,News.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                            MainActivity.this.finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                        }
                }
            });
            alertDialog.setIcon(R.drawable.title);
            alertDialog.setMessage("是否登录");
            alertDialog.show();
        }

    }
}