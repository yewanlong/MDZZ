package com.yewanlong.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CommonUtil {
    public static final int FILE_SAVE_TYPE_IMAGE = 0X00013;
    public static final int CAMERA = 100;
    public static final int ALBUM = 101;
    public static final int CAMERA_CUT = 200;
    public static String fileName = "";

    public static String getSavePath(int type) {
        String path;
        String folder = (type == FILE_SAVE_TYPE_IMAGE) ? "images" : "audio";
        if (CommonUtil.checkSDCard()) {
            path = Environment.getExternalStorageDirectory().toString() + File.separator + "YeWanLong" + File.separator
                    + folder + File.separator;

        } else {
            path = Environment.getDataDirectory().toString() + File.separator + "YeWanLong" + File.separator + folder
                    + File.separator;
        }
        return path;
    }

    /**
     * @return
     * @Description 判断存储卡是否存在
     */
    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static String getImageSavePath(String fileName) {

        if (TextUtils.isEmpty(fileName)) {
            return null;
        }

        final File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "YeWanLong" + File.separator + "images");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.getAbsolutePath() + File.separator + fileName;
    }

    public static void cropImageUri(Activity activity, Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, requestCode);
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        if (checkSDCard()) {
            try {
                File file = new File(pathString);
                if (file.exists()) {
                    bitmap = BitmapFactory.decodeFile(pathString);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return bitmap;
    }

    /**
     * 保存图片
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Context content, Bitmap bm, String fileName) throws IOException {
        final File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "YeWanLong" + File.separator + "images");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File myCaptureFile = new File(folder.getAbsolutePath() + File.separator + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        Uri uri = Uri.fromFile(myCaptureFile);
        Intent mIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        content.sendBroadcast(mIntent);
        return myCaptureFile.getPath();
    }

    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public static boolean fileIsExists(String fileName) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
