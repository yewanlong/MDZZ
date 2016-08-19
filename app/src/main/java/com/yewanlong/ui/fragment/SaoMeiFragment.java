package com.yewanlong.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.bean.taomodel.SaoFemale;
import com.yewanlong.model.bean.taomodel.SaoFemaleList;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.ui.adapter.recyclerView.SaoAdapter;
import com.yewanlong.ui.fragment.base.BaseFragment;
import com.yewanlong.utils.HttpUtils;
import com.yewanlong.utils.volley.RequestListener;
import com.yewanlong.utils.volley.StringRequest;
import com.yewanlong.widget.loadmore.HeaderViewRecyclerAdapter;

import java.util.Map;

/**
 * Created by Lkn on 2016/8/11.
 */
public class SaoMeiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        SaoAdapter.OnRecyclerItemClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int page = 1;
    private SaoAdapter adapter;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private View footView;
    private boolean mIsRefreshing = false;

    @Override
    protected void getIntent() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_belle;
    }

    @Override
    protected void initView(View v) {
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        swipeRefreshLayout.setColorSchemeColors(getColorPrimary());
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager mLinearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        adapter = new SaoAdapter(getActivity());
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
        createFootView();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom = mLinearLayoutManager.findLastCompletelyVisibleItemPositions(
                        new int[2])[1] >= adapter.getItemCount() - 6; //预加载
                if (isBottom) {
                    if (mIsRefreshing) {
                        page++;
                        footView.setVisibility(View.VISIBLE);
                        getSaoFemaleData();
                    } else {
                        UIHelper.showSnackBar("暂时没有更多数据~~", recyclerView);
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
        showRefreshProgress();
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnRecyclerItemClick(this);

    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }


    @Override
    public void onRefresh() {
        page = 1;
        adapter.clearList();
        getSaoFemaleData();
    }

    public void showRefreshProgress() {

        swipeRefreshLayout.postDelayed(() -> {

            swipeRefreshLayout.setRefreshing(true);
            getSaoFemaleData();
        }, 500);
    }

    public void getSaoFemaleData() {
        StringRequest request = HttpUtils.getSaoFemale(listener, page, Constants.APP_ID, Constants.APP_SIGN);
        app.addRequestQueue(1001, request, this);
    }


    public RequestListener<String> listener = new RequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            switch (what) {
                case 1001:
                    SaoFemale taoFemale = JSON.parseObject(response, SaoFemale.class);
                    if (taoFemale.getShowapi_res_code() == 0) {
                        swipeRefreshLayout.setRefreshing(false);
                        taoFemale.getShowapi_res_body().remove("ret_code");
                        Map<String, SaoFemaleList> map = (Map<String, SaoFemaleList>) JSON.parse(taoFemale.getShowapi_res_body().toString());
                        adapter.addList(JSON.parseArray(map.values().toString(), SaoFemaleList.class));
                        if (map.size() == 20) {
                            mIsRefreshing = true;
                        } else {
                            mIsRefreshing = false;
                        }

                    } else {
                        UIHelper.showToast(app, taoFemale.getShowapi_res_error());
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void onError(int what, int code, String message) {
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    public void onRecyclerItemClick(View view, int position) {
        UIHelper.startBelleDetailActivity(getActivity(),adapter.getList().get(position).getThumb(),adapter.getList().get(position).getTitle());

    }
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
    private void createFootView() {
        footView = LayoutInflater.from(getActivity()).inflate(R.layout.load_more_foot_layout, recyclerView, false);
        mHeaderViewRecyclerAdapter.addFooterView(footView);
        footView.setVisibility(View.GONE);
    }
}
