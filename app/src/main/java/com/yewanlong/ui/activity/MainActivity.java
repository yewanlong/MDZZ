package com.yewanlong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.eventbus.EventCenter;
import com.yewanlong.ui.activity.base.BaseActivity;
import com.yewanlong.ui.adapter.IndexContentAdapter;
import com.yewanlong.ui.dialog.StepDialog;
import com.yewanlong.ui.fragment.SaoMeiFragment;
import com.yewanlong.utils.AlertDialogUtil;
import com.yewanlong.utils.CommonUtil;
import com.yewanlong.utils.SPUtils;
import com.yewanlong.utils.ShareUtil;
import com.yewanlong.widget.RoundImageView;
import com.yewanlong.widget.XViewPager;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;

/**
 * Created by Lkn on 2016/7/12.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private TextView titleTextView;
    private LinearLayout leftLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView leftImageView;
    private RoundImageView headImageView;
    private XViewPager xViewPager;
    private StepDialog dialog;
    private View headerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_mian;
    }

    @Override
    protected void initView() {
        leftLayout = (LinearLayout) findViewById(R.id.layout_left);
        titleTextView = (TextView) findViewById(R.id.tv_title);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        leftImageView = (ImageView) findViewById(R.id.iv_left);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        headImageView = (RoundImageView) headerView.findViewById(R.id.header_image);
        xViewPager = (XViewPager) findViewById(R.id.content);
    }

    @Override
    protected void initData() {
        titleTextView.setText("首页");
        navigationView.setCheckedItem(R.id.nav_login_type);
        leftImageView.setImageResource(R.mipmap.icon_menu);
        IndexContentAdapter indexContentAdapter = new IndexContentAdapter(getSupportFragmentManager());
        xViewPager.setAdapter(indexContentAdapter);
        if (SPUtils.contains(Constants.HEADER_IAMGE)) {
            headImageView.setImageBitmap(CommonUtil.getDiskBitmap(SPUtils.get(Constants.HEADER_IAMGE, "").toString()));
        }
    }

    @Override
    protected void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
        headImageView.setOnClickListener(this);
        leftLayout.setOnClickListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.header_image:
                initPopWindow();
                break;
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
            case R.id.tv_camera:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    CommonUtil.fileName = CommonUtil.getImageSavePath(String.valueOf(System.currentTimeMillis()) + ".jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(CommonUtil.fileName)));
                    startActivityForResult(intent, CommonUtil.CAMERA);
                } else {
                    AlertDialogUtil.getInstance().SingleAlertDialog(this, "没有存储卡，无法拍照，请求相册选择图片！");
                }
                popupWindow.dismiss();
                break;
            case R.id.tv_album:
                ImageSelectActivity.startImageSelectAct(this, true, CommonUtil.ALBUM);
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (UIHelper.onBackPress(leftLayout)) {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_login_type:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_mail_type:
                UIHelper.startMemorandumActivity(this);
                break;
            case R.id.nav_note_type:
//                showDialog();
                startActivity(new Intent(this, BelleFragmentActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_setting:
                UIHelper.startSettingActivity(this);
                break;
            case R.id.nav_share:
                ShareUtil.shareLink(getString(R.string.project_link), "分享", MainActivity.this);
                break;
            default:
                break;
        }
        return true;
    }

    @Subscribe
    public void onMessageEvent(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == Constants.EVEN_BUS.CHANGE_THEME) {
            reload(false);
        }
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new StepDialog(this, R.style.myDialogTheme);
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.show();
    }

    private PopupWindow popupWindow;

    private void initPopWindow() {
        if (popupWindow == null) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popView = inflater.inflate(R.layout.pw_header, null);
            TextView cancelTextView = (TextView) popView.findViewById(R.id.tv_cancel);
            TextView cameraTextView = (TextView) popView.findViewById(R.id.tv_camera);
            TextView albumTextView = (TextView) popView.findViewById(R.id.tv_album);
            popupWindow = new PopupWindow(popView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout
                    .LayoutParams.WRAP_CONTENT, true);
            cancelTextView.setOnClickListener(this);
            albumTextView.setOnClickListener(this);
            cameraTextView.setOnClickListener(this);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAsDropDown(headImageView);
        } else {
            if (!popupWindow.isShowing()) {
                popupWindow.showAsDropDown(headImageView);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CommonUtil.CAMERA:
                if (CommonUtil.fileName != null)
                    CommonUtil.cropImageUri(MainActivity.this, Uri.parse("file://" + CommonUtil.fileName), 400, 400, CommonUtil.CAMERA_CUT);
                break;
            case CommonUtil.ALBUM:
                if (data != null) {
                    SPUtils.put(Constants.HEADER_IAMGE, data.getStringExtra("filePath"));
                    headImageView.setImageBitmap(CommonUtil.getDiskBitmap(data.getStringExtra("filePath")));
                }
                break;
            case CommonUtil.CAMERA_CUT: {
                if (CommonUtil.fileIsExists(CommonUtil.fileName)) {
                    SPUtils.put(Constants.HEADER_IAMGE, CommonUtil.fileName);
                    headImageView.setImageBitmap(CommonUtil.getDiskBitmap(CommonUtil.fileName));
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
