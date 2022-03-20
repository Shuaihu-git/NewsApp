package com.example.newsapp;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<com.example.newsapp.PlantInfo> getPlantInfos(InputStream is) throws Exception {

                XmlPullParser parser = Xml.newPullParser();

                parser.setInput(is, "utf-8");
                List<com.example.newsapp.PlantInfo> plantInfos = null;
        com.example.newsapp.PlantInfo plantInfo = null;

        int type = parser.getEventType();

        while (type != XmlPullParser.END_DOCUMENT) {
                        switch (type) {

                case XmlPullParser.START_TAG:

                    if ("plants".equals(parser.getName())) {
                        plantInfos = new ArrayList<com.example.newsapp.PlantInfo>();
                    } else if ("plantsInfo".equals(parser.getName())) {
                        plantInfo = new com.example.newsapp.PlantInfo();
                    } else if ("name".equals(parser.getName())) {

                        String name = parser.nextText();
                        plantInfo.setPlantName(name);
                    } else if ("content".equals(parser.getName())) {
                        String content = parser.nextText();
                        plantInfo.setPlantContent(content);
                    }
                    break;

                    case XmlPullParser.END_TAG:

                        if ("plantsInfo".equals(parser.getName())) {
                            plantInfos.add(plantInfo);
                            plantInfo = null;
                        }
                        break;
                        }
                        type = parser.next();
                   }
               return plantInfos;
        	    }

}

