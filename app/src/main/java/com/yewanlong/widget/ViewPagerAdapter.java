package com.yewanlong.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/5/28.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {

        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
