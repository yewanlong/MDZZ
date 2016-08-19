package com.yewanlong.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
public class NewsInfo implements Serializable {

    private int  cursor;
    private boolean has_more;
    private List<NewsDetail> data;

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<NewsDetail> getData() {
        return data;
    }

    public void setData(List<NewsDetail> data) {
        this.data = data;
    }
}
