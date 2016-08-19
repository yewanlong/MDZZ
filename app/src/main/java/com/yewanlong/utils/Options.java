package com.yewanlong.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * @author vincent
 * @version V1.0
 * @Title: Options.java
 * @Description:
 * @date 2014-11-21 上午9:44:38
 */
public class Options {

    /**
     * 默认图片的option
     *
     * @param resourceId
     * @return
     */
    public static DisplayImageOptions options(int resourceId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(resourceId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(resourceId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(resourceId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(false)// 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型//
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .build();// 构建完成
        return options;
    }

    /**
     * 医院加圆角的默认图片的option
     *
     * @return
     */
    public static DisplayImageOptions optionsWithRound(int resourceId, int roundValue) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(resourceId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(resourceId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(resourceId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(false)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(false)// 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(roundValue)).build();// 构建完成

        return options;
    }

    public static DisplayImageOptions clubImageOptions(int resourceId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(resourceId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(resourceId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(resourceId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
//				.displayer(new FadeInBitmapDisplayer(100))
                .build();// 构建完成
        return options;
    }
}
