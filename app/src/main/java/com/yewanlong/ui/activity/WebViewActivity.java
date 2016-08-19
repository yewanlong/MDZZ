package com.yewanlong.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.widget.BrowserLayout;

/**
 * Created by Lkn on 2016/7/15.
 */
public class WebViewActivity extends BaseSwipeBackActivity {
    private TextView titleTextView;
    private LinearLayout leftLayout;
    private String url,title;
    private BrowserLayout webView;
    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("URL");
        title = getIntent().getStringExtra("TITLE");
        titleTextView = (TextView) findViewById(R.id.tv_title);
        leftLayout = (LinearLayout) findViewById(R.id.layout_left);
        webView = (BrowserLayout) findViewById(R.id.webView);
    }

    @Override
    protected void initData() {
        if(title==null){
            titleTextView.setText("详情");
        }else{
            titleTextView.setText(title);
            MobclickAgent.onEvent(this, Constants.NEWS_DETAIL);
        }
        webView.loadUrl(url);
    }

    @Override
    protected void initListener() {
        leftLayout.setOnClickListener(v -> finish());
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }
}
