package com.yewanlong.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;

import com.yewanlong.utils.interfaces.AlertDialogInterface;
import com.yewanlong.utils.interfaces.DoubleAlertDialogInterface;


/**
 * 提示框帮助类
 * */
public class AlertDialogUtil {

	private static final String TAG = "AlertDialogUtil";
	private static AlertDialogUtil instance = new AlertDialogUtil();

	public static AlertDialogUtil getInstance() {
		if (instance == null) {
			instance = new AlertDialogUtil();
		}
		return instance;
	}

	/**
	 * 单按钮通用提示框
	 * 
	 * @param context
	 * @param str
	 */
	public void SingleAlertDialog(Context context, String str) {
		SingleAlertDialog(context, str, null);
	}

	/**
	 * 单按钮通用提示框
	 * 
	 * @param context
	 * @param str
	 * @param callback
	 */
	public void SingleAlertDialog(Context context, String str, final AlertDialogInterface callback) {
		new AlertDialog.Builder(context).setCancelable(true).setMessage(str)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null) {
							callback.buttonSelectedListener(which);
						}
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * 双按钮用户操作提示框
	 * 
	 * @param context
	 * @param strId
	 * @param callback
	 */
	public void DoubleAlertDialog(Context context, String strId, final AlertDialogInterface callback) {
		new AlertDialog.Builder(context).setCancelable(true).setMessage(strId)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null) {
							callback.buttonSelectedListener(which);
						}
						dialog.dismiss();
					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * 双按钮用户操作提示框
	 * 
	 * @param context
	 * @param strId
	 * @param callback
	 */
	public void DoubleAlertDialog(Context context, String strId, final DoubleAlertDialogInterface callback) {
		new AlertDialog.Builder(context).setTitle("提示信息").setCancelable(true).setMessage(strId)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null) {
							callback.confirmClick(which);
						}
						dialog.dismiss();
					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (callback != null) {
							callback.cancelClick(which);
						}
						dialog.dismiss();
					}
				}).show();
	}
}
