package com.yewanlong.model.bean.taomodel;

import java.io.Serializable;
import java.util.ArrayList;

public class ContentList implements Serializable{

    public int totalFavorNum;

    public String realName;

    public String totalFanNum;

    public String link;

    public String weight;

    public String avatarUrl;

    public String type;

    public String userId;

    public String city;

    public String height;

    public ArrayList<String> imgList;

    public String cardUrl;

    public void setTotalFavorNum(int totalFavorNum) {
        this.totalFavorNum = totalFavorNum;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setTotalFanNum(String totalFanNum) {
        this.totalFanNum = totalFanNum;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    public int getTotalFavorNum() {

        return totalFavorNum;
    }

    public String getRealName() {
        return realName;
    }

    public String getTotalFanNum() {
        return totalFanNum;
    }

    public String getLink() {
        return link;
    }

    public String getWeight() {
        return weight;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getCity() {
        return city;
    }

    public String getHeight() {
        return height;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public String getCardUrl() {
        return cardUrl;
    }
}
