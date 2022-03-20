package com.example.newsapp;




import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class NewsSQL {
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://www.shuaihu.vip:3306/Android?useUnicode = true&characterEncoding = utf-8&serverTimezone=UTC";
    static final String USER="root";
    static final String Passwd="19991016zsh..";


    public static Connection getCon() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, Passwd);
            Log.e("SQl", "SUCCESS");
            if (!conn.isClosed()) {
                System.out.println("连接成功");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("SQl", "JDBC");
        }
        return conn;
    }
}
