package com.yewanlong.ui.view;

import android.widget.ListView;

/**
 * Created by Lkn on 2016/7/18.
 */
public interface NewsView {
    void backActivity();
    ListView getListView();
    void getList();
    void getNews();
   void getAccessToken();
    void endRefreshing();
    void endLoadingMore();
}
