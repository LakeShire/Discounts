package com.github.lakeshire.discounts.fragment.info;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.IDataDisplayView;
import com.github.lakeshire.discounts.fragment.base.DBasePullFragment;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.view.pulltofresh.EnhancePtrFrameLayout;

public class InfoFragment extends DBasePullFragment implements IDataDisplayView {

    public static final String EXTRA_SOURCE = "extra_source";

    ListView mLvInfo;
    private String mSource;
    private InfoPresenter mInfoPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInfoPresenter = new InfoPresenter(this, getActivity());
        Bundle args = getArguments();
        if (args != null) {
            mSource = args.getString(EXTRA_SOURCE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initUI() {
        showAction(R.drawable.ic_search);
        showBack(true);
        setTitle(mSource);

        mLvInfo = (ListView) find(R.id.list);
        mLvInfo.setAdapter(mInfoPresenter.getAdapter());
        mLvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info model = (Info) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
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
    public void loadData() {
        super.loadData();
        showLoadingLayout();
        mInfoPresenter.loadData(mSource);
    }

    @Override
    public void onDataLoaded() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideAllLayout();
                    mInfoPresenter.getAdapter().notifyDataSetChanged();
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
        mInfoPresenter.loadData();
    }

    @Override
    protected boolean checkCanRefresh(EnhancePtrFrameLayout frame, View content, View header) {
        ListView absListView = mLvInfo;
        boolean canRefresh =  !(absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
        return canRefresh;
    }
}
