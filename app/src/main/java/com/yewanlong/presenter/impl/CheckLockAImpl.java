package com.yewanlong.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;


import com.yewanlong.R;
import com.yewanlong.databinding.ActivityCheckLockBinding;
import com.yewanlong.model.LockBean;
import com.yewanlong.presenter.ActivityPresenter;
import com.yewanlong.ui.activity.MainActivity;
import com.yewanlong.ui.view.CheckLockAView;
import com.yewanlong.utils.LockPatternUtils;

import java.util.List;

import com.yewanlong.widget.LockPatternView;

/**
 * Created by Clearlove on 16/1/14.
 */
public class CheckLockAImpl implements ActivityPresenter {

    private final Context mContext;
    private final ActivityCheckLockBinding mDataBinding;
    private LockBean lockBean;
    private final CheckLockAView mCheckView;

    public CheckLockAImpl(Context context, CheckLockAView view, ActivityCheckLockBinding binding) {
        mContext = context;
        mCheckView = view;
        mDataBinding = binding;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        lockBean = new LockBean(mContext.getString(R.string.check_default));
        lockBean.setColor(mContext.getResources().getColor(R.color.actionbar_title_color));
        mDataBinding.setLockWarn(lockBean);
        mCheckView.initLockPatternView();
    }

    @Override
    public void getIntent(Intent intent) {

    }

    public void check(List<LockPatternView.Cell> pattern) {
        if (pattern == null) return;

        LockPatternUtils instances = LockPatternUtils.getInstances(mContext);
        if (instances.checkPattern(pattern)) {
            mCheckView.readyGoThenKill(MainActivity.class);
        } else {
            mCheckView.lockDisplayError();
            lockBean.setColor(mContext.getResources().getColor(R.color.text_red));
            mDataBinding.setLockWarn(lockBean);
            mDataBinding.showText.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake_x));
        }
    }
}
