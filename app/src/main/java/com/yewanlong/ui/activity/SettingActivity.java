package com.yewanlong.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.ui.fragment.SettingFragment;

/**
 * Created by Lkn on 2016/7/13.
 */
public class SettingActivity extends BaseSwipeBackActivity implements View.OnClickListener{
    private TextView titleTextView;
    private LinearLayout leftLayout;
    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        leftLayout = (LinearLayout) findViewById(R.id.layout_left);
        titleTextView = (TextView) findViewById(R.id.tv_title);
        setFragment();
    }

    @Override
    protected void initData() {
        titleTextView.setText("设置");
    }

    @Override
    protected void initListener() {
        leftLayout.setOnClickListener(this);
    }
    private void setFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SettingFragment())
                .commit();
    }
    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                finish();
                break;
            default:
                break;
        }
    }
}
