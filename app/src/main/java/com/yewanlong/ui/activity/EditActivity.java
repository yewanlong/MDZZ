package com.yewanlong.ui.activity;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.yewanlong.R;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.bean.God;
import com.yewanlong.presenter.impl.EditAImpl;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.ui.view.EditAView;
import com.yewanlong.widget.spinner.NiceSpinner;

import java.util.List;

/**
 * Created by Lkn on 2016/7/14.
 */
public class EditActivity extends BaseSwipeBackActivity implements EditAView {
    private TextView titleTextView, mTimeTextView;
    private LinearLayout leftLayout, rightLayout, viewLayout;
    private NiceSpinner mSpinner;
    private EditAImpl mEditImpl;
    private MaterialEditText mTitleEdt;
    private MaterialEditText mPassWordEdt, mMemoInfo, mUserNameEdt;
    private ImageView rightImageView;
    private CheckBox mEyeChB;
    private Button mDeleteButton;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.tv_title);
        leftLayout = (LinearLayout) findViewById(R.id.layout_left);
        rightLayout = (LinearLayout) findViewById(R.id.layout_right);
        viewLayout = (LinearLayout) findViewById(R.id.view);
        mSpinner = (NiceSpinner) findViewById(R.id.spinner);
        mTitleEdt = (MaterialEditText) findViewById(R.id.title_edit_text);
        mUserNameEdt = (MaterialEditText) findViewById(R.id.userName);
        mPassWordEdt = (MaterialEditText) findViewById(R.id.passWord);
        mMemoInfo = (MaterialEditText) findViewById(R.id.memo);
        rightImageView = (ImageView) findViewById(R.id.iv_right);
        mEyeChB = (CheckBox) findViewById(R.id.checkbox);
        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
    }

    @Override
    protected void initData() {
        titleTextView.setText("添加");
        mEditImpl = new EditAImpl(this, this);
        mEditImpl.onCreate();
        mEditImpl.getIntent(getIntent());
    }

    @Override
    protected void initListener() {
        leftLayout.setOnClickListener(mEditImpl);
        rightLayout.setOnClickListener(mEditImpl);
        mTitleEdt.addTextChangedListener(mEditImpl);
        mUserNameEdt.addTextChangedListener(mEditImpl);
        mPassWordEdt.addTextChangedListener(mEditImpl);
        mMemoInfo.addTextChangedListener(mEditImpl);
        mEyeChB.setOnCheckedChangeListener(mEditImpl);
        mDeleteButton.setOnClickListener(mEditImpl);
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
    public void initSpinner(List<String> data) {
        mSpinner.attachDataSource(data);
        mSpinner.setOnItemSelectedListener(mEditImpl);
    }

    @Override
    public void backActivity(int type) {
        setResult(type);
        finish();
    }

    @Override
    public String getTitleName() {
        return mTitleEdt.getText().toString().trim();
    }

    @Override
    public String getUserName() {
        return mUserNameEdt.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return mPassWordEdt.getText().toString().trim();
    }

    @Override
    public String getMemoInfo() {
        return mMemoInfo.getText().toString().trim();
    }

    @Override
    public void rightVisible() {
        rightImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void rightGone() {
        rightImageView.setVisibility(View.GONE);
    }

    @Override
    public void showSnackToast(String msg) {
        UIHelper.showSnackBar(msg, titleTextView);
    }

    @Override
    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mPassWordEdt.getWindowToken(), 0);
    }

    @Override
    public void setPassWordVisible(boolean visible) {
        if (visible) {
            mPassWordEdt.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        } else {
            mPassWordEdt.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
        }
        mPassWordEdt.setSelection(getPassWord().length());
    }

    @Override
    public void initViewModel(God god, int positionType) {
        viewLayout.setFocusable(true);
        viewLayout.setFocusableInTouchMode(true);
        hideKeyBoard();
        mTitleEdt.setText(god.getTitle());
        mTitleEdt.setEnabled(false);
        mUserNameEdt.setText(god.getUserName());
        mPassWordEdt.setText(god.getPassWord());
        mMemoInfo.setText(god.getMemoInfo());
        mPassWordEdt.setTransformationMethod(HideReturnsTransformationMethod
                .getInstance());
        mUserNameEdt.setOnFocusChangeListener(mEditImpl);
        mPassWordEdt.setOnFocusChangeListener(mEditImpl);
        mMemoInfo.setOnFocusChangeListener(mEditImpl);
        mEyeChB.setChecked(false);
        mSpinner.setSelectedIndex(positionType);
        addEdtChangeListener();
        mDeleteButton.setVisibility(View.VISIBLE);
        mDeleteButton.setOnClickListener(mEditImpl);
    }

    @Override
    public void setToolBarTitle(int resId) {
        titleTextView.setText(getResources().getString(resId));
    }

    @Override
    public void setTime(String time) {
        mTimeTextView.setVisibility(View.VISIBLE);
        mTimeTextView.setText("创建于：" + time);
    }

    @Override
    public void initCreateModel() {
        mTitleEdt.requestFocus();
        showKeyBoard();
        addEdtChangeListener();
    }

    private void showKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void addEdtChangeListener() {
        mTitleEdt.addTextChangedListener(mEditImpl);
        mUserNameEdt.addTextChangedListener(mEditImpl);
        mPassWordEdt.addTextChangedListener(mEditImpl);
        mMemoInfo.addTextChangedListener(mEditImpl);
    }

    @Override
    public void showDialog(String msg, String positiveMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);//
        builder.setPositiveButton(positiveMsg, mEditImpl);
        builder.setNegativeButton("取消", mEditImpl);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        mEditImpl.comeBack();
    }
}
