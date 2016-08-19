package com.yewanlong.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageLoaderUtil {
    private static ImageLoaderConfiguration IMImageLoaderConfig;
    private static ImageLoader IMImageLoadInstance;
    private static Map<Integer, Map<Integer, DisplayImageOptions>> avatarOptionsMaps = new HashMap<>();

    public static void initImageLoaderConfig(Context context) {
        try {
            File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                    CommonUtil.getSavePath(CommonUtil.FILE_SAVE_TYPE_IMAGE));
            File reserveCacheDir = StorageUtils.getCacheDirectory(context);

            int maxMemory = (int) (Runtime.getRuntime().maxMemory());
            // 使用最大可用内存值的1/8作为缓存的大小。
            int cacheSize = maxMemory / 8;
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mWm.getDefaultDisplay().getMetrics(metrics);

            IMImageLoaderConfig = new ImageLoaderConfiguration.Builder(context)
                    .memoryCacheExtraOptions(metrics.widthPixels, metrics.heightPixels)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .memoryCache(new UsingFreqLimitedMemoryCache(cacheSize))
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .diskCacheExtraOptions(metrics.widthPixels, metrics.heightPixels, null)
                    .diskCache(new UnlimitedDiskCache(cacheDir, reserveCacheDir, new Md5FileNameGenerator()))
                    .diskCacheSize(1024 * 1024 * 1024).diskCacheFileCount(1000).build();

            IMImageLoadInstance = ImageLoader.getInstance();
            IMImageLoadInstance.init(IMImageLoaderConfig);
        } catch (Exception e) {
        }
    }


    /**
     * 清除缓存
     */
    public static void clearCache() {
        try {
            if (IMImageLoadInstance != null) {
                IMImageLoadInstance.clearMemoryCache();
                IMImageLoadInstance.clearDiskCache();
            }
            if (null != avatarOptionsMaps) {
                avatarOptionsMaps.clear();
            }
        } catch (Exception e) {
        }
    }
}
