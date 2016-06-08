package com.github.lakeshire.discounts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.base.BaseAdapter;
import com.github.lakeshire.discounts.adapter.base.ViewHolder;
import com.github.lakeshire.discounts.model.Source;
import com.github.lakeshire.discounts.util.HttpUtil;
import com.github.lakeshire.discounts.util.ImageUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/2.
 */
public class SourceFragment extends PagerFragment {

    GridView mGvSource;
    private ArrayList<Source> mSourceList = new ArrayList<>();
    private SourceAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_source;
    }

    @Override
    public void initUi() {
        super.initUi();

        mGvSource = (GridView) find(R.id.grid);
        mAdapter = new SourceAdapter(getActivity(), mSourceList, R.layout.item_source_grid);
        mGvSource.setAdapter(mAdapter);
        mGvSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Source model = (Source) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("extra_source", model.getName());
                startFragment(InfoFragment.class, bundle);
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        mSourceList.clear();
        showLoadingLayout();
        HttpUtil.getInstance().get("http://lakeshire.top/source/all", new HttpUtil.Callback() {
            @Override
            public void onFail(String error) {
                showNetworkErrorLayout();
                Logger.d("onFail");
            }

            @Override
            public void onSuccess(String response) {
                Logger.d(response);
                if (response != null && !response.isEmpty()) {
                    List<Source> sources = JSON.parseArray(response, Source.class);
                    if (sources.isEmpty()) {
                        showNoContentLayout();
                    } else {
                        mSourceList.addAll(sources);
                        notifyAdapter();
                    }
                }
            }
        }, 0);
    }

    class SourceAdapter extends BaseAdapter<Source> {

        public SourceAdapter(Context mContext, List<Source> mListData, int mLayoutResId) {
            super(mContext, mListData, mLayoutResId);
        }

        @Override
        public void bindViewData(ViewHolder viewHolder, Source item, int position) {
            viewHolder.setText(R.id.tv_name, item.getName());
            ImageUtil.getInstance(getActivity()).setImage((ImageView) viewHolder.getItemView(R.id.iv_logo), item.getPic());
        }
    }

//    @Override
//    protected boolean checkCanRefresh(EnhancePtrFrameLayout frame, View content, View header) {
//        ListView absListView = mLvSource;
//        boolean canRefresh =  !(absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
//        return canRefresh;
//    }

    private void notifyAdapter() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideAllLayout();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
