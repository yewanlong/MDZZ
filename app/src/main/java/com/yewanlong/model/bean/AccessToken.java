package com.yewanlong.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/9/8.
 */
public class AccessToken implements Serializable {
    private String access_token;
    private int expires_in; //access_token剩余过期时间, 相对值，单位秒

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}

