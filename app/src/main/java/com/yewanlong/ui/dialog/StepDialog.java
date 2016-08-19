package com.yewanlong.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.common.Constants;
import com.yewanlong.utils.SPUtils;
import com.yewanlong.utils.ScreenUtil;


/**
 * Created by Lkn on 2016/7/25.
 */
public class StepDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private StepListener onclickListener;
    private TextView nextTextView, contentTextView;

    public StepDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_step);
        initManager();
        initView();
        initListener();
    }

    private void initView() {
        nextTextView = (TextView) findViewById(R.id.tv_next);
        contentTextView = (TextView) findViewById(R.id.tv_content);
        handler.post(runnable);//每两秒执行一次runnable.
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            contentTextView.setText("" + SPUtils.get(Constants.CURRENT_STEP, 0));
            handler.postDelayed(this, 3000);//每两秒执行一次runnable.
        }
    };

    private void initManager() {
        WindowManager.LayoutParams params = getWindow().getAttributes(); // 获取对话框当前的参数值
        int width = (int) (ScreenUtil.getInstance(context).getWidth() * 0.75);
        params.width = width;
        params.gravity = Gravity.CENTER;
        onWindowAttributesChanged(params);
    }

    private void initListener() {
        nextTextView.setOnClickListener(this);
    }

    public void setDialogClickListener(StepListener dialogOnclickListener) {
        this.onclickListener = dialogOnclickListener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_next:
                dismiss();
                break;
        }
    }

    public interface StepListener {
        void dialogOnclick(float number);
    }
}
