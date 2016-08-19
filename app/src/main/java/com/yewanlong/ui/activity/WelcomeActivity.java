package com.yewanlong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.yewanlong.R;
import com.yewanlong.ui.activity.base.BaseActivity;
import com.yewanlong.common.Constants;
import com.yewanlong.utils.SPUtils;

/**
 * Created by Ywl on 2016/7/12.
 */
public class WelcomeActivity extends BaseActivity {
    private Handler handler = new Handler();
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        mRunnable = new Runnable() {
            @Override
            public void run() {
                pullActivity();
            }
        };
        handler.postDelayed(mRunnable, 3000);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_welcome;
    }

    private void pullActivity() {
        Boolean isSuccess = (Boolean) SPUtils.get( Constants.CREATE_LOCK_SUCCESS, false);
        Intent intent = null;
        if (!isSuccess) {
            intent = new Intent(this, CreateLockActivity.class);
            intent.putExtra("CREATE_MODE", Constants.CREATE_GESTURE);
        } else {
            boolean isOpen = (boolean) SPUtils.get( Constants.SETTING.OPEN_GESTURE, true);
            if (isOpen) {
                intent = new Intent(this, CheckLockActivity.class);
            } else {
                intent = new Intent(this, MainActivity.class);
            }
        }
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mRunnable != null) {
            handler.removeCallbacks(mRunnable);
        }
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    protected void initListener() {
    }


    @Override
    protected void initData() {
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

}
