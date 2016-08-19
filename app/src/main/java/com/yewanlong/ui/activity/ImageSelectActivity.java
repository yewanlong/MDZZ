package com.yewanlong.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yewanlong.R;
import com.yewanlong.common.UIHelper;
import com.yewanlong.ui.activity.base.BaseActivity;
import com.yewanlong.utils.CommonUtil;
import com.yewanlong.utils.Options;
import com.yewanlong.utils.VLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImageSelectActivity extends BaseActivity implements View.OnClickListener {
    private GridView gridView;
    private LinearLayout backLayout;
    private TextView centerTextView, previewTextView, confirmTextView,cancelTextView;
    private ArrayList<HashMap<String, String>> list;
    private ImageSelectAdapter adapter;
    private int maxSelectedImageNum = 9;
    private boolean isCut = true;
    private RelativeLayout bottomLayout;
    private int selectType;//图片选择模式，1表示单选，2表示多选
    private String filePath = "";
    /**
     * 多选：可选择多张图片
     *
     * @param context
     * @param maxNum  最多可选择图片数量
     */
    public static void startImageSelectAct(Context context, int maxNum) {
        Intent intent = new Intent(context, ImageSelectActivity.class);
        intent.putExtra("maxNum", maxNum);//最多可选择图片数量，如果传1，则表示单选。不是1则是多选
        intent.putExtra("isCut", false);//单选图片时，是否裁剪
        intent.putExtra("selectType", 2);//多选
        context.startActivity(intent);
    }

    /**
     * 单选：只可以选择一张图片
     * @param context
     * @param isCut 选择图片之后是否裁剪
     */
    public static void startImageSelectAct(Context context, boolean isCut, int requestCode) {
        Intent intent = new Intent(context, ImageSelectActivity.class);
        intent.putExtra("maxNum", 1);//最多可选择图片数量，如果传1，则表示单选。不是1则是多选
        intent.putExtra("isCut", isCut);//单选图片时，是否裁剪
        intent.putExtra("selectType", 1);//单选
        ((Activity)context).startActivityForResult(intent, requestCode);
    }


    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_images;
    }

    public void initView() {
        maxSelectedImageNum = getIntent().getIntExtra("maxNum", 1);
        selectType = getIntent().getIntExtra("selectType", 1);
        isCut = getIntent().getBooleanExtra("isCut", true);

        gridView = (GridView) findViewById(R.id.gridview);

        backLayout = (LinearLayout) findViewById(R.id.layout_back);

        bottomLayout = (RelativeLayout) findViewById(R.id.layout_bottom);

        centerTextView = (TextView) findViewById(R.id.center_content);
        previewTextView = (TextView) findViewById(R.id.preview);
        confirmTextView = (TextView) findViewById(R.id.confirm);
        cancelTextView= (TextView) findViewById(R.id.cancel);
    }

    public void initData() {
        if (selectType == 1) {
            bottomLayout.setVisibility(View.GONE);
        } else {
            bottomLayout.setVisibility(View.VISIBLE);
        }
        centerTextView.setText("最近照片");
        confirmTextView.setText("确定(0" + "/" + maxSelectedImageNum + ")");
        list = new ArrayList<>();
        getImages();

        adapter = new ImageSelectAdapter(this, list);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        confirmTextView.setOnClickListener(this);
        backLayout.setOnClickListener(this);
        previewTextView.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        gridView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                gotoSubmitPost();
                break;
            case R.id.preview:
                if(adapter.getSelectedImages("file://").size()>0){
//                    Intent i = new Intent(ImageSelectActivity.this, PreviewMultiImagesActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putStringArrayList(IntentConstant.CUR_MESSAGE, adapter.getSelectedImages("file://"));
//                    bundle.putInt("currentItem", 0);
//                    i.putExtras(bundle);
//                    startActivity(i);
//                    overridePendingTransition(R.anim.tt_image_enter, R.anim.tt_stay);
                }
                break;
            case R.id.layout_back:
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            default:
                break;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.framelayout);
            if (selectType == 1) {
                //单选
                if (isCut) {
                  cropImageUri(Uri.parse("file://" + list.get(position).get("path")), 400, 400, CommonUtil.ALBUM);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("filePath", list.get(position).get("path"));
                    setResult(199, intent);
                    finish();
                }
            } else {
                //多选
                if (frameLayout.getVisibility() == View.GONE) {
                    if (adapter.getSelectedNum() >= maxSelectedImageNum) {
                        UIHelper.showToast(ImageSelectActivity.this, "最多只能选择" + maxSelectedImageNum + "张图片");
                    } else {
                        frameLayout.setVisibility(View.VISIBLE);
                        adapter.changeSelectPosition(position);
                    }
                } else {
                    frameLayout.setVisibility(View.GONE);
                    adapter.changeSelectPosition(position);
                }
                previewTextView.setText("预览(" + adapter.getSelectedNum() + ")");
                confirmTextView.setText("确定(" + adapter.getSelectedNum() + "/" + maxSelectedImageNum + ")");
            }
        }
    };


    private void gotoSubmitPost() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("paths", adapter.getSelectedImages(""));
//        VLog.d(TAG, "SubmitNewPostActivity.class.getSimpleName() = " + SubmitNewPostActivity.class.getSimpleName());
//        if (((ProApplication) getApplication()).getGoBackActivityName().equals(SubmitNewPostActivity.class.getSimpleName())) {
//            intent.setClass(this, SubmitNewPostActivity.class);
//        }else{
//            intent.setClass(this, CreateCourseOfDiseaseActivity.class);
//        }
        startActivity(intent);
        finish();
    }

    private void getImages() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = this.getContentResolver();

        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

        while (mCursor.moveToNext()) {
            // 获取图片的路径
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            HashMap hash = new HashMap();
            hash.put("path", path);
            list.add(hash);
        }
        Collections.reverse(list);

        mCursor.close();
    }

    private class ImageSelectAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> imageList;

        private HashMap<Integer, String> selectedImages;

        public ImageSelectAdapter(Context context, ArrayList<HashMap<String, String>> imageList) {
            this.context = context;
            this.imageList = imageList;
            selectedImages = new HashMap<>();
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_gv_select_image, null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.club_image);
                holder.frameLayout = (FrameLayout) convertView.findViewById(R.id.framelayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (selectedImages.containsKey(position)) {
                holder.frameLayout.setVisibility(View.VISIBLE);
            } else {
                holder.frameLayout.setVisibility(View.GONE);
            }

            ImageLoader.getInstance().displayImage("file://" + list.get(position).get("path"), holder.imageView, Options.clubImageOptions(R.drawable.shape_corner_10dp_solid_white));

            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            FrameLayout frameLayout;
        }


        private void changeSelectPosition(int position) {
            if (selectedImages.containsKey(position)) {
                selectedImages.remove(position);
            } else {
                selectedImages.put(position, list.get(position).get("path"));
            }
        }

        private int getSelectedNum() {
            return selectedImages.size();
        }

        /**
         * @param head 图片路径之前是否需要加前缀，比如加载本地图片的时候需要加上"file://"
         * @return
         */
        private ArrayList<String> getSelectedImages(String head) {
            List<Map.Entry<Integer, String>> infoIds = new ArrayList<>(selectedImages.entrySet());
            Collections.sort(infoIds, new Comparator<Map.Entry<Integer, String>>() {
                public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                    if (o1.getKey() == o2.getKey()) {
                        return 0;
                    } else if (o1.getKey() < o2.getKey()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
            ArrayList<String> images = new ArrayList<>();
            for (Iterator iter = infoIds.iterator(); iter.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iter.next();
                images.add(head + entry.getValue());
            }
            return images;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK != resultCode) {
            return;
        }
        switch (requestCode) {
            case CommonUtil.ALBUM: {
                VLog.d("ImageListActivity", "拍照裁剪");
//                centerFragment.uploadImage();
                Intent intent = new Intent();
                intent.putExtra("filePath", filePath);
                setResult(CommonUtil.CAMERA_CUT, intent);
                finish();
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(CommonUtil.getImageSavePath("album_thumbnail.jpg"))));
        filePath = CommonUtil.getImageSavePath("album_thumbnail.jpg");
        intent.putExtra("return-data", false);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }
}
