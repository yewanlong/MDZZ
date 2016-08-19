package com.yewanlong.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;
import com.yewanlong.AppContext;
import com.yewanlong.utils.ThemeUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Ywl on 2016/7/12.
 */
public abstract class BaseActivity extends Base {
    public static AppContext app = AppContext.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        if (isApplyEventBus()) EventBus.getDefault().register(this);
        initData();
        initListener();
    }

    private void initTheme() {
        ThemeUtils.Theme currentTheme = ThemeUtils.getCurrentTheme(this);
        ThemeUtils.changeTheme(this, currentTheme);
    }
    protected abstract int getContentView();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    protected abstract boolean isApplyEventBus();


    @Override
    protected void onDestroy() {
        if (isApplyEventBus()) EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    public void reload(boolean anim) {
        Intent intent = getIntent();
        if (!anim) {
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        finish();
        if (!anim) {
            overridePendingTransition(0, 0);
        }
        startActivity(intent);
    }
    @Override protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }
}
