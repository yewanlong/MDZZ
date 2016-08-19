package com.yewanlong.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.ui.activity.base.BaseActivity;
import com.yewanlong.ui.fragment.BelleImageFragment;
import com.yewanlong.widget.HackyViewPager;
import com.yewanlong.widget.XViewPager;

import java.util.ArrayList;

/**
 * Created by Lkn on 2016/8/12.
 */
public class BelleListActivity extends ImageBaseActivity {
    private TextView tv_index;
    private HackyViewPager viewPager;
    public ArrayList<String> list = new ArrayList<>();
    private int page = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_list_delle;
    }

    @Override
    protected void initView() {
        list.addAll(getIntent().getStringArrayListExtra("list"));
        tv_index = (TextView) findViewById(R.id.tv_index);
        viewPager = (HackyViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initData() {
        tv_index.setText(page + "/" + list.size());
        viewPager.setAdapter(new BelleListPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tv_index.setText((position + 1) + " / " + list.size());
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
            return new BelleImageFragment().newInstance(list.get(position));
        }

        @Override
        public int getCount() {

            return list.size();
        }
    }
}
