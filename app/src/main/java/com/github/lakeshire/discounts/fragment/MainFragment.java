package com.github.lakeshire.discounts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.base.BaseAdapter;
import com.github.lakeshire.discounts.adapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/2.
 */
public class MainFragment extends DBasePullFragment {

//    ListView mLvSource;
    GridView mGvSource;
    private ArrayList<Source> mSourceList = new ArrayList<>();
    private SourceAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    void initUI() {
        showAction(R.drawable.ic_search);
        showBack(true);
        setTitle("折扣信息聚合");

//        mLvSource = (ListView) find(R.id.list);
        mGvSource = (GridView) find(R.id.grid);
        mAdapter = new SourceAdapter(getActivity(), mSourceList, R.layout.item_source_grid);
//        mLvSource.setAdapter(mAdapter);
        mGvSource.setAdapter(mAdapter);
//        mLvSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        mSourceList.add(new Source("麦当劳", R.drawable.source_mcdonald));
        mSourceList.add(new Source("肯德基", R.drawable.source_kfc));
        mSourceList.add(new Source("招商银行", R.drawable.source_mcdonald));
        mSourceList.add(new Source("浦发银行", R.drawable.source_mcdonald));
        mSourceList.add(new Source("建设银行", R.drawable.source_mcdonald));
        mSourceList.add(new Source("农业银行", R.drawable.source_mcdonald));
        mAdapter.notifyDataSetChanged();
    }

    class SourceAdapter extends BaseAdapter<Source> {

        public SourceAdapter(Context mContext, List<Source> mListData, int mLayoutResId) {
            super(mContext, mListData, mLayoutResId);
        }

        @Override
        public void bindViewData(ViewHolder viewHolder, Source item, int position) {
//            viewHolder.setText(R.id.tv_title, item);
            viewHolder.setText(R.id.tv_name, item.getName());
            viewHolder.setImageResource(R.id.iv_logo, item.getPic());
        }
    }

    /**
     * 主Fragment必须重写这个
     * @return
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }

    class Source {
        private int pic;
        private String name;

        public Source(String name, int pic) {
            this.name = name;
            this.pic = pic;
        }

        public String getName() {
            return name;
        }

        public int getPic() {
            return pic;
        }
    }
//    @Override
//    protected boolean checkCanRefresh(EnhancePtrFrameLayout frame, View content, View header) {
//        ListView absListView = mLvSource;
//        boolean canRefresh =  !(absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
//        return canRefresh;
//    }
}
