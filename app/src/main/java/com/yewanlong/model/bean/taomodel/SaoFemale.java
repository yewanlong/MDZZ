package com.yewanlong.model.bean.taomodel;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class SaoFemale implements Serializable{

    public JSONObject showapi_res_body;

    public String showapi_res_error;

    public int showapi_res_code;


    public void setShowapi_res_body(JSONObject showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public JSONObject getShowapi_res_body() {

        return showapi_res_body;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }
}
