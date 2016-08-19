package com.yewanlong.ui.view;

import com.yewanlong.model.bean.God;

import java.util.List;

/**
 * Created by Lkn on 2016/7/14.
 */
public interface EditAView {
    void initSpinner(List<String> data);
    void backActivity(int type);
    String getTitleName();
    String getUserName();
    String getPassWord();
    String getMemoInfo();
    void rightVisible();
    void rightGone();
    void  showSnackToast(String msg);
    void hideKeyBoard();
    void setPassWordVisible(boolean visible);
    void initViewModel(God god, int positionType);
    void setToolBarTitle(int resId);
    void setTime(String time);
    void initCreateModel();
    void showDialog(String msg, String positiveMsg);
}
