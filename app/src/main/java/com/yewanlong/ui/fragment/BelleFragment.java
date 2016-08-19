package com.yewanlong.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.bean.taomodel.TaoFemale;
import com.yewanlong.ui.activity.base.BaseSwipeBackActivity;
import com.yewanlong.ui.adapter.recyclerView.BelleAdapter;
import com.yewanlong.ui.fragment.base.BaseFragment;
import com.yewanlong.utils.HttpUtils;
import com.yewanlong.utils.volley.RequestListener;
import com.yewanlong.utils.volley.StringRequest;
import com.yewanlong.widget.loadmore.EndlessRecyclerOnScrollListener;
import com.yewanlong.widget.loadmore.HeaderViewRecyclerAdapter;

/**
 * Created by Lkn on 2016/8/11.
 */
public class BelleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BelleAdapter.OnRecyclerItemClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int page = 1, pageNum = 1;
    private BelleAdapter adapter;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private View footView;
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
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        adapter = new BelleAdapter(getActivity());
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
        createFootView();
        recyclerView.setAdapter(adapter);
        showRefreshProgress();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (page < pageNum) {
                    page++;
                    footView.setVisibility(View.VISIBLE);
                    getTaoFemaleData();
                } else {
                    UIHelper.showSnackBar("暂时没有更多数据~~", recyclerView);
                }

            }
        });
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
        getTaoFemaleData();
    }

    public void showRefreshProgress() {

        swipeRefreshLayout.postDelayed(() -> {

            swipeRefreshLayout.setRefreshing(true);
            getTaoFemaleData();
        }, 500);
    }

    public void getTaoFemaleData() {
        StringRequest request = HttpUtils.getTaoFemale(listener, page, Constants.APP_ID, Constants.APP_SIGN);
        app.addRequestQueue(1001, request, this);
    }


    public RequestListener<String> listener = new RequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            switch (what) {
                case 1001:
                    TaoFemale taoFemale = JSON.parseObject(response, TaoFemale.class);
                    if (taoFemale.getShowapi_res_code() == 0 && taoFemale.getShowapi_res_body().getRet_code() == 0) {
                        swipeRefreshLayout.setRefreshing(false);
                        pageNum = taoFemale.getShowapi_res_body().getPagebean().getMaxResult();
                        adapter.addList(taoFemale.getShowapi_res_body().getPagebean().getContentlist());
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
        UIHelper.startBelleDetailActivity(getActivity(),adapter.getList().get(position).getAvatarUrl(),adapter.getList().get(position).getRealName());
    }

    private void createFootView() {
        footView = LayoutInflater.from(getActivity()).inflate(R.layout.load_more_foot_layout, recyclerView, false);
        mHeaderViewRecyclerAdapter.addFooterView(footView);
        footView.setVisibility(View.GONE);
    }
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
