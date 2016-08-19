package com.yewanlong.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yewanlong.R;
import com.yewanlong.ui.adapter.recyclerView.BelleAdapter;
import com.yewanlong.widget.RoundImageView;

/**
 * Created by Lkn on 2016/8/11.
 */
public class BelleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public RoundImageView avatarImageView;
    public TextView nameTextView, typeTextView, locationTextView, fansNumTextView, heightTextView, widthTextView, imageNumTextView;
    public ImageView imageView;
    private BelleAdapter.OnRecyclerItemClickListener mListener;

    public BelleHolder(View itemView, BelleAdapter.OnRecyclerItemClickListener mListener) {
        super(itemView);
        this.mListener = mListener;
        avatarImageView = (RoundImageView) itemView.findViewById(R.id.tao_avatar);
        nameTextView = (TextView) itemView.findViewById(R.id.tao_name);
        typeTextView = (TextView) itemView.findViewById(R.id.tao_type);
        locationTextView = (TextView) itemView.findViewById(R.id.tao_location);
        fansNumTextView = (TextView) itemView.findViewById(R.id.tao_fans_num);
        heightTextView = (TextView) itemView.findViewById(R.id.tao_height);
        widthTextView = (TextView) itemView.findViewById(R.id.tao_width);
        imageNumTextView = (TextView) itemView.findViewById(R.id.tao_image_num);
        imageView = (ImageView) itemView.findViewById(R.id.tao_image);
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
