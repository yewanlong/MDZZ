package com.yewanlong.ui.adapter.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yewanlong.R;
import com.yewanlong.common.UIHelper;
import com.yewanlong.model.bean.taomodel.ContentList;
import com.yewanlong.ui.adapter.viewholder.BelleHolder;
import com.yewanlong.utils.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lkn on 2016/8/11.
 */
public class BelleAdapter extends RecyclerView.Adapter<BelleHolder> {
    private Context mContext;
    private List<ContentList> list = new ArrayList<>();
    private OnRecyclerItemClickListener listener;

    public BelleAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BelleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tao_female, parent, false);
        return new BelleHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(BelleHolder holder, int position) {
        holder.nameTextView.setText(list.get(position).getRealName());
        holder.locationTextView.setText(list.get(position).getCity());
        holder.fansNumTextView.setText("体重: " + list.get(position).getWeight());
        holder.heightTextView.setText("身高: " + list.get(position).getHeight());
        holder.fansNumTextView.setText(list.get(position).getTotalFanNum());
        holder.typeTextView.setText(list.get(position).getType());
        holder.imageNumTextView.setText(list.get(position).getImgList().size() + "");
        ImageLoader.getInstance().displayImage(list.get(position).getAvatarUrl(), holder.avatarImageView, Options
                .clubImageOptions(R.mipmap.profile));
        ImageLoader.getInstance().displayImage(list.get(position).getAvatarUrl(), holder.imageView, Options
                .clubImageOptions(R.mipmap.ic_friends_sends_pictures_no));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.startBelleListActivity(mContext,list.get(position).getImgList());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(View view, int position);
    }

    public void addList(List<ContentList> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<ContentList> getList() {
        return list;
    }

    public void clearList() {
        list.clear();
    }
}
