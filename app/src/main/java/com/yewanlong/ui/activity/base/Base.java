package com.yewanlong.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yewanlong.R;

/**
 * Created by Ywl on 2016/7/12.
 */
public abstract class Base extends AppCompatActivity {

    public enum TransitionMode {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        SCALE,
        FADE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (null != intent) {
            startAnim();
        }
        super.onCreate(savedInstanceState);
    }

    private void startAnim() {
//        if (toggleOverridePendingTransition()) {
        if (getOverridePendingTransitionMode() == null) {
            return;
        }
        switch (getOverridePendingTransitionMode()) {
            case LEFT:
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case RIGHT:
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case TOP:
                overridePendingTransition(R.anim.top_in, R.anim.top_out);
                break;
            case BOTTOM:
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                break;
            case SCALE:
                overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                break;
            case FADE:
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
//            }
        }
    }

    //    protected abstract boolean toggleOverridePendingTransition();
    protected abstract TransitionMode getOverridePendingTransitionMode();

}
