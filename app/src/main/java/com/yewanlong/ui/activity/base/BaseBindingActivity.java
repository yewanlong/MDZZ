package com.yewanlong.ui.activity.base;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;
import com.yewanlong.R;
import com.yewanlong.utils.ThemeUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Ywl on 2016/7/12.
 */
public abstract class BaseBindingActivity extends Base {
    protected ViewDataBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        if (isApplyTranslucency()) initWindow();
        mDataBinding = DataBindingUtil.setContentView(this, getContentView());
        initView();
        if (isApplyEventBus()) EventBus.getDefault().register(this);
        initData();
    }

    private void initTheme() {
        ThemeUtils.Theme currentTheme = ThemeUtils.getCurrentTheme(this);
        ThemeUtils.changeTheme(this, currentTheme);
    }
    protected void initToolBar(Toolbar toolbar) {

        if (toolbar == null) return;

        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    protected abstract int getContentView();

    protected abstract void initView();
    protected abstract void initData();

    protected abstract boolean isApplyTranslucency();

    protected abstract boolean isApplyEventBus();

    /**
     * api大于19的时候，实现沉浸式状态栏
     */
    @TargetApi(19)
    protected void initWindow() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getStatusBarColor());
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    protected int getStatusBarColor() {
        return getColorPrimary();
    }

    private int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    protected void onDestroy() {
        if (isApplyEventBus()) EventBus.getDefault().unregister(this);
        super.onDestroy();
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
