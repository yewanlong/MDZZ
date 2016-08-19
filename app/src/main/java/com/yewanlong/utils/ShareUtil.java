package com.yewanlong.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.net.URL;

public class ShareUtil {

    /**
     * 分享链接
     *
     * @param url
     * @param title
     */
    public static void shareLink(String url, String title, Context context) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "来自「叶烨」的分享:" + url);
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 分享图片
     *
     * @param uriString
     */
    public static void sharePic(String  uriString, String desc, Context context) {
        Uri uri = Uri.parse(uriString);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, desc));
    }
}
