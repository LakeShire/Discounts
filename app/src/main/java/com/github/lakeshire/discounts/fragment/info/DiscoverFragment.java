package com.github.lakeshire.discounts.fragment.info;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.IDataDisplayView;
import com.github.lakeshire.discounts.fragment.base.PagerFragment;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.view.pulltofresh.EnhancePtrFrameLayout;

public class DiscoverFragment extends PagerFragment implements IDataDisplayView {



    ListView mLvInfo;
    private InfoPresenter mDiscoverPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDiscoverPresenter = new InfoPresenter(this, getActivity());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    public void initUi() {
        super.initUi();

        mLvInfo = (ListView) find(R.id.list);
        mLvInfo.setAdapter(mDiscoverPresenter.getAdapter());
        mLvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info model = (Info) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(InfoDetailFragment.EXTRA_INFO_ID, model.getId());
                bundle.putString(InfoDetailFragment.EXTRA_INFO_URL, model.getUrl());
                bundle.putString(InfoDetailFragment.EXTRA_INFO_TITLE, model.getTitle());
                bundle.putString(InfoDetailFragment.EXTRA_INFO_DESCRIPTION, model.getDescription());
                bundle.putString(InfoDetailFragment.EXTRA_INFO_TAGS, model.getTags());
                bundle.putString(InfoDetailFragment.EXTRA_INFO_PIC, model.getPic());
                startFragment(InfoDetailFragment.class, bundle);
            }
        });
    }

    @Override
    protected void initUI() {

    }

    @Override
    public void loadData() {
        super.loadData();
        showLoadingLayout();
        mDiscoverPresenter.loadData();
    }

    @Override
    protected boolean checkCanRefresh(EnhancePtrFrameLayout frame, View content, View header) {
        ListView absListView = mLvInfo;
        boolean canRefresh =  !(absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
        return canRefresh;
    }

    @Override
    public void onDataLoaded() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideAllLayout();
                    mDiscoverPresenter.getAdapter().notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onDataLoadedFail() {
        showNetworkErrorLayout();
    }

    @Override
    public void onNoDataLoaded() {
        showNoContentLayout();
    }

    @Override
    protected void onRefresh(EnhancePtrFrameLayout frame) {
        super.onRefresh(frame);
        mDiscoverPresenter.loadData();
    }
}
