package com.yewanlong.ui.adapter.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yewanlong.R;
import com.yewanlong.model.bean.NewsDetail;
import com.yewanlong.utils.Options;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Lkn on 2016/6/14.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<NewsDetail> list;
    private OnItemClickListener listener;

    public MyAdapter(Context context, List<NewsDetail> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (type == 0) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_news_type_one, null);
                holder.titleOneTextView = (TextView) convertView.findViewById(R.id.title);
                holder. ripple = (MaterialRippleLayout) convertView.findViewById(R.id.ripple);
                holder.fromAndTimeOneTextView = (TextView) convertView.findViewById(R.id.from_and_time);
            } else if (type == 1) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_news_type_three, null);
                holder. ripple = (MaterialRippleLayout) convertView.findViewById(R.id.ripple);
                holder.titleThreeTextView = (TextView) convertView.findViewById(R.id.title);
                holder.fromAndTimeThreeTextView = (TextView) convertView.findViewById(R.id.from_and_time);
                holder.singleImageView = (ImageView) convertView.findViewById(R.id.image_one);
            } else if (type == 2) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_news_type_two, null);
                holder.titleTwoTextView = (TextView) convertView.findViewById(R.id.title);
                holder.fromAndTimeTwoTextView = (TextView) convertView.findViewById(R.id.from_and_time);
                holder.oneImageView = (ImageView) convertView.findViewById(R.id.image_one);
                holder.twoImageView = (ImageView) convertView.findViewById(R.id.image_two);
                holder.threeImageView = (ImageView) convertView.findViewById(R.id.image_three);
                holder. ripple = (MaterialRippleLayout) convertView.findViewById(R.id.ripple);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type == 0) {
            holder.titleOneTextView.setText(list.get(position).getTitle());
            holder.fromAndTimeOneTextView.setText(list.get(position).getSite() + "  " + getTime(list.get(position).getPublish_time()));
        } else if (type == 1) {
            holder.titleThreeTextView.setText(list.get(position).getTitle());
            holder.fromAndTimeThreeTextView.setText(list.get(position).getSite() + "  " + getTime(list.get(position).getPublish_time()));
            ImageLoader.getInstance().displayImage(list.get(position).getList_images().get(0).getUrls().get(0), holder.singleImageView, Options.options(R.mipmap.ic_friends_sends_pictures_no));
        } else if (type == 2) {
            holder.titleTwoTextView.setText(list.get(position).getTitle());
            holder.fromAndTimeTwoTextView.setText(list.get(position).getSite() + "  " + getTime(list.get(position).getPublish_time()));
            ImageLoader.getInstance().displayImage(list.get(position).getList_images().get(0).getUrls().get(0), holder.oneImageView, Options.options(R.mipmap.ic_friends_sends_pictures_no));
            ImageLoader.getInstance().displayImage(list.get(position).getList_images().get(1).getUrls().get(0), holder.twoImageView, Options.options(R.mipmap.ic_friends_sends_pictures_no));
            ImageLoader.getInstance().displayImage(list.get(position).getList_images().get(2).getUrls().get(0), holder.threeImageView, Options.options(R.mipmap.ic_friends_sends_pictures_no));
        }
        holder.ripple.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecyclerItemClick(v, position);
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView titleOneTextView, titleTwoTextView, titleThreeTextView, fromAndTimeOneTextView, fromAndTimeTwoTextView, fromAndTimeThreeTextView;
        ImageView singleImageView, oneImageView, twoImageView, threeImageView;
        MaterialRippleLayout ripple;
    }
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getList_images().size() == 0) {
            return 0;
        } else if (list.get(position).getList_images().size() < 3) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    private String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(time*1000);
    }
    public void setItemClick(OnItemClickListener onItemClickListener){
        listener = onItemClickListener;
    }
    public  interface OnItemClickListener {
        void onRecyclerItemClick(View view, int position);
    }
}
