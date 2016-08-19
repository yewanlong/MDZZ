package com.yewanlong.ui.adapter.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yewanlong.R;
import com.yewanlong.model.bean.taomodel.SaoFemaleList;
import com.yewanlong.ui.adapter.viewholder.SaoHolder;
import com.yewanlong.utils.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lkn on 2016/8/11.
 */
public class SaoAdapter extends RecyclerView.Adapter<SaoHolder> {
    private Context mContext;
    private List<SaoFemaleList> list = new ArrayList<>();
    private OnRecyclerItemClickListener listener;

    public SaoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public SaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sao_female, parent, false);
        return new SaoHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(SaoHolder holder, int position) {
        holder.titleTextView.setText(list.get(position).getTitle());
        ImageLoader.getInstance().displayImage(list.get(position).getThumb(), holder.imageView, Options
                .clubImageOptions(R.mipmap.ic_friends_sends_pictures_no));
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

    public void addList(List<SaoFemaleList> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<SaoFemaleList> getList() {
        return list;
    }

    public void clearList() {
        list.clear();
    }
}
