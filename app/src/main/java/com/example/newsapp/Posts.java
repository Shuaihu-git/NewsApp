package com.example.newsapp;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Posts extends AppCompatActivity {
    private List<PostsInfo> postsInfos;
 	    private TextView content;
 	    private ImageView Imgv;
 	    News news=new News();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        try {
            postsInfos = com.example.newsapp.XMLUtils.getPlantInfos(getResources().getAssets().open("posts.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
        content.setMovementMethod(new ScrollingMovementMethod());



 }
    private void initView() {
        content = (TextView) findViewById(R.id.tv_content);
        Imgv = (ImageView) findViewById(R.id.imgv);
        int p=news.p;
        content.setText(
                "\u3000\u3000" + postsInfos.get(p).getPostsContent());
        Imgv.setBackgroundResource(R.drawable.b);
    }
}


