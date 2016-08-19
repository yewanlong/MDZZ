package com.yewanlong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.yewanlong.R;
import com.yewanlong.ui.activity.base.BaseBindingActivity;
import com.yewanlong.databinding.ActivityCreateLockBinding;
import com.yewanlong.presenter.impl.CreateLockActivityImpl;
import com.yewanlong.ui.view.CreateLockAView;

import java.util.List;

import com.yewanlong.widget.LockPatternView;

/**
 * Created by Lkn on 2016/7/12.
 */
public class CreateLockActivity extends BaseBindingActivity implements CreateLockAView, LockPatternView.OnPatternListener {
    private LockPatternView mLockPatternView;
    private ActivityCreateLockBinding binding;
    private CreateLockActivityImpl mLockActivity;

    @Override
    protected int getContentView() {
        return R.layout.activity_create_lock;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLockPatternView = (LockPatternView) findViewById(R.id.lockPatternView);
        binding = (ActivityCreateLockBinding) this.mDataBinding;
        mLockActivity = new CreateLockActivityImpl(this, this, binding);
        mLockActivity.onCreate(savedInstanceState);
        mLockActivity.getIntent(getIntent());
    }

    @Override
    protected void initView() {
    }


    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return  TransitionMode.RIGHT;
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
    public void setResults(int isSuccess) {
        setResult(isSuccess);
    }

    @Override
    public void clearPattern() {
        mLockPatternView.clearPattern();
    }

    @Override
    public void readyGoThenKill(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    @Override
    public void kill() {
        finish();
    }

    @Override
    public void showSnackBar(String msg) {
        Snackbar.make(mLockPatternView, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPatternStart() {
        mLockActivity.fingerPress();
    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        mLockActivity.check(pattern);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mLockActivity.onBack();
    }
}
