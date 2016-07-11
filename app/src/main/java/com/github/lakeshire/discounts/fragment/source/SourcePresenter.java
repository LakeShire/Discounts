package com.github.lakeshire.discounts.fragment.source;

import android.content.Context;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.SourceAdapter;
import com.github.lakeshire.discounts.biz.info.InfoLoadListener;
import com.github.lakeshire.discounts.biz.source.SourceLoadListener;
import com.github.lakeshire.discounts.biz.source.ISourceBiz;
import com.github.lakeshire.discounts.biz.source.SourceBiz;
import com.github.lakeshire.discounts.fragment.IDataDisplayView;
import com.github.lakeshire.discounts.model.Source;

import java.util.ArrayList;
import java.util.List;

public class SourcePresenter {

    private ArrayList<Source> mSourceList = new ArrayList<>();
    private SourceAdapter mAdapter;
    private Context mContext;
    private ISourceBiz mSourceBiz;
    private IDataDisplayView mSourceView;

    public SourcePresenter(IDataDisplayView sourceView, Context context) {
        this.mSourceView = sourceView;
        this.mSourceBiz = new SourceBiz();
        this.mContext = context;
        mAdapter = new SourceAdapter(mContext, mSourceList, R.layout.item_source_grid);
    }

    public void getSources() {
        mSourceBiz.getSources(new SourceLoadListener() {
            @Override
            public void onSourceLoadSuccess(List<Source> sources) {
                mSourceList.addAll(sources);
                mSourceView.onDataLoaded();
            }

            @Override
            public void onSourceLoadFail(int err) {
                if (err == InfoLoadListener.ERR_FAIL) {
                    mSourceView.onDataLoadedFail();
                } else if (err == InfoLoadListener.ERR_NO_CONTENT) {
                    mSourceView.onNoDataLoaded();
                }
            }
        });
    }

    public SourceAdapter getAdapter() {
        return mAdapter;
    }

    public void loadData() {
        mSourceList.clear();
        getSources();
    }
}
