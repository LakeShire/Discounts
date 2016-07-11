package com.github.lakeshire.discounts.fragment.source;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.IDataDisplayView;
import com.github.lakeshire.discounts.fragment.info.InfoFragment;
import com.github.lakeshire.discounts.fragment.base.PagerFragment;
import com.github.lakeshire.discounts.model.Source;

public class SourceFragment extends PagerFragment implements IDataDisplayView {

    GridView mGvSource;
    private SourcePresenter mSourcePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSourcePresenter = new SourcePresenter(this, getActivity());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_source;
    }

    @Override
    public void initUi() {
        super.initUi();

        mGvSource = (GridView) find(R.id.grid);
        mGvSource.setAdapter(mSourcePresenter.getAdapter());
        mGvSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Source model = (Source) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(InfoFragment.EXTRA_SOURCE, model.getName());
                startFragment(InfoFragment.class, bundle);
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
        mSourcePresenter.loadData();
    }

    @Override
    public void onDataLoaded() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideAllLayout();
                    mSourcePresenter.getAdapter().notifyDataSetChanged();
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
}
