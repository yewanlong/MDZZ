package com.yewanlong.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.model.bean.taomodel.LangFemaleList;
import com.yewanlong.ui.activity.base.BaseActivity;
import com.yewanlong.ui.fragment.BelleImageFragment;
import com.yewanlong.utils.HttpUtils;
import com.yewanlong.utils.JsoupUtils;
import com.yewanlong.utils.volley.RequestListener;
import com.yewanlong.utils.volley.StringRequest;
import com.yewanlong.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lkn on 2016/8/12.
 */
public class LangDetailActivity extends ImageBaseActivity {
    private TextView tv_index;
    private HackyViewPager viewPager;
    private String title, url;
    private List<LangFemaleList> list = new ArrayList<>();
    private BelleListPagerAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_list_delle;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        StringRequest request = HttpUtils.getLangFameleDetais(listener, url);
        app.addRequestQueue(1002, request, this);
        tv_index = (TextView) findViewById(R.id.tv_index);
        viewPager = (HackyViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initData() {
        tv_index.setText(title);
        adapter = new BelleListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position+1==list.size()){
                    StringRequest request = HttpUtils.getLangFameleDetais(listener,list.get(position).getUrl());
                    app.addRequestQueue(1002, request, this);
                }
            }

            @Override
            public void onPageSelected(int position) {
                tv_index.setText(list.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;

    }

    public class BelleListPagerAdapter extends FragmentStatePagerAdapter {


        public BelleListPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BelleImageFragment().newInstance(list.get(position).getImageurl());
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


    public RequestListener<String> listener = new RequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            switch (what) {
                case 1002:
                    list.addAll(JsoupUtils.parserMeiziTuDeHtml(response));
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void onError(int what, int code, String message) {

        }
    };
}
