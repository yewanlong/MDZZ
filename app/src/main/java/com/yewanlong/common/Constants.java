package com.yewanlong.common;

/**
 * Created by Ywl on 2016/7/12.
 */
public class Constants {
    /**
     * 第一次进入程序
     */
    public static final String CREATE_LOCK_SUCCESS = "CREATE_LOCK_SUCCESS";
    public static int REALM_VERSION = 1;

    public static int VIEW_MODE = 0;
    public static int CREATE_MODE = 1;
    /**
     * 首次设置密码
     */
    public static int CREATE_GESTURE = 1;
    public static int UPDATE_GESTURE = 2;

    public final static class EVEN_BUS {
        public static int CHANGE_THEME = 2;
        public static int CHANGE_PASS_WORD_SHOW = 3;
    }
    /**
     * 是否打开解锁
     */
    public final static class SETTING {
        public static String OPEN_GESTURE = "OPEN_GESTURE";
        public static String OPEN_PASS_WORD_SHOW = "OPEN_PASS_WORD_SHOW";
    }
    /**
     * access_token剩余过期时间, 相对值，单位秒
     */
    public  static String EXPIRES_IN = "expires_in";
    /**
     * 上次获取access_token时候的时间戳
     */
    public static String OLD_TOKEN_TIME = "token_time";
    /**
     * 上次获取的access_token
     */
    public static String ACCESS_TOKEN = "access_token";

    /**
     * 上次获取的timestamp
     */
    public static String TIME_STAMP = "timestamp";
    /**
     * 上次获取的nonce
     */
    public static String NONCE = "nonce";
    /**
     * 上次获取的signature
     */
    public static String SIGNATURE = "news_signature";
    /**
     * 查看新闻详情
     */
    public static String NEWS_DETAIL = "news_detail";//查看新闻详情

    /**
     * 步数
     */

    public static String CURRENT_STEP = "current_step";
    public static String SERVICE_TEMP = "com.ywl.step.destroy";

    /**
     * 头像
     */
    public static String HEADER_IAMGE="header_image";

    public static String URL_DOWNLOAD = "http://www.pgyer.com/apiv1/app/install?aId=d5a6abd949459191686e3463486ac75f&_api_key=a47f1ecebe5b9d952dad9b65f148b56f";
    //易源接口AppId
    public static final String APP_ID = "15314";

    //易源接口AppSign
    public static final String APP_SIGN = "d424376f51f1467da1b8c75debebf148";
}
