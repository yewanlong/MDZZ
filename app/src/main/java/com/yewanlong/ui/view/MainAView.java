package com.yewanlong.ui.view;

import android.widget.ListView;
import android.widget.TextView;

import com.yewanlong.widget.circle.ImageCycleView;

/**
 * Created by Lkn on 2016/7/18.
 */
public interface MainAView  {
    ImageCycleView mCycleView();
    void getAccessToken();
    ListView getListView();
    void getList();
    void getNews();
    void startNewIntent();
    void endRefreshing();
}
