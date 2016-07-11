package com.github.lakeshire.discounts.fragment.myspace;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.adapter.InfoAdapter;
import com.github.lakeshire.discounts.fragment.IDataDisplayView;
import com.github.lakeshire.discounts.fragment.base.PagerFragment;
import com.github.lakeshire.discounts.fragment.info.InfoDetailFragment;
import com.github.lakeshire.discounts.fragment.login.LoginFragment;
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

public class MySpaceFragment extends PagerFragment implements IDataDisplayView {

    private PullToZoomListViewEx mListView;
    private TextView mTvName;
    private ImageView mIvAvatar;
    private User mUser;
    private List<Info> mInfoList = new ArrayList<>();
    private InfoAdapter mAdapter;

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
                bundle.putString("extra_id", model.getId());
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
    protected void initUI() {

    }

    @Override
    public void onResume() {
        super.onResume();

        mUser = UserManager.getUser();
        if (mUser != null) {
            mInfoList.clear();
            mInfoList.addAll(mUser.getCollections());
            ImageUtil.getInstance(getActivity()).setImage(mIvAvatar, R.drawable.image2, 256, 256, true);
            mTvName.setText(mUser.getName());
        } else {
            ImageUtil.getInstance(getActivity()).setImage(mIvAvatar, R.drawable.avatar_default, 256, 256, true);
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

    @Override
    public void onDataLoaded() {

    }

    @Override
    public void onDataLoadedFail() {

    }

    @Override
    public void onNoDataLoaded() {

    }
}
