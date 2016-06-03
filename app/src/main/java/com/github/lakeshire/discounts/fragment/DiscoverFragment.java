package com.github.lakeshire.discounts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.base.BaseAdapter;
import com.github.lakeshire.discounts.adapter.base.ViewHolder;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.util.HttpUtil;
import com.github.lakeshire.discounts.view.pulltofresh.EnhancePtrFrameLayout;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/2.
 */
public class DiscoverFragment extends PagerFragment {

    ListView mLvInfo;
    private ArrayList<Info> mInfoList = new ArrayList<>();
    private InfoAdapter mAdapter;
    private String mSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSource = args.getString("extra_source");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    public void initUi() {
//        showAction(R.drawable.ic_search);
//        showBack(true);
//        setTitle(mSource);

        mLvInfo = (ListView) find(R.id.list);
        mAdapter = new InfoAdapter(getActivity(), mInfoList, R.layout.item_source);
        mLvInfo.setAdapter(mAdapter);
        mLvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info model = (Info) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("extra_info_url", model.getUrl());
                bundle.putString("extra_info_title", model.getTitle());
                bundle.putString("extra_info_description", model.getDescription());
                bundle.putString("extra_info_tags", model.getTags());
                bundle.putString("extra_info_pic", model.getPic());
                startFragment(InfoDetailFragment.class, bundle);
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        mInfoList.clear();
        HttpUtil.getInstance().get("http://lakeshire.top/info/infos", new HttpUtil.Callback() {
            @Override
            public void onFail(String error) {
                Logger.d("onFail");
            }

            @Override
            public void onSuccess(String response) {
                Logger.d(response);
                if (response != null && !response.isEmpty()) {
                    List<Info> infos = JSON.parseArray(response, Info.class);
                    mInfoList.addAll(infos);
                    notifyAdapter();
                }
            }
        }, 0);
    }

    class InfoAdapter extends BaseAdapter<Info> {

        public InfoAdapter(Context mContext, List<Info> mListData, int mLayoutResId) {
            super(mContext, mListData, mLayoutResId);
        }

        @Override
        public void bindViewData(ViewHolder viewHolder, Info item, int position) {
            viewHolder.setText(R.id.tv_title, item.getTitle());
        }
    }

    @Override
    protected boolean checkCanRefresh(EnhancePtrFrameLayout frame, View content, View header) {
        ListView absListView = mLvInfo;
        boolean canRefresh =  !(absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
        return canRefresh;
    }

    private void notifyAdapter() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
