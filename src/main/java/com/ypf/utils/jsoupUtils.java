package com.ypf.utils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class jsoupUtils {
    public static Map getLocalWeather() throws IOException {
        Connection connection = Jsoup.newSession();
        connection.url("http://ip.chinaz.com/");
        Element element = connection.get().getElementsByClass("Whwtdhalf w45-0 lh45").get(0).getElementsByTag("em").get(0);

        Map map = new HashMap();
        map.put("currentLocation", element.text());
        String urlCode = URLEncoder.encode(element.text().substring(0, element.text().lastIndexOf(" ")) + "天气", "utf-8");

        connection.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("accept-encoding","gzip, deflate, br")
                .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        connection.url("http://weathernew.pae.baidu.com/weathernew/pc?query=" + urlCode + "&srcid=4982");
        Document doc = connection.get();
        String res = null;
        String[] elements = doc.getElementsByTag("script").outerHtml().toString().split("data");
        for (String ele : elements) {
            if (ele.contains("[\"weather\"]")) {
                res = ele;
                break;
            }
        }
        if(res!=null){
            res = res.replaceAll("\"", "");
        }

        String[] strings = res.substring(res.lastIndexOf("{") + 1, res.lastIndexOf("}")).split(",");

        for (String ele : strings) {
            String[] temp = ele.split(":");
            if (temp[0].equals("update_time")) {
                map.put(temp[0], temp[1]);
            }
            if (temp[0].equals("weather_day")) {
                temp[1] = unicodetoString(temp[1]);
                map.put(temp[0], temp[1]);
            }
            if (temp[0].equals("temperature")) {
                map.put(temp[0], temp[1]);
            }
            if (temp[0].equals("dew_temperature")) {
                map.put(temp[0], temp[1]);
            }

        }
        return map;
    }


    public static String unicodetoString(String unicode) {
        if (unicode == null || "".equals(unicode)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }

}
