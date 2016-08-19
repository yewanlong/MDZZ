package com.yewanlong.presenter.impl;

import android.app.Notification;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.view.View;

import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.eventbus.EventCenter;
import com.yewanlong.presenter.FragmentPresenter;
import com.yewanlong.ui.activity.CreateLockActivity;
import com.yewanlong.ui.service.apdate.UpdateService;
import com.yewanlong.ui.view.SettingAView;
import com.yewanlong.utils.AlertDialogUtil;
import com.yewanlong.utils.SPUtils;
import com.yewanlong.utils.Utils;
import com.yewanlong.utils.interfaces.AlertDialogInterface;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by yezhidong on 2016/1/27.
 */
public class SettingFImpl implements FragmentPresenter, AlertDialogInterface {

    private final Context mContext;
    private final SettingAView settingAView;
    private Boolean isOpen;
    private boolean isOpenShow;

    public SettingFImpl(Context context, SettingAView view) {
        mContext = context;
        settingAView = view;
    }

    @Override
    public void onFirstUserVisible() {
        settingAView.findView();
        isOpen = (Boolean) SPUtils.get(Constants.SETTING.OPEN_GESTURE, true);
        isOpenShow = (Boolean) SPUtils.get(Constants.SETTING.OPEN_PASS_WORD_SHOW, true);
        settingAView.initState(isOpen);
        settingAView.initOpenShow(isOpenShow);
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {
    }

    public void onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (TextUtils.equals(key, "开启手势密码")) {
            isOpen = !isOpen;
            SPUtils.put(Constants.SETTING.OPEN_GESTURE, isOpen);
        } else if (TextUtils.equals(key, "首页密码可见")) {
            isOpenShow = !isOpenShow;
            SPUtils.put(Constants.SETTING.OPEN_PASS_WORD_SHOW, isOpenShow);
        } else if (TextUtils.equals(key, "修改手势密码")) {
            Intent intent = new Intent(mContext, CreateLockActivity.class);
            intent.putExtra("CREATE_MODE", Constants.UPDATE_GESTURE);
            settingAView.readyGo(CreateLockActivity.class, intent);
        } else if (TextUtils.equals(key, "更换主题")) {
            settingAView.showChangeThemeDialog();
        } else if (TextUtils.equals(key, "意见反馈")) {
        } else if (TextUtils.equals(key, "给应用评分")) {
            UIHelper.showToast(mContext, "应用评分");
            giveFavor();
        } else if (TextUtils.equals(key, "检测更新")) {
            if (Utils.isWifi(mContext)) {
                updateService();
            } else {
                AlertDialogUtil.getInstance().DoubleAlertDialog(mContext, "当前属于移动网络，是否下载？", this);
            }
        }
    }

    public void onThemeChoose(int position) {
        SPUtils.put(mContext.getResources().getString(R.string.change_theme_key), position);
        EventBus.getDefault().post(new EventCenter(Constants.EVEN_BUS.CHANGE_THEME));
        settingAView.reCreate();
    }

    private void updateService() {
        UIHelper.showToast(mContext, "检测更新");
        UpdateService.Builder.create(Constants.URL_DOWNLOAD).build(mContext);
    }

    public void updateFlag() {
        UpdateService.Builder.create(Constants.URL_DOWNLOAD)
                .setStoreDir("update/flag")
                .setDownloadSuccessNotificationFlag(Notification.DEFAULT_ALL)
                .setDownloadErrorNotificationFlag(Notification.DEFAULT_ALL)
                .build(mContext);
    }

    public void updateStore() {
        UpdateService.Builder.create(Constants.URL_DOWNLOAD)
                .setStoreDir("update/store")
                .build(mContext);
    }

    private void giveFavor() {
        try {
            Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buttonSelectedListener(Object args) {
        updateService();
    }
}
