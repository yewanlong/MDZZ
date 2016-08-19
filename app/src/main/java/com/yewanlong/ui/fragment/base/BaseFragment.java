package com.yewanlong.ui.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yewanlong.AppContext;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lkn on 2016/7/15.
 */
public abstract class BaseFragment extends Fragment {
    public static AppContext app = AppContext.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent();
        if (isApplyEventBus()) EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    protected abstract void getIntent();

    protected abstract int layoutId();

    protected abstract void initView(View v);

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract boolean isApplyEventBus();

    @Override
    public void onDestroyView() {
        if (isApplyEventBus()) EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }
}
