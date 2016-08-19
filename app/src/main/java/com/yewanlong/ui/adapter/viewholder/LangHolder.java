package com.yewanlong.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.ui.adapter.recyclerView.LangAdapter;
import com.yewanlong.ui.adapter.recyclerView.SaoAdapter;

/**
 * Created by Lkn on 2016/8/11.
 */
public class LangHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView titleTextView;
    public ImageView imageView;
    private LangAdapter.OnRecyclerItemClickListener mListener;

    public LangHolder(View itemView,LangAdapter.OnRecyclerItemClickListener mListener) {
        super(itemView);
        this.mListener = mListener;
        titleTextView = (TextView) itemView.findViewById(R.id.tv_title);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        itemView.setOnClickListener(this);
    }



    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        if(mListener != null){
            mListener.onRecyclerItemClick(v,getAdapterPosition());
        }
    }

}
