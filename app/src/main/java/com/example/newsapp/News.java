package com.example.newsapp;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class News extends AppCompatActivity {
    private LinearLayout loading;
    private ListView lvNews;
    private List<NewsInfo> newsInfos;
    private TextView tv_title;
    private TextView tv_description;
    private TextView tv_type;
    private NewsInfo newsInfo;
    private SmartImageView siv;
    private TextView content;
    private ImageView image;
    public  static int p=0;
    private List<PostsInfo> postsInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fillData();
    }
    private void initView() {
        loading = (LinearLayout) findViewById(R.id.loading);
        lvNews = (ListView) findViewById(R.id.lv_news);
        content = (TextView) findViewById(R.id.tv_content);
        image= (ImageView) findViewById(R.id.imgv);
//        content.setText(
//                "\u3000\u3000" + postsInfos.get(0).getPostsContent());
//        image.setBackgroundResource(R.drawable.a);
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=view.findViewById(R.id.tv_title);
                String s="总行："+parent.getCount()+"/n Item的Name："+textView.getText().toString()+" 行号："+position+"id:"+id;
                System.out.println(s);
                p=position;
                Intent intent=new Intent(News.this, Posts.class);
                startActivity(intent);
            }
        });

    }

    private void fillData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getString(R.string.serverurl), new AsyncHttpResponseHandler() {
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                try {
                    String json = new String(bytes, "UTF-8");
                    newsInfos = JsonParse.getNewsInfo(json);
                    if (newsInfos == null) {
                        Toast.makeText(News.this, "解析失败", Toast.LENGTH_SHORT).show();
                    } else {
                        loading.setVisibility(View.INVISIBLE);
                        lvNews.setAdapter(new News.NewsAdapter());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(News.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private  class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsInfos.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= View.inflate(News.this,R.layout.news_item,null);
            siv=(SmartImageView) view.findViewById(R.id.siv_icon);
            tv_description=(TextView) view.findViewById(R.id.tv_description);
            tv_title=(TextView) view.findViewById(R.id.tv_title);
            tv_type=(TextView) view.findViewById(R.id.tv_type);
            newsInfo = newsInfos.get(position);
            siv.setImageUrl(newsInfo.getIcon(),R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            tv_title.setText(newsInfo.getTitle());
            tv_description.setText(newsInfo.getContent());
            int type=newsInfo.getType();
            switch (type){
                case 1:
                    tv_type.setTextColor(Color.MAGENTA);
                    tv_type.setText("薪资："+ newsInfo.getComment());
                    break;
                case 2:
                    tv_type.setTextColor(Color.CYAN);
                    tv_type.setText("招聘人数:"+newsInfo.getComment());
                    break;
                case 3:

                    tv_type.setText("点击了解详情");
                    break;
                case 4:
                    tv_type.setTextColor(Color.GREEN);
                    tv_type.setText("日新："+newsInfo.getComment());
                    break;
                case 5:
                    tv_type.setTextColor(Color.RED);
                    tv_type.setText("停止招聘");
                    break;
                default:
                    break;
            }
            return view;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }
}
