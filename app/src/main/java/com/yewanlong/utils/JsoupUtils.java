package com.yewanlong.utils;

import android.util.Log;

import com.yewanlong.model.bean.taomodel.LangFemaleList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lkn on 2016/8/16.
 */
public class JsoupUtils {
    //43 36
    public static List<LangFemaleList> parserMeiziTuHtml(String html) {

        List<LangFemaleList> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        Elements links = doc.select("li");
        Element aelement;
        Element imgelement;
        for (int i = 7; i < links.size(); i++) {
            aelement = links.get(i).select("a").first();
            imgelement = links.get(i).select("img").first();

            LangFemaleList bean = new LangFemaleList();
            bean.setOrder(i);
            bean.setHeight(345);
            bean.setWidth(236);
            bean.setTitle(imgelement.attr("alt").toString());
            bean.setImageurl(imgelement.attr("data-original"));
            bean.setUrl(aelement.attr("href"));
            bean.setGroupid(url2groupid(bean.getUrl()));
            list.add(bean);
        }
        return list;
    }

    public static List<LangFemaleList> parserMeiziTuDeHtml(String html) {
        List<LangFemaleList> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements links = doc.getElementsByAttributeValue("class", "main-image");
        Element aelement;
        Element imgelement;
        for (int i = 0; i < links.size(); i++) {
            aelement = links.get(i).select("a").first();
            imgelement = links.get(i).select("img").first();
            LangFemaleList bean = new LangFemaleList();
            bean.setTitle(imgelement.attr("alt").toString());
            bean.setImageurl(imgelement.attr("src"));
            bean.setUrl(aelement.attr("href"));
            list.add(bean);
        }
        return list;
    }

    /**
     * 获取妹子图的GroupId
     *
     * @param url
     * @return
     */
    private static int url2groupid(String url) {
        return Integer.parseInt(url.split("/")[3]);
    }
}
