package com.github.lakeshire.discounts.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.base.BaseAdapter;
import com.github.lakeshire.discounts.adapter.base.ViewHolder;
import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.model.User;
import com.github.lakeshire.discounts.util.BitmapUtil;
import com.github.lakeshire.discounts.util.ImageUtil;
import com.github.lakeshire.discounts.util.ScreenUtil;
import com.github.lakeshire.discounts.view.BlurableImageView;
import com.github.lakeshire.discounts.view.pulltozoom.PullToZoomListViewEx;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/2.
 */
public class MeFragment extends PagerFragment {

//    ListView mLvInfo;
    private PullToZoomListViewEx mListView;
    private InfoAdapter mAdapter;
    private List<Info> mInfoList = new ArrayList<>();
    private TextView mTvName;
    private ImageView mIvAvatar;
    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initUi() {

        super.initUi();

        mAdapter = new InfoAdapter(getActivity(), mInfoList, R.layout.item_source);
        mListView = (PullToZoomListViewEx) find(R.id.listview);
        mListView.setAdapter(mAdapter);

        int mScreenWidth = ScreenUtil.getScreenWidth(getActivity());
        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (mScreenWidth * 12 / 16f));
        mListView.setHeaderLayoutParams(localObject);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info model = (Info) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                Logger.d("id: " + model.getId());
                bundle.putString("extra_id", model.getId());
//                bundle.putString("extra_info_url", model.getUrl());
//                bundle.putString("extra_info_title", model.getTitle());
//                bundle.putString("extra_info_description", model.getDescription());
//                bundle.putString("extra_info_tags", model.getTags());
//                bundle.putString("extra_info_pic", model.getPic());
                startFragment(InfoDetailFragment.class, bundle);
            }
        });

        BlurableImageView zoomView = (BlurableImageView) mListView.getZoomView();
        Bitmap bitmap = BitmapUtil.reduce(getContext(), R.drawable.image3, 256, 256);
        zoomView.blur(new BitmapDrawable(getActivity().getResources(), bitmap), "blur", true);

        View headerView = mListView.getHeaderView();
        mTvName = (TextView) headerView.findViewById(R.id.tv_name);
        mIvAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserManager.getUser();
                if (user == null) {
                    startFragment(LoginFragment.class);
                }
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();

//        HttpUtil.getInstance().get("http://lakeshire.top/info/infos", new HttpUtil.Callback() {
//            @Override
//            public void onFail(String error) {
//                showNetworkErrorLayout();
//            }
//
//            @Override
//            public void onSuccess(String response) {
//                if (response != null && !response.isEmpty()) {
//                    List<Info> infos = JSON.parseArray(response, Info.class);
//                    if (infos.isEmpty()) {
//                        showNoContentLayout();
//                    } else {
//                        mInfoList.addAll(infos);
//                        notifyAdapter();
//                    }
//                }
//            }
//        }, 0);
    }

//    @Override
//    protected boolean checkCanRefresh(EnhancePtrFrameLayout frame, View content, View header) {
//
//    }

    @Override
    public void onResume() {
        super.onResume();

        mUser = UserManager.getUser();
        if (mUser != null) {
            mInfoList.clear();
            mInfoList.addAll(mUser.getCollections());
            Logger.d("mInfoList: " + mInfoList.size());
            ImageUtil.getInstance(getActivity()).setImage(mIvAvatar, R.drawable.image2, 256, 256, true);
            mTvName.setText(mUser.getName());
        } else {
            ImageUtil.getInstance(getActivity()).setImage(mIvAvatar, R.drawable.avatar_default, 256, 256, true);
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
