package com.yewanlong.ui.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yewanlong.common.Constants;
import com.yewanlong.ui.service.running.StepCounterService;

/**
 * Created by Lkn on 2016/7/28.
 */
public class MainReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION)){
            Intent service = new Intent(context, StepCounterService.class);
            context.startService(service);
        }else if (intent.getAction().equals(Constants.SERVICE_TEMP)) {
            Intent service = new Intent(context, StepCounterService.class);
            context.startService(service);
        }else{
            Intent service = new Intent(context, StepCounterService.class);
            context.startService(service);
        }
    }

}
