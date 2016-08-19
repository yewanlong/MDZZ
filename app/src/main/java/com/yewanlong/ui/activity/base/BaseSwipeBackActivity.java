package com.yewanlong.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.yewanlong.AppContext;
import com.yewanlong.utils.ThemeUtils;

import org.greenrobot.eventbus.EventBus;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by Lkn on 2016/7/13.
 */
public abstract class BaseSwipeBackActivity extends Base implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;
    public static AppContext app = AppContext.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
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
        intent.putExtra("anim", anim);
        if (!anim) {
            mHelper.getSwipeBackLayout();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        finish();
        if (!anim) {
            overridePendingTransition(0, 0);
        }
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
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
