package com.github.lakeshire.discounts.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.model.Source;
import com.github.lakeshire.lemon.adapter.base.BaseAdapter;
import com.github.lakeshire.lemon.adapter.base.ViewHolder;
import com.github.lakeshire.lemon.util.ImageUtil;

import java.util.List;

public class SourceAdapter extends BaseAdapter<Source> {

    private Context mContext;

    public SourceAdapter(Context context, List<Source> data, int res) {
        super(context, data, res);
        this.mContext = context;
    }

    @Override
    public void bindViewData(ViewHolder viewHolder, Source item, int position) {
        viewHolder.setText(R.id.tv_name, item.getName());
        ImageUtil.getInstance(mContext).setImage((ImageView) viewHolder.getItemView(R.id.iv_logo), item.getPic());
    }
}
