package com.yewanlong.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class HackyViewPager extends ViewPager {

    public HackyViewPager(Context context) {

        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

            return super.canScroll(v, checkV, dx, x, y);
    }
}
