package com.github.lakeshire.discounts.fragment.info;

import android.content.Context;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.InfoAdapter;
import com.github.lakeshire.discounts.biz.info.InfoLoadListener;
import com.github.lakeshire.discounts.biz.info.IInfoBiz;
import com.github.lakeshire.discounts.biz.info.InfoBiz;
import com.github.lakeshire.discounts.fragment.IDataDisplayView;
import com.github.lakeshire.discounts.model.Info;

import java.util.ArrayList;
import java.util.List;

public class InfoPresenter {

    private Context mContext;
    private IInfoBiz mInfoBiz;
    private IDataDisplayView mDiscoverView;
    private ArrayList<Info> mInfoList = new ArrayList<>();
    private InfoAdapter mAdapter;

    public InfoPresenter(IDataDisplayView discoverView, Context context) {
        this.mDiscoverView = discoverView;
        this.mInfoBiz = new InfoBiz();
        this.mContext = context;
        mAdapter = new InfoAdapter(mContext, mInfoList, R.layout.item_source);
    }

    public void getInfos(String source, int page) {
        mInfoBiz.getInfos(source, page, new InfoLoadListener() {
            @Override
            public void onInfoLoadSuccess(List<Info> infos) {
                mInfoList.addAll(infos);
                mDiscoverView.onDataLoaded();
            }

            @Override
            public void onInfoLoadFail(int err) {
                if (err == InfoLoadListener.ERR_FAIL) {
                    mDiscoverView.onDataLoadedFail();
                } else if (err == InfoLoadListener.ERR_NO_CONTENT) {
                    mDiscoverView.onNoDataLoaded();
                }
            }

            @Override
            public void onSingleInfoLoadSuccess(Info info) {

            }
        });
    }

    public InfoAdapter getAdapter() {
        return mAdapter;
    }

    public void loadData(String source) {
        mInfoList.clear();
        getInfos(source, 0);
    }

    public void loadData() {
        loadData(null);
    }
}
