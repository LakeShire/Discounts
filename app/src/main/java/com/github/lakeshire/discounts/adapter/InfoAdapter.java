package com.github.lakeshire.discounts.adapter;

import android.content.Context;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.lemon.adapter.base.BaseAdapter;
import com.github.lakeshire.lemon.adapter.base.ViewHolder;

import java.util.List;

public class InfoAdapter extends BaseAdapter<Info> {

    public InfoAdapter(Context mContext, List<Info> mListData, int mLayoutResId) {
        super(mContext, mListData, mLayoutResId);
    }

    @Override
    public void bindViewData(ViewHolder viewHolder, Info item, int position) {
        viewHolder.setText(R.id.tv_title, item.getTitle());
    }
}