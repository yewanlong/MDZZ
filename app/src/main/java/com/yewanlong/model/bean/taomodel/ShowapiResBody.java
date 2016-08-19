package com.yewanlong.model.bean.taomodel;


import java.io.Serializable;

public class ShowapiResBody implements Serializable {

    public Integer ret_code;

    public void setRet_code(Integer ret_code) {
        this.ret_code = ret_code;
    }

    public Integer getRet_code() {

        return ret_code;
    }

    public void setPagebean(PageBean pagebean) {
        this.pagebean = pagebean;
    }

    public PageBean getPagebean() {
        return pagebean;
    }

    public PageBean pagebean;
}
