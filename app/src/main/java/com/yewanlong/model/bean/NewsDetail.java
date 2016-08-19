package com.yewanlong.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
public class NewsDetail implements Serializable {

    private long id;//新闻的id
    private String title;//	新闻的标题
    private String site;//	新闻的来源
    private String site_url;//	新闻的来源的网址
    private long publish_time;//	新闻的发表时间	时间戳
    private long display_time;//	新闻展示时间	时间戳
//    private String anAbstract;//	新闻的简介
    private String toutiao_url;//	新闻的头条网址
    private String toutiao_wap_url;//	新闻头条网Wap版地址	 没有安装今日头条APP时，使用此URL打开对应的今日头条H5页
    private String app_open_url;//	头条APP详情页打开地址	URL 安装了今日头条APP时，即可打开对应详情页（体验更好，评论更多）Android实例  snssdk143://detail?groupid=1348418464
    private int tip;//	热、荐的状态0, 无额外信息. 默认【皆为二进制】1 代表 热 10 代表 推荐11 代表 推荐 + 热 ( 1 | 2)
    private int digg_count;//	顶的数量
    private int bury_count;//	踩的数量
    private int favorite_count;//	收藏的数量
    private int comment_count;//	评论的数量
    private List<NewsImage> images;//	图片相关新闻 每个图片存一个对象，整个字段是一个list
    private List<NewsListImage> list_images;//即列表页图片字段，头条的文章列表也是使用的此参数
    private List<NewsThumbImage> thumb_images;//即缩略图字段，一般比list_images规格要小

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

//    public String getAnAbstract() {
//        return anAbstract;
//    }
//
//    public void setAnAbstract(String anAbstract) {
//        this.anAbstract = anAbstract;
//    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(long publish_time) {
        this.publish_time = publish_time;
    }

    public long getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(long display_time) {
        this.display_time = display_time;
    }

    public String getToutiao_url() {
        return toutiao_url;
    }

    public void setToutiao_url(String toutiao_url) {
        this.toutiao_url = toutiao_url;
    }

    public String getToutiao_wap_url() {
        return toutiao_wap_url;
    }

    public void setToutiao_wap_url(String toutiao_wap_url) {
        this.toutiao_wap_url = toutiao_wap_url;
    }

    public String getApp_open_url() {
        return app_open_url;
    }

    public void setApp_open_url(String app_open_url) {
        this.app_open_url = app_open_url;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public int getBury_count() {
        return bury_count;
    }

    public void setBury_count(int bury_count) {
        this.bury_count = bury_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public List<NewsImage> getImages() {
        return images;
    }

    public void setImages(List<NewsImage> images) {
        this.images = images;
    }

    public List<NewsListImage> getList_images() {
        return list_images;
    }

    public void setList_images(List<NewsListImage> list_images) {
        this.list_images = list_images;
    }

    public List<NewsThumbImage> getThumb_images() {
        return thumb_images;
    }

    public void setThumb_images(List<NewsThumbImage> thumb_images) {
        this.thumb_images = thumb_images;
    }
}
