package com.yewanlong.presenter;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Clearlove on 16/1/13.
 */
public interface ActivityPresenter {

    void onCreate(Bundle savedInstanceState);

    void getIntent(Intent intent);
}
