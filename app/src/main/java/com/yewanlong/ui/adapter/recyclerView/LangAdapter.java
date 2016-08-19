package com.yewanlong.ui.adapter.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yewanlong.R;
import com.yewanlong.model.bean.taomodel.LangFemaleList;
import com.yewanlong.model.bean.taomodel.SaoFemaleList;
import com.yewanlong.ui.adapter.viewholder.LangHolder;
import com.yewanlong.utils.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lkn on 2016/8/11.
 */
public class LangAdapter extends RecyclerView.Adapter<LangHolder> {
    private Context mContext;
    private List<LangFemaleList> list = new ArrayList<>();
    private OnRecyclerItemClickListener listener;

    public LangAdapter(Context context) {
        mContext = context;
    }

    @Override
    public LangHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sao_female, parent, false);
        return new LangHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(LangHolder holder, int position) {
        holder.titleTextView.setText(list.get(position).getTitle());
        ImageLoader.getInstance().displayImage(list.get(position).getImageurl(), holder.imageView, Options
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

    public void addList(List<LangFemaleList> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<LangFemaleList> getList() {
        return list;
    }

    public void clearList() {
        list.clear();
    }
}
