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

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

/**
 *用户注册类
 *处理用户界面发送的内容并与数据库进行对比
 * 返回用户注册成功与否
 */
public class Zhuce extends AppCompatActivity implements View.OnClickListener {
    private Button btn_fanhui;
    private Button btn_ok;
    int exists;
    private static EditText ed_name;
    private static  EditText ed_passwd;
    private  static  EditText ed_repasswd;
    NewsSQL newsSQL=new NewsSQL();
    Connection connection=null;
    Statement statement=null;
    boolean Success;
    String name,passwd;
    boolean flag;
    boolean PasswdNotNull;
    boolean UserNotNull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        ed_name=(EditText) findViewById(R.id.ed_name);
        ed_passwd=(EditText) findViewById(R.id.ed_passwd);
        ed_repasswd=(EditText) findViewById(R.id.ed_repasswd);//前端获取输入
        btn_fanhui=(Button) findViewById(R.id.btn_fanhui);
        btn_ok=(Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        btn_fanhui.setOnClickListener(this);
    }
    /*
    用户点击处理类
     */
    @Override
    public void onClick(View v) {
        if (v==btn_fanhui){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            Zhuce.this.finish();
            Toast.makeText(this,"返回登录页面成功 ",Toast.LENGTH_LONG).show();
        }
        else {  AlertDialog.Builder alertDialog =new AlertDialog.Builder(this).setTitle("注册");
            alertDialog.setPositiveButton("取消",null);
            alertDialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String SQL,sql;
                    String user_name=ed_name.getText().toString().trim();
                    String user_passwd=ed_passwd.getText().toString().trim();
                    String user_repasswd=ed_repasswd.getText().toString().trim();
                    String mm="";
                    SQL="insert into user(user_name,user_passwd,birthday,sex,telephone,wechat) values(?,?,'20220222','','','')";
                    sql="select exists (select * from user where user_name="+"'"+user_name+"'"+")";
                    if(user_name.equals(mm))
                    {
                        UserNotNull=false;
                        Toast.makeText(Zhuce.this, "用户名不能为空!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        UserNotNull=true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                connection=newsSQL.getCon();
                                try {
                                    statement = connection.createStatement();
                                    ResultSet resultset = statement.executeQuery(sql);
                                    while (resultset.next()) {
                                        exists =resultset.getInt(1);
                                    }
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        }).start();
                        if (exists==1) {
                            flag = false;
                            Toast.makeText(Zhuce.this, "用户名已存在!", Toast.LENGTH_LONG).show();
                        } else
                        {
                            flag = true;
                        }
                    }
                    if(user_passwd.equals(mm)&&user_repasswd.equals(mm)) {
                        PasswdNotNull=false;
                        Toast.makeText(Zhuce.this, "密码不能为空!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        PasswdNotNull=true;
                        if (user_passwd.equals(user_repasswd)){
                            Log.e("11",user_name);
                            Log.e("111",user_passwd);
                            Success=true;
                        }
                        else{
                            Success=false;
                            Toast.makeText(Zhuce.this, "两次输入密码不一致，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                    if(UserNotNull==false&&PasswdNotNull==false){
                        Toast.makeText(Zhuce.this, "用户名和密码不能为空！", Toast.LENGTH_LONG).show();
                    }else{

                    }
                    System.out.println(user_passwd);
                    System.out.println(user_repasswd);
                    System.out.println(Success);
                    System.out.println(flag);
                        if (UserNotNull&&PasswdNotNull&&flag&&Success) {
                            new Thread(new Runnable() {
                                public void run() {
                                    connection = newsSQL.getCon();
                                    try {
                                        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                                        preparedStatement.setString(1, user_name);
                                        preparedStatement.setString(2, user_passwd);
                                        preparedStatement.execute();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                            }).start();
                            Intent intent = new Intent(Zhuce.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Zhuce.this, "注册成功", Toast.LENGTH_LONG).show();
                            Zhuce.this.finish();
                        }
                        else{
                                Toast.makeText(Zhuce.this, "注册失败，请更换其他用户名或密码", Toast.LENGTH_LONG).show();
                        }
                }
            }).setIcon(R.drawable.title);
            alertDialog.setMessage("是否注册");
            alertDialog.show();
        }
    }
}