package com.yewanlong.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.android.volley.Request;
import com.yewanlong.AppContext;
import com.yewanlong.common.Constants;
import com.yewanlong.utils.volley.RequestListener;
import com.yewanlong.utils.volley.StringRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lkn on 2016/7/19.
 */
public class HttpUtils {
    public static final String BASE_HUABAN_URL = "http://route.showapi.com/";
    public static final String BASE_MEIZITU_URL = "http://www.mzitu.com/";

    public static StringRequest getNewsList(RequestListener<String> listener, int count) {
        Map<String, Object> map = new HashMap<>();
        map.put("min_behot_time", "0");
        map.put("count", count);
        map.put("access_token", SPUtils.get(Constants.ACCESS_TOKEN, "") + "");
        map.put("partner", "tongrenbao");
        map.put("timestamp", SPUtils.get(Constants.TIME_STAMP, "").toString());
        map.put("nonce", SPUtils.get(Constants.NONCE, "").toString());
        map.put("signature", SPUtils.get(Constants.SIGNATURE, "").toString());
        StringRequest request = new StringRequest(Request.Method.GET, AppContext.getInstance()._MakeURL("http://open.snssdk.com/data/stream/?v=2", map),
                listener);
        return request;
    }

    public static StringRequest getAccessToken(RequestListener<String> listener, Context context) {
        long timestamp = System.currentTimeMillis() / 1000;
        int nonce = 1 + (int) (Math.random() * 1000000);
        ArrayList list = new ArrayList();
        list.add("d2aeae98f0966d86a5e831f3e4d6d863");
        list.add(timestamp + "");
        list.add(nonce + "");
        Collections.sort(list, new SpellComparator());
        String signature = "";
        for (int i = 0; i < list.size(); i++) {
            signature = signature + list.get(i);
        }
        signature = TouTiaoUtil.encryptToSHA(signature);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("udid", telephonyManager.getDeviceId());
        jsonObject.put("udopenudidid", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        jsonObject.put("os", "Android");
        jsonObject.put("partner", "tongrenbao");
        jsonObject.put("timestamp", timestamp + "");
        jsonObject.put("nonce", nonce + "");
        jsonObject.put("signature", signature);
        StringRequest request = new StringRequest(Request.Method.GET, AppContext.getInstance()._MakeURL("http://open.snssdk.com/auth/access/device/", jsonObject),
                listener);
        SPUtils.put(Constants.TIME_STAMP, timestamp + "");
        SPUtils.put(Constants.NONCE, nonce + "");
        SPUtils.put(Constants.SIGNATURE, signature);
        return request;
    }

    public static StringRequest getTaoFemale(RequestListener<String> listener, int page, String appId, String appSign) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("showapi_appid", appId);
        map.put("showapi_sign", appSign);
        StringRequest request = new StringRequest(Request.Method.POST, AppContext.getInstance()._MakeURL(BASE_HUABAN_URL + "126-2", map),
                listener);
        return request;

    }

    public static StringRequest getSaoFemale(RequestListener<String> listener, int page, String appId, String appSign) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("showapi_appid", appId);
        map.put("showapi_sign", appSign);
        StringRequest request = new StringRequest(Request.Method.GET, AppContext.getInstance()._MakeURL(BASE_HUABAN_URL + "819-1", map),
                listener);
        return request;
    }

    public static StringRequest getLangFemale(RequestListener<String> listener, int page) {
        StringRequest request;
        if (page == 1) {
            request = new StringRequest(Request.Method.GET, BASE_MEIZITU_URL,
                    listener);
        } else {
            request = new StringRequest(Request.Method.GET, BASE_MEIZITU_URL + "page/" + page,
                    listener);
        }
        request.putHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 2345chrome v3.0.0.9739");
        return request;
    }
    public static StringRequest getLangFameleDetais(RequestListener<String> listener,String url) {
        StringRequest request;
            request = new StringRequest(Request.Method.GET, url,
                    listener);
        request.putHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 2345chrome v3.0.0.9739");
        return request;
    }
}
