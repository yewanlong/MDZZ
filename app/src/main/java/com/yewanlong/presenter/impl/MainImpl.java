package com.yewanlong.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.bean.AccessToken;
import com.yewanlong.model.bean.NewsDetail;
import com.yewanlong.model.bean.NewsInfo;
import com.yewanlong.presenter.ActivityPresenterBundle;
import com.yewanlong.ui.adapter.listview.MyAdapter;
import com.yewanlong.ui.view.MainAView;
import com.yewanlong.utils.Options;
import com.yewanlong.utils.SPUtils;
import com.yewanlong.utils.ScreenUtil;
import com.yewanlong.utils.volley.RequestListener;
import com.yewanlong.widget.circle.ImageCycleView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Lkn on 2016/7/18.
 */
public class MainImpl implements ActivityPresenterBundle,View.OnClickListener,MyAdapter.OnItemClickListener,BGARefreshLayout.BGARefreshLayoutDelegate{

    private Context context;
    private MainAView mainAView;
    private MyAdapter adapter;
    private List<NewsDetail> newsDetailList = new ArrayList<>();

    public MainImpl(Context context, MainAView mainAView) {
        this.context = context;
        this.mainAView = mainAView;
    }

    @Override
    public void onCreate() {
        initDataCycle();
        mainAView.getNews();
        if (adapter == null) {
            adapter = new MyAdapter(context, newsDetailList);
        }
        mainAView.getListView().setAdapter(adapter);
        adapter.setItemClick(this);
    }

    @Override
    public void getIntent(Intent intent) {

    }

    /**
     * 初始化轮播图
     */
    public void initCycleView(ArrayList<String> imageDescList, ArrayList<String> urlList) {
        LinearLayout.LayoutParams cParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (ScreenUtil.getInstance(context).getWidth() * 0.35));
        mainAView.mCycleView().setLayoutParams(cParams);
        ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void onImageClick(int position, View imageView) {
                if (position == 0) {
                    UIHelper.startWebViewActivity(context, "http://www.baidu.com/");
                } else if (position == 1) {
                    UIHelper.startWebViewActivity(context, "http://qiqu.uc.cn/?uc_param_str=frpfvedncpssntnwbipr#!/index/index");
                } else if (position == 2) {
                    UIHelper.startWebViewActivity(context, "http://www.budejie.com/");
                } else {
                    UIHelper.startWebViewActivity(context, "http://www.qiushibaike.com");
                }
            }

            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                /**在此方法中，显示图片，可以用自己的图片加载库，也可以用本demo中的（Imageloader）*/
                ImageLoader.getInstance().displayImage(imageURL, imageView, Options
                        .options(R.mipmap.ic_friends_sends_pictures_no));
            }
        };
        mainAView.mCycleView().setImageResources(imageDescList, urlList, mAdCycleViewListener);
        mainAView.mCycleView().startImageCycle();
    }

    private void initDataCycle() {
        ArrayList<String> imageDescList = new ArrayList<>();
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("http://img4.imgtn.bdimg.com/it/u=3626000301,3419146151&fm=21&gp=0.jpg");
        urlList.add("http://www.datoraudio.com/upload/day_140402/201404021045318195.jpg");
        urlList.add("http://image.tianjimedia.com/uploadImages/2015/170/20/6UZI59OV34GS_1000x500.jpg");
        urlList.add("http://img.ausingroup.com/Uploads/Picture/2015-12-28/5680cfe38eebc.jpg");
        imageDescList.add("百度一下");
        imageDescList.add("奇趣百科");
        imageDescList.add("百思不得姐");
        imageDescList.add("糗事百科");
        initCycleView(imageDescList, urlList);
    }

    public RequestListener<String> listener = new RequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            JSONObject jsonObject;   int ret;
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
                        mainAView.getList();
                    }
                    break;
                case 1002:
                    jsonObject = JSON.parseObject(response);
                    ret = jsonObject.getInteger("ret");
                    if (ret == 0) {
                        String data = jsonObject.getString("data");
                        mainAView.endRefreshing();
                        newsDetailList.clear();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_news_title:
                mainAView.startNewIntent();
                break;
        }
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        UIHelper.startWebViewActivity(context,newsDetailList.get(position).getToutiao_url(),"新闻详情");
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mainAView.getNews();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
