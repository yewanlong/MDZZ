package com.yewanlong;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yewanlong.common.Constants;
import com.yewanlong.model.Realm.RealmMigration;
import com.yewanlong.utils.ImageLoaderUtil;
import com.yewanlong.utils.volley.BaseRequest;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ywl on 2016/7/12.
 * Application
 */
public class AppContext extends Application {

    public static AppContext appContext;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        init(this);
        Intent intent = new Intent(Constants.SERVICE_TEMP);
        sendBroadcast(intent);
    }

    public static AppContext getInstance() {
        return appContext;
    }

    private void init(Context context) {
        ImageLoaderUtil.initImageLoaderConfig(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("ywl.realm")
                .migration(new RealmMigration())
                .schemaVersion(Constants.REALM_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static String _MakeURL(String p_url, Map<String, Object> params) {
        StringBuilder url = new StringBuilder(p_url);
        if (url.indexOf("?") < 0)
            url.append('?');
        for (String name : params.keySet()) {
            url.append('&');
            url.append(name);
            url.append('=');
            url.append(String.valueOf(params.get(name)));
            // 不做URLEncoder处理
            // url.append(URLEncoder.encode(String.valueOf(params.get(name)),
            // UTF_8));
        }
        return url.toString().replace("?&", "?");
    }

    private RequestQueue mQueue;
    private DefaultRetryPolicy mPolicy;

    /**
     * 将请求加入队列并执行，默认不进行请求缓存，默认6秒超时，retry一次
     *
     * @param what
     * @param request
     * @param tag
     */
    public void addRequestQueue(int what, BaseRequest<?> request, Object tag) {
        addRequestQueue(what, request, false, tag);
    }

    public void addRequestQueue(int what, BaseRequest<?> request,
                                boolean shouldCache, Object tag) {
        addRequestQueue(what, request, shouldCache, 6 * 1000, true, tag);
    }

    public void addRequestQueue(int what, BaseRequest<?> request,
                                boolean shouldCache, int initialTimeoutMs, boolean retry, Object tag) {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(this);
        }
        if (mPolicy == null) {
            mPolicy = new DefaultRetryPolicy(initialTimeoutMs, retry ? 1 : 0,
                    1.0f);
        }
//        request.putHeader(getMap());
        request.setRetryPolicy(mPolicy);
        request.setWhat(what);
        request.setShouldCache(shouldCache);
        if (tag != null)
            request.setTag(tag);
        mQueue.add(request);
    }

    public boolean isDebugMode() {
        ApplicationInfo info = getApplicationInfo();
        return (0 != ((info.flags) & ApplicationInfo.FLAG_DEBUGGABLE));
    }
}
