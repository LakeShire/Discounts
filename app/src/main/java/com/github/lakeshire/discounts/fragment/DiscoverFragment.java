package com.github.lakeshire.discounts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.base.BaseAdapter;
import com.github.lakeshire.discounts.adapter.base.ViewHolder;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.model.InfoResult;
import com.github.lakeshire.discounts.util.HttpUtil;
import com.github.lakeshire.discounts.view.LoadMoreListView;
import com.github.lakeshire.discounts.view.pulltofresh.EnhancePtrFrameLayout;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/2.
 */
public class DiscoverFragment extends PagerFragment {

    LoadMoreListView mLvInfo;
    private ArrayList<Info> mInfoList = new ArrayList<>();
    private InfoAdapter mAdapter;
    private String mSource;
    private int mTotalPage = 0;
    private int mPageId = 0;
    private boolean loading;
    private boolean isRefresh;

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
        super.initUi();
        mLvInfo = (LoadMoreListView) find(R.id.list);
        mLvInfo.setLoadMoreCallback(new LoadMoreListView.Callback() {
            @Override
            public void loadMore() {
                mLvInfo.onLoadMoreComplete(LoadMoreListView.STATUS_LOADING);
                mPageId++;
                getData();
            }

            @Override
            public void initFooter(View view) {
                ImageView ivAnim = (ImageView) view.findViewById(R.id.iv_anim);
                FadingCircle cg = new FadingCircle();
                int color = getResources().getColor(R.color.tiffany);
                cg.setColor(color);
                ivAnim.setBackgroundDrawable(cg);
                cg.start();
            }
        });
        mAdapter = new InfoAdapter(getActivity(), mInfoList, R.layout.item_source);
        mLvInfo.setAdapter(mAdapter);
        mLvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info model = (Info) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("extra_id", model.getId());
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
        showLoadingLayout();
        getData();
    }

    private void getData() {
        if (!loading) {
            loading = true;
            HttpUtil.getInstance().get("http://lakeshire.top/info/infos?pageId=" + mPageId, new HttpUtil.Callback() {
                @Override
                public void onFail(String error) {
                    mLvInfo.onLoadMoreComplete(LoadMoreListView.STATUS_NETWORK_ERROR);
                    loading = false;
                    showNetworkErrorLayout();
                }

                @Override
                public void onSuccess(String response) {
                    loading = false;
                    if (response != null && !response.isEmpty()) {
                        InfoResult result = JSON.parseObject(response, InfoResult.class);
                        if (result.getRet() == 0) {
                            mTotalPage = result.getTotalPage();
                            final List<Info> infos = result.getInfos();
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (infos.isEmpty()) {
                                            showNoContentLayout();
                                            mLvInfo.onLoadMoreComplete(LoadMoreListView.STATUS_NO_CONTENT);
                                        } else {
                                            mInfoList.addAll(infos);
                                            hideAllLayout();
                                            mAdapter.notifyDataSetChanged();
                                            if (isRefresh) {
                                                mPtrFrameLayout.refreshComplete();
                                            }
                                            if (mPageId == mTotalPage - 1) {
                                                mLvInfo.onLoadMoreComplete(LoadMoreListView.STATUS_NO_MORE);
                                            }                                        }
                                    }
                                });

                            }
                        }
                    }
                }
            }, 0);
        }
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

    @Override
    protected void onRefresh(EnhancePtrFrameLayout frame) {
        super.onRefresh(frame);
        refresh();
    }

    private void refresh() {
        mInfoList.clear();
        mPageId = 0;
        isRefresh = true;
        getData();
        mLvInfo.resetStatus();
    }
}
