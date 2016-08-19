package com.yewanlong.presenter.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.yewanlong.R;
import com.yewanlong.model.bean.God;
import com.yewanlong.model.Realm.RealmHelper;
import com.yewanlong.presenter.ActivityPresenterBundle;
import com.yewanlong.ui.view.EditAView;
import com.yewanlong.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Lkn on 2016/7/14.
 */
public class EditAImpl implements ActivityPresenterBundle, AdapterView.OnItemSelectedListener, View.OnClickListener,
        TextWatcher, CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener, DialogInterface.OnClickListener {
    private final EditAView mEditAView;
    private final Context context;
    private int mPosition = 0;
    private int createMode, p;
    private God mGodInfo;
    private boolean isEdit;
    private String mPositiveButtonMsg;

    public EditAImpl(Context context, EditAView view) {
        this.context = context;
        this.mEditAView = view;
    }

    @Override
    public void onCreate() {
        String[] stringArray = context.getResources().getStringArray(R.array.spinner_item);
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : stringArray) {
            arrayList.add(str);
        }
        mEditAView.initSpinner(arrayList);
    }

    @Override
    public void getIntent(Intent intent) {
        createMode = intent.getIntExtra("CREATE_MODE", 1);
        switch (createMode) {
            case 0:// 查看
                p = intent.getIntExtra("position", 0);
                // 密码类型
                ArrayList<God> selector = selector();
                mGodInfo = selector.get(p);
                mEditAView.initViewModel(mGodInfo, mGodInfo.getGodType());
                mEditAView.setToolBarTitle(R.string.view_mode);
                mEditAView.setTime(TimeUtils.getTime(mGodInfo.getTime()));
                isEdit = false;
                break;
            case 1:// 添加
                mEditAView.initCreateModel();
                break;
        }
    }

    private ArrayList<God> selector() {
        return RealmHelper.getInstances(context).selector(context);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPosition = position;

    }

    public void comeBack() {
        if (isEdit) {
            String userName = mEditAView.getUserName();
            String passWord = mEditAView.getPassWord();
            mEditAView.hideKeyBoard();
            if (!TextUtils.equals(userName, mGodInfo.getUserName()) || !TextUtils.equals(passWord, mGodInfo.getPassWord())) {
                mPositiveButtonMsg = "保存";
                mEditAView.showDialog("密码还未保存，是否先保存在退出", mPositiveButtonMsg);
            } else {
                mEditAView.backActivity(1);
            }
        } else {
            mEditAView.hideKeyBoard();
            mEditAView.backActivity(0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                comeBack();
                break;
            case R.id.layout_right:
                saveData();
                break;
            case R.id.deleteButton:
                mPositiveButtonMsg = "确定";
                mEditAView.showDialog("将永久删除该密码备忘记录~~", mPositiveButtonMsg);
                break;
        }
    }

    private void saveData() {
        String titleName = mEditAView.getTitleName();
        String userName = mEditAView.getUserName();
        String passWord = mEditAView.getPassWord();
        String memoInfo = mEditAView.getMemoInfo();
        God god = new God(mPosition, titleName, userName, passWord, TimeUtils.getCurrentTimeInLong(), memoInfo);
        switch (createMode) {
            case 0:
                if (!RealmHelper.update(context, god)) {
                    mEditAView.showSnackToast("修改失败");
                    mEditAView.hideKeyBoard();
                    return;
                }
                break;
            case 1:
                if (RealmHelper.save(context, god)) {
                    mEditAView.showSnackToast("保存失败，已经存在-" + god.getTitle() + "-的标题");
                    mEditAView.hideKeyBoard();
                    return;
                }
                break;
        }

        mEditAView.hideKeyBoard();
        mEditAView.backActivity(1);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String titleName = mEditAView.getTitleName();
        String userName = mEditAView.getUserName();
        String passWord = mEditAView.getPassWord();
        if (!TextUtils.isEmpty(passWord)) {
            if (!TextUtils.isEmpty(titleName) && !TextUtils.isEmpty(userName)) {
                mEditAView.rightVisible();
            } else {
                mEditAView.rightGone();
            }
        } else {
            mEditAView.rightGone();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mEditAView.setPassWordVisible(isChecked);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            isEdit = true;
            mEditAView.setToolBarTitle(R.string.edit_mode);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == dialog.BUTTON_POSITIVE) {
            if (TextUtils.equals(mPositiveButtonMsg, "确定")) {
                RealmHelper.delete(context, mGodInfo, p);
                mEditAView.backActivity(1);
            } else {
                saveData();
            }
        } else if (which == dialog.BUTTON_NEGATIVE) {
            if (!TextUtils.equals(mPositiveButtonMsg, "确定")) {
                mEditAView.hideKeyBoard();
                mEditAView.backActivity(0);
            }
        }
    }
}
