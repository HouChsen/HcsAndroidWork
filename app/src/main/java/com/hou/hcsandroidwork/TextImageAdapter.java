package com.hou.hcsandroidwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 86186 on 2022/4/25.
 */

public class TextImageAdapter extends BaseAdapter {
    private Context mContext;

    private List<String> texts;
    private List<Integer> images;
    private List<String> phones;



    //获取图片数组和文字数组
    public TextImageAdapter(Context mContext, List<String> texts, List<Integer> images,List<String> phones) {
        this.mContext = mContext;
        this.texts = texts;
        this.images = images;
        this.phones = phones;

    }

    @Override
    public int getCount() {
        return texts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_bean_list, null);
            ItemViewCache viewCache = new ItemViewCache();
            viewCache.mTextView = (TextView) convertView
                    .findViewById(R.id.item_search_tv_title);
            viewCache.mImageView = (ImageView) convertView
                    .findViewById(R.id.item_search_iv_icon);
            viewCache.mPhone = (TextView) convertView
                    .findViewById(R.id.item_search_tv_phone);
            convertView.setTag(viewCache);
        }
        ItemViewCache cache = (ItemViewCache) convertView.getTag();
        cache.mTextView.setText(texts.get(position));
        cache.mImageView.setImageResource(images.get(position));
        cache.mPhone.setText(phones.get(position));
        return convertView;
    }

    private class ItemViewCache {
        public TextView mTextView;
        public ImageView mImageView;
        public TextView mPhone;

    }
}
