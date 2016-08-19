package com.yewanlong.ui.activity;

import android.content.Intent;
import android.os.Bundle;


import com.yewanlong.R;
import com.yewanlong.databinding.ActivityCheckLockBinding;
import com.yewanlong.presenter.impl.CheckLockAImpl;
import com.yewanlong.ui.activity.base.BaseBindingActivity;
import com.yewanlong.ui.view.CheckLockAView;


import java.util.List;

import com.yewanlong.widget.LockPatternView;


public class CheckLockActivity extends BaseBindingActivity implements CheckLockAView, LockPatternView.OnPatternListener {

    private  LockPatternView mLockPatternView;
    private CheckLockAImpl mCheckLockA;

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }


    @Override protected int getContentView() {
        return R.layout.activity_check_lock;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLockPatternView = (LockPatternView) findViewById(R.id.lockPatternView);
        mCheckLockA = new CheckLockAImpl(this, this, (ActivityCheckLockBinding) mDataBinding);
        mCheckLockA.onCreate(savedInstanceState);
        mLockPatternView = (LockPatternView) findViewById(R.id.lockPatternView);
    }

    @Override
    protected void initView() {

    }
    @Override
    protected void initData() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public void initLockPatternView() {
        mLockPatternView.setOnPatternListener(this);
    }

    @Override
    public void lockDisplayError() {
        mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
    }

    @Override
    public void readyGoThenKill(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    @Override
    public void kill() {

    }

    @Override
    public void showSnackBar(String msg) {

    }

    @Override
    public void onPatternStart() {
    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        mCheckLockA.check(pattern);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
