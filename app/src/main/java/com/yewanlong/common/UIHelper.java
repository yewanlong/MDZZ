package com.yewanlong.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yewanlong.ui.activity.BelleDetailActivity;
import com.yewanlong.ui.activity.BelleListActivity;
import com.yewanlong.ui.activity.EditActivity;
import com.yewanlong.ui.activity.LangDetailActivity;
import com.yewanlong.ui.activity.MemorandumActivity;
import com.yewanlong.ui.activity.NewsListActivity;
import com.yewanlong.ui.activity.SettingActivity;
import com.yewanlong.ui.activity.WebViewActivity;
import com.yewanlong.utils.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by Lkn on 2016/7/13.
 */
public class UIHelper {
    private static Toast mToast;
    private static long DOUBLE_CLICK_TIME = 0L;

    public static void showToast(Context context, String text) {
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static boolean onBackPress(View view) {
        if (System.currentTimeMillis() - DOUBLE_CLICK_TIME > 2000) {
            DOUBLE_CLICK_TIME = System.currentTimeMillis();
            showSnackBar("再按一次退出~~", view);
            return false;
        } else {
            ImageLoaderUtil.clearCache();
            return true;
        }
    }

    public static void showSnackBar(String msg, View view) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showDialog(Context activity, DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener,
                                                 String title, String posStr, String negStr, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setPositiveButton(posStr == null ? "" : posStr, posListener);
        builder.setNegativeButton(negStr == null ? "" : negStr, negListener);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.show();
    }

    public static void startSettingActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    public static void startEditActivity(Activity activity) {
        Intent intent = new Intent(activity, EditActivity.class);
        activity.startActivity(intent);
    }

    public static void startMemorandumActivity(Activity activity) {
        Intent intent = new Intent(activity, MemorandumActivity.class);
        activity.startActivity(intent);
    }
    public static void startWebViewActivity(Context activity,String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("URL",url);
        activity.startActivity(intent);
    }
    public static void startWebViewActivity(Context activity,String url,String title) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("URL",url);
        intent.putExtra("TITLE",title);
        activity.startActivity(intent);
    }
    public static void startNewsListActivity(Context activity) {
        Intent intent = new Intent(activity, NewsListActivity.class);
        activity.startActivity(intent);
    }
    public static void startBelleDetailActivity(Context activity,String url,String title) {
        Intent intent = new Intent(activity, BelleDetailActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        activity.startActivity(intent);
    }
    public static void startBelleListActivity(Context activity, ArrayList<String> list) {
        Intent intent = new Intent(activity, BelleListActivity.class);
        intent.putStringArrayListExtra("list",list);
        activity.startActivity(intent);
    }
    public static void startLangDetailActivity(Context activity,String url,String title) {
        Intent intent = new Intent(activity, LangDetailActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        activity.startActivity(intent);
    }
}
