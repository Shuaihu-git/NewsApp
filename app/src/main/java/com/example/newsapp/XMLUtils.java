package com.example.newsapp;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLUtils {
    public static List<PostsInfo> getPlantInfos(InputStream is) throws Exception {

                XmlPullParser parser = Xml.newPullParser();

                parser.setInput(is, "utf-8");
                List<PostsInfo> postsInfos = null;
                PostsInfo postsInfo = null;

                 int type = parser.getEventType();

                while (type != XmlPullParser.END_DOCUMENT) {
                        switch (type) {

                case XmlPullParser.START_TAG:

                    if ("posts".equals(parser.getName())) {
                        postsInfos = new ArrayList<PostsInfo>();
                    } else if ("postsInfo".equals(parser.getName())) {
                        postsInfo = new PostsInfo();
                    } else if ("name".equals(parser.getName())) {
                        String name = parser.nextText();
                        postsInfo.setPostsName(name);
                    } else if ("content".equals(parser.getName())) {
                        String content = parser.nextText();
                        postsInfo.setPostsContent(content);
                    }
                    break;

                    case XmlPullParser.END_TAG:

                        if ("postsInfo".equals(parser.getName())) {
                            postsInfos.add(postsInfo);
                            postsInfo = null;
                        }
                        break;
                        }
                        type = parser.next();
                   }
               return postsInfos;
        	    }

}

