package com.yewanlong.model.bean.taomodel;

import java.io.Serializable;

/**
 * Created by Lkn on 2016/8/15.
 */
public class SaoFemaleList implements Serializable {
    private String title;
    private String thumb;
    private String url;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {

        return title;
    }

    public String getThumb() {
        return thumb;
    }

    public String getUrl() {
        return url;
    }
}
