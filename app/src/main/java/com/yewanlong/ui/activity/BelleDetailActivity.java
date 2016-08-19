package com.yewanlong.ui.activity;


import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yewanlong.R;
import com.yewanlong.common.UIHelper;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.utils.CommonUtil;
import com.yewanlong.utils.ImmersiveUtil;
import com.yewanlong.utils.Options;
import com.yewanlong.utils.ShareUtil;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Lkn on 2016/8/12.
 */
public class BelleDetailActivity extends ImageBaseActivity implements View.OnClickListener {
    private String title, url;
    private Toolbar toolbar;
    private AppBarLayout mAppBarLayout;
    private ImageView imageView;
    private TextView imageErrorTextView;
    public static Bitmap headBitmap;
    private boolean isHide;
    @Override
    protected int getContentView() {
        return R.layout.activity_belle_detail;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageErrorTextView = (TextView) findViewById(R.id.tv_image_error);
    }

    @Override
    protected void initData() {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        ImageLoader.getInstance().displayImage(url, imageView, Options
                .clubImageOptions(R.color.black), new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                imageErrorTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imageErrorTextView.setVisibility(View.GONE);
                headBitmap = bitmap;
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }


    @Override
    protected void initListener() {
        imageView.setOnClickListener(this);
        imageErrorTextView.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        mAppBarLayout.setAlpha(0.5f);
        toolbar.setBackgroundResource(R.color.black);
        mAppBarLayout.setBackgroundResource(R.color.black);
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_meizi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_fuli_share:
                ShareUtil.sharePic(ImageLoader.getInstance().getDiskCache().get(url).getPath(), title, this);
                return true;
            case R.id.action_fuli_save:
                try {
                    String meiNv = CommonUtil.saveFile(this, headBitmap, "meinv_" + new Random().nextInt(100000) + ".jpg");
                    UIHelper.showToast(this, meiNv);
                } catch (IOException e) {
                    UIHelper.showToast(this, "图片保存失败");
                    e.printStackTrace();
                }
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    protected void hideOrShowToolbar() {

        if (isHide) {
            //显示
            ImmersiveUtil.exit(this);
            mAppBarLayout.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
            isHide = false;
        } else {
            //隐藏
            ImmersiveUtil.enter(this);
            mAppBarLayout.animate()
                    .translationY(-mAppBarLayout.getHeight())
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
            isHide = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView:
            case R.id.tv_image_error:
//                hideOrShowToolbar();
                break;
        }
    }
}
