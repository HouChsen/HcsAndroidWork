package com.hou.hcsandroidwork.finderr;

import android.content.Context;

import com.hou.hcsandroidwork.R;

import java.util.List;


//
//
//继承自CommonAdapter
//
//
public class SearchAdapter extends CommonAdapter<Bean>{

    public SearchAdapter(Context context, List<Bean> data, int layoutId) {
        super(context, data, layoutId);
    }

    //
    //
    //使用ViewHolder类定义对象
    //
    //

    @Override
    public void convert(ViewHolder holder, int position) {
        holder.setImageResource(R.id.item_search_iv_icon,mData.get(position).getIconId())
                .setText(R.id.item_search_tv_title,mData.get(position).getTitle())
                .setText(R.id.item_search_tv_phone,mData.get(position).getPhone());
    }
}
