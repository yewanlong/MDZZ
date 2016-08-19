package com.yewanlong.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yewanlong.R;
import com.yewanlong.common.UIHelper;
import com.yewanlong.ui.fragment.base.BaseFragment;
import com.yewanlong.utils.AlertDialogUtil;
import com.yewanlong.utils.CommonUtil;
import com.yewanlong.utils.Options;
import com.yewanlong.utils.interfaces.AlertDialogInterface;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Lkn on 2016/8/12.
 */
public class BelleImageFragment extends BaseFragment  {
    private ImageView imageView;
    private TextView tv_image_error;
    private String url;
    public static Bitmap headBitmap;

    public static BelleImageFragment newInstance(String url) {
        BelleImageFragment belleImageFragment = new BelleImageFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("url", url);
        belleImageFragment.setArguments(mBundle);
        return belleImageFragment;
    }

    @Override
    protected void getIntent() {
        url = getArguments().getString("url");
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_belle_image;
    }

    @Override
    protected void initView(View v) {
        imageView = (ImageView) v.findViewById(R.id.imageView);
        tv_image_error = (TextView) v.findViewById(R.id.tv_image_error);
    }

    @Override
    protected void initData() {
        ImageLoader.getInstance().displayImage(url, imageView, Options
                .clubImageOptions(R.color.black), new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                tv_image_error.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                headBitmap = bitmap;
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }

    @Override
    protected void initListener() {
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialogUtil.getInstance().DoubleAlertDialog(getActivity(), "是否保存图片？", new AlertDialogInterface() {
                    @Override
                    public void buttonSelectedListener(Object args) {
                            if(headBitmap!=null){
                                String meiNv = null;
                                try {
                                    meiNv = CommonUtil.saveFile(getActivity(), headBitmap, "meinv_" + new Random().nextInt(100000) + ".jpg");
                                } catch (IOException e) {
                                    UIHelper.showToast(getActivity(),"图片保存失败");
                                    e.printStackTrace();
                                }
                                UIHelper.showToast(getActivity(), meiNv);
                            }else{
                                UIHelper.showToast(getActivity(),"图片保存失败");
                            }
                    }
                });

                return true;
            }
        });
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }


}
