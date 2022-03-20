package com.example.newsapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonParse {
    public static List<com.example.newsapp.NewsInfo> getNewsInfo(String json){
     Gson gson=new Gson();
     Type listType=new TypeToken<List<com.example.newsapp.NewsInfo>>(){}.getType();
     List<com.example.newsapp.NewsInfo> newsInfos=gson.fromJson(json,listType);
        return newsInfos;
    }
}

