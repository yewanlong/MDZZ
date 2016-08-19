package com.yewanlong.ui.fragment;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.common.UIHelper;
import com.yewanlong.presenter.impl.MainImpl;
import com.yewanlong.ui.fragment.base.BaseFragment;
import com.yewanlong.ui.view.MainAView;
import com.yewanlong.utils.HttpUtils;
import com.yewanlong.utils.SPUtils;
import com.yewanlong.utils.VLog;
import com.yewanlong.utils.volley.StringRequest;
import com.yewanlong.widget.NoScrollerListView;
import com.yewanlong.widget.circle.ImageCycleView;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Lkn on 2016/7/15.
 */
public class MainFragment extends BaseFragment implements MainAView {
    private ImageCycleView cycleView;
    private ListView listView;
    private MainImpl mMainImpl;
    private LinearLayout newsLayout;
    private BGARefreshLayout refreshLayout;
    @Override
    protected void getIntent() {

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View v) {
        cycleView = (ImageCycleView) v.findViewById(R.id.cycleView);
        listView = (ListView) v.findViewById(R.id.listView);
        refreshLayout = (BGARefreshLayout) v.findViewById(R.id.refreshLayout);
        newsLayout = (LinearLayout) v.findViewById(R.id.layout_news_title);
        mMainImpl = new MainImpl(getActivity(), this);
    }

    @Override
    protected void initData() {
        mMainImpl.onCreate();
        newsLayout.setFocusable(true);
        newsLayout.setFocusableInTouchMode(true);
        newsLayout.requestFocus();
    }

    @Override
    protected void initListener() {
        newsLayout.setOnClickListener(mMainImpl);
        refreshLayout.setDelegate(mMainImpl);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(app, false));
    }


    @Override
    protected boolean isApplyEventBus() {
        return false;
    }


    @Override
    public ImageCycleView mCycleView() {
        return cycleView;
    }

    @Override
    public void getAccessToken() {
        StringRequest request = HttpUtils.getAccessToken(mMainImpl.listener,getActivity());
        app.addRequestQueue(1001, request, this);
    }

    @Override
    public ListView getListView() {
        return listView;
    }

    @Override
    public void getList() {
        StringRequest request = HttpUtils.getNewsList(mMainImpl.listener,5);
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
    public void startNewIntent() {
        UIHelper.startNewsListActivity(getActivity());
    }

    @Override
    public void endRefreshing() {
        refreshLayout.endRefreshing();
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
