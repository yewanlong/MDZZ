package com.yewanlong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @文件名称: NoScrollerListView.java
 * @功能描述: listview 无法滑动
 * @版本信息: Copyright (c)2014
 * @公司信息: 航讯科技
 * @开发人员: vincent
 * @版本日志: 1.0
 * @创建时间: 2014年4月3日 上午10:54:12
 */
public class NoScrollerListView extends ListView {

	public NoScrollerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
