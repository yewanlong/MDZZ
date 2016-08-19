package com.yewanlong.ui.activity;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.presenter.impl.NewsListImpl;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.ui.view.NewsView;
import com.yewanlong.utils.HttpUtils;
import com.yewanlong.utils.SPUtils;
import com.yewanlong.utils.VLog;
import com.yewanlong.utils.volley.StringRequest;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Lkn on 2016/7/18.
 */
public class NewsListActivity extends BaseSwipeBackActivity implements NewsView {
    private TextView titleTextView;
    private LinearLayout leftLayout;
    private NewsListImpl mNewsListImpl;
    private ListView listView;
    private BGARefreshLayout refreshLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.tv_title);
        leftLayout = (LinearLayout) findViewById(R.id.layout_left);
        listView = (ListView) findViewById(R.id.listView);
        refreshLayout = (BGARefreshLayout) findViewById(R.id.refreshLayout);
        mNewsListImpl = new NewsListImpl(this, this);
    }

    @Override
    protected void initData() {
        titleTextView.setText("头条新闻");
    }

    @Override
    protected void initListener() {
        leftLayout.setOnClickListener(mNewsListImpl);
        refreshLayout.setDelegate(mNewsListImpl);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(app, true));
        mNewsListImpl.onCreate();
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
    public void backActivity() {
        finish();
    }

    @Override
    public ListView getListView() {
        return listView;
    }

    @Override
    public void getList() {
        StringRequest request = HttpUtils.getNewsList(mNewsListImpl.listener,15);
        app.addRequestQueue(1002, request, this);
    }

    @Override
    public void getNews() {
        if (isAccessTokenValid()) {
            getList();
        } else {
            getAccessToken();
        }
    }

    @Override
    public void getAccessToken() {
        StringRequest request = HttpUtils.getAccessToken(mNewsListImpl.listener, this);
        app.addRequestQueue(1001, request, this);
    }

    @Override
    public void endRefreshing() {
        refreshLayout.endRefreshing();
    }

    @Override
    public void endLoadingMore() {
        refreshLayout.endLoadingMore();
    }

    public boolean isAccessTokenValid() {
        if ((Integer) SPUtils.get(Constants.EXPIRES_IN, 0) == 0) {
            //这里是没有获取过access_token，所以access_token是无效的
            return false;
        } else {
            //这里access_token时间过期，所以access_token是无效的
            Lg("OLD_TOKEN_TIME" + SPUtils.get(Constants.OLD_TOKEN_TIME, (long) 0).toString());
            Lg("currentTimeMillis" + System.currentTimeMillis());
            Lg("旧的token到现在的时间间隔" + ((int) (System.currentTimeMillis() - Long.parseLong(SPUtils.get(Constants.OLD_TOKEN_TIME, (long) 0).toString())) / 1000));
            Lg("旧的token的有效时间" + SPUtils.get(Constants.EXPIRES_IN, 0).toString());
            if ((int) (System.currentTimeMillis() - Long.parseLong(SPUtils.get(Constants.OLD_TOKEN_TIME, (long) 0).toString())) / 1000 > Integer.parseInt(SPUtils.get(Constants.EXPIRES_IN, 0).toString())) {
                return false;
            }
            return true;
        }
    }

    public void Lg(String message) {
        VLog.d(getClass().getSimpleName(), message);
    }

}
