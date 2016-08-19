package com.yewanlong.model.bean.taomodel;

import java.io.Serializable;
import java.util.ArrayList;

public class PageBean implements Serializable {

    public ArrayList<ContentList> contentlist;

    public Integer maxResult;

    public String currentPage;

    public String allNum;

    public String allPages;

    public void setContentlist(ArrayList<ContentList> contentlist) {
        this.contentlist = contentlist;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public void setAllPages(String allPages) {
        this.allPages = allPages;
    }

    public ArrayList<ContentList> getContentlist() {

        return contentlist;
    }

    public Integer getMaxResult() {
        return maxResult;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getAllNum() {
        return allNum;
    }

    public String getAllPages() {
        return allPages;
    }
}
