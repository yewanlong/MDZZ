package com.yewanlong.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
public class NewsImage implements Serializable {
    private int width;//  600,
    private List<String> urls;//返回的图片URL，前两个是CDN地址、第三个是头条服务器地；若第一个失败，可依次使用其它地址。
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
