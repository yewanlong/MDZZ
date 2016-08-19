package com.yewanlong.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.bean.AccessToken;
import com.yewanlong.model.bean.NewsDetail;
import com.yewanlong.model.bean.NewsInfo;
import com.yewanlong.presenter.ActivityPresenterBundle;
import com.yewanlong.ui.adapter.listview.MyAdapter;
import com.yewanlong.ui.view.NewsView;
import com.yewanlong.utils.SPUtils;
import com.yewanlong.utils.volley.RequestListener;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Lkn on 2016/7/18.
 */
public class NewsListImpl implements ActivityPresenterBundle, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, MyAdapter.OnItemClickListener {

    private Context content;
    private NewsView newsView;
    private MyAdapter adapter;
    private List<NewsDetail> newsDetailList = new ArrayList<>();
    private boolean isPull = false;

    public NewsListImpl(Context content, NewsView newsView) {
        this.content = content;
        this.newsView = newsView;
    }

    @Override
    public void onCreate() {
        if (adapter == null) {
            adapter = new MyAdapter(content, newsDetailList);
        }
        newsView.getListView().setAdapter(adapter);
        newsView.getNews();
        adapter.setItemClick(this);
    }

    @Override
    public void getIntent(Intent intent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                newsView.backActivity();
                break;
        }
    }


    public RequestListener<String> listener = new RequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            JSONObject jsonObject;
            int ret;
            switch (what) {
                case 1001:
                    jsonObject = JSON.parseObject(response);
                    ret = jsonObject.getInteger("ret");
                    if (ret == 0) {
                        String data = jsonObject.getString("data");
                        AccessToken accessToken = JSON.parseObject(data, AccessToken.class);
                        SPUtils.put(Constants.EXPIRES_IN, accessToken.getExpires_in());
                        SPUtils.put(Constants.OLD_TOKEN_TIME, System.currentTimeMillis());
                        SPUtils.put(Constants.ACCESS_TOKEN, accessToken.getAccess_token());
                        newsView.getList();
                    }
                    break;
                case 1002:
                    jsonObject = JSON.parseObject(response);
                    ret = jsonObject.getInteger("ret");
                    if (ret == 0) {
                        if (isPull) {
                            newsDetailList.clear();
                            newsView.endRefreshing();
                        } else {
                            newsView.endLoadingMore();
                        }
                        String data = jsonObject.getString("data");
                        NewsInfo newsInfo = JSON.parseObject(data, NewsInfo.class);
                        newsDetailList.addAll(newsInfo.getData());
                        adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void onError(int what, int code, String message) {
        }
    };

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isPull = true;
        newsView.getList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isPull = false;
        newsView.getList();
        return true;
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        UIHelper.startWebViewActivity(content, newsDetailList.get(position).getToutiao_url(), "新闻详情");
    }
}
