package com.yewanlong.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.ui.activity.base.BaseActivity;
import com.yewanlong.ui.fragment.BelleFragment;
import com.yewanlong.ui.fragment.LangMeiFragment;
import com.yewanlong.ui.fragment.SaoMeiFragment;
import com.yewanlong.widget.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lkn on 2016/8/15.
 */
public class BelleFragmentActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView titleTextView, oneTitleTextView, twoTitleTextView, threeTitleTextView;
    private TextView oneImageTextView, twoImageTextView, threeImageTextView;
    private LinearLayout leftLayout, oneLayout, twoLayout, threeLayout;
    private ViewPager viewPager;
    public static List<Fragment> fragments;
    private ViewPagerAdapter viewPagerAdapter;
    private int currentPage = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment_belle;
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.tv_title);
        leftLayout = (LinearLayout) findViewById(R.id.layout_left);

        oneLayout = (LinearLayout) findViewById(R.id.layout_one);
        twoLayout = (LinearLayout) findViewById(R.id.layout_two);
        threeLayout = (LinearLayout) findViewById(R.id.layout_three);

        oneTitleTextView = (TextView) findViewById(R.id.title_one);
        twoTitleTextView = (TextView) findViewById(R.id.title_two);
        threeTitleTextView = (TextView) findViewById(R.id.title_three);

        oneImageTextView = (TextView) findViewById(R.id.image_one);
        twoImageTextView = (TextView) findViewById(R.id.image_two);
        threeImageTextView = (TextView) findViewById(R.id.image_three);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
    }

    @Override
    protected void initData() {
        titleTextView.setText("萌妹儿");
        LangMeiFragment langMeiFragment = new LangMeiFragment();
        SaoMeiFragment saoMeiFragment = new SaoMeiFragment();
        BelleFragment belleFragment = new BelleFragment();
        fragments.add(langMeiFragment);
        fragments.add(saoMeiFragment);
        fragments.add(belleFragment);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void initListener() {
        leftLayout.setOnClickListener(this);
        oneLayout.setOnClickListener(this);
        twoLayout.setOnClickListener(this);
        threeLayout.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                finish();
                break;
            case R.id.layout_one:
                if (currentPage != 0)
                    viewPager.setCurrentItem(0);
                break;
            case R.id.layout_two:
                if (currentPage != 1)
                    viewPager.setCurrentItem(1);
                break;
            case R.id.layout_three:
                if (currentPage != 2)
                    viewPager.setCurrentItem(2);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changePageView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void changePageView(int index) {
        setImageColor(index);
        currentPage = index;
        switch (index) {
            case 0: {
                fragments.get(index);
                break;
            }
            case 1: {
                fragments.get(index);
                break;
            }
            case 2: {
                fragments.get(index);
                break;
            }
            default:
                break;
        }
    }

    public void setImageColor(int index) {
        if (index == 0) {
            oneImageTextView.setVisibility(View.VISIBLE);
            twoImageTextView.setVisibility(View.INVISIBLE);
            threeImageTextView.setVisibility(View.INVISIBLE);
            oneTitleTextView.setTextColor(getColorPrimary());
            twoTitleTextView.setTextColor(getResources().getColor(R.color.text_color));
            threeTitleTextView.setTextColor(getResources().getColor(R.color.text_color));
        } else if(index==1) {
            oneImageTextView.setVisibility(View.INVISIBLE);
            twoImageTextView.setVisibility(View.VISIBLE);
            threeImageTextView.setVisibility(View.INVISIBLE);
            oneTitleTextView.setTextColor(getResources().getColor(R.color.text_color));
            twoTitleTextView.setTextColor(getColorPrimary());
            threeTitleTextView.setTextColor(getResources().getColor(R.color.text_color));
        }else{
            oneImageTextView.setVisibility(View.INVISIBLE);
            twoImageTextView.setVisibility(View.INVISIBLE);
            threeImageTextView.setVisibility(View.VISIBLE);
            twoTitleTextView.setTextColor(getResources().getColor(R.color.text_color));
            oneTitleTextView.setTextColor(getResources().getColor(R.color.text_color));
            threeTitleTextView.setTextColor(getColorPrimary());
        }
    }
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
