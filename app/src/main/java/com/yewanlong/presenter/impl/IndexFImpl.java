package com.yewanlong.presenter.impl;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.yewanlong.common.Constants;
import com.yewanlong.model.bean.God;
import com.yewanlong.model.Realm.RealmHelper;
import com.yewanlong.model.eventbus.EventCenter;
import com.yewanlong.presenter.FragmentPresenter;
import com.yewanlong.ui.activity.EditActivity;
import com.yewanlong.ui.adapter.IndexViewAdapter;
import com.yewanlong.ui.view.LoginTypeFView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/15.
 */
public class IndexFImpl implements FragmentPresenter, IndexViewAdapter.OnRecyclerItemClickListener {

    private final Context mContext;
    private final LoginTypeFView mLoginTypeFView;
    private IndexViewAdapter mAdapter;
    private ArrayList<God> selector = new ArrayList<>();
    private boolean isCreate;

    public IndexFImpl(Context context, LoginTypeFView view) {
        mContext = context;
        mLoginTypeFView = view;
    }
    @Override
    public void onFirstUserVisible() {
        isCreate = true;
        selector = selector();
        mAdapter = new IndexViewAdapter(mContext, selector);
        mAdapter.setOnRecyclerItemClick(this);
        mLoginTypeFView.initRecycler(new LinearLayoutManager(mContext), mAdapter);
        if (null != selector && selector.size() > 0) {
            mLoginTypeFView.hideException();
        } else {
            mLoginTypeFView.showException();
        }
    }

    private ArrayList<God> selector() {
        return RealmHelper.getInstances(mContext).selector(mContext);
    }

    @Override
    public void onUserVisible() {
    }

    @Override
    public void onUserInvisible() {
    }

    public void onEventComing(EventCenter eventCenter) {
        if (isCreate) {
            if (eventCenter.getEventCode() == 1) {
                boolean data = (boolean) eventCenter.getData();
                if (data) {
                    selector = selector();
                    if (null != selector && selector.size() > 0) {
                        mLoginTypeFView.hideException();
                        mAdapter.addAll(selector);
                    } else {
                        mLoginTypeFView.showException();
                        mAdapter.clearData();
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } else if (eventCenter.getEventCode() == Constants.EVEN_BUS.CHANGE_PASS_WORD_SHOW) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onRecyclerItemClick(View view, int position) {
        mLoginTypeFView.readGo(EditActivity.class, Constants.VIEW_MODE, position);
    }

}
