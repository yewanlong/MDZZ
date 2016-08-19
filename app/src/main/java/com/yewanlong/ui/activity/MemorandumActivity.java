package com.yewanlong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.yewanlong.AppContext;
import com.yewanlong.R;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.eventbus.EventCenter;
import com.yewanlong.presenter.impl.EditAImpl;
import com.yewanlong.presenter.impl.IndexFImpl;
import com.yewanlong.ui.activity.base.BaseActivity;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.ui.view.EditAView;
import com.yewanlong.ui.view.LoginTypeFView;
import com.yewanlong.utils.SpellComparator;
import com.yewanlong.utils.volley.RequestListener;
import com.yewanlong.utils.volley.StringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lkn on 2016/7/13.
 */
public class MemorandumActivity extends BaseSwipeBackActivity implements LoginTypeFView,View.OnClickListener{
    private RecyclerView mRecyclerView;
    private FloatingActionButton addButton;
    private TextView titleTextView;
    private LinearLayout leftLayout;
    private static final int INDEX_FRAGMENT_REQUEST_CODE = 2;
    private   LinearLayout mException;
    private IndexFImpl mIndexFImpl;
    private static final int EDIT_SAVE = 1;
    private static final int SUCCESS = 1;
    @Override
    protected int getContentView() {
        return R.layout.fragment_login_type;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addButton = (FloatingActionButton) findViewById(R.id.button_add);
        titleTextView = (TextView) findViewById(R.id.tv_title);
        leftLayout = (LinearLayout) findViewById(R.id.layout_left);
        mException = (LinearLayout) findViewById(R.id.exception);
        mIndexFImpl = new IndexFImpl(this, this);
    }

    @Override
    protected void initData() {
        titleTextView.setText("备忘录");
        mIndexFImpl.onFirstUserVisible();
    }

    @Override
    protected void initListener() {
        addButton.setOnClickListener(this);
        leftLayout.setOnClickListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
         return TransitionMode.RIGHT;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                finish();
                break;
            case R.id.button_add:
                readGo(EditActivity.class,1,0);
                break;
            default:
                break;
        }
    }

    @Override
    public void initRecycler(LinearLayoutManager linearLayoutManager, RecyclerView.Adapter adapter) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void readGo(Class clazz, int type, int position) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("CREATE_MODE", type);
        intent.putExtra("position", position);
        startActivityForResult(intent, INDEX_FRAGMENT_REQUEST_CODE);
    }

    @Override
    public void hideException() {
        mException.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showException() {
        mException.setVisibility(View.VISIBLE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INDEX_FRAGMENT_REQUEST_CODE) {
            if (resultCode == EDIT_SAVE && resultCode == SUCCESS) {
                EventCenter eventCenter = new EventCenter(EDIT_SAVE, true);
                EventBus.getDefault().post(eventCenter);
            }
        }
    }
    @Subscribe
    protected void onEventComing(EventCenter eventCenter) {
        mIndexFImpl.onEventComing(eventCenter);
    }

}
