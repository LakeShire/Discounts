package com.github.lakeshire.discounts.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.discounts.model.User;
import com.github.lakeshire.discounts.view.pulltofresh.EnhancePtrFrameLayout;

/**
 * Created by nali on 2016/6/2.
 */
public class MeFragment extends PagerFragment {

    ListView mLvInfo;
    private Button mBtnLogin;
    private TextView mTvStatus;

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
//        showAction(R.drawable.ic_search);
//        showBack(true);
//        setTitle(mSource);
        mBtnLogin = (Button) find(R.id.btn_login);
        mTvStatus = (TextView) find(R.id.tv_status);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(LoginFragment.class);
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
    }

    @Override
    protected boolean checkCanRefresh(EnhancePtrFrameLayout frame, View content, View header) {
        ListView absListView = mLvInfo;
        boolean canRefresh =  !(absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
        return canRefresh;
    }

    @Override
    public void onResume() {
        super.onResume();

        User user = UserManager.getUser();
        if (user == null) {
            mTvStatus.setText("你还没有登录");
        } else {
            mBtnLogin.setVisibility(View.GONE);
            mTvStatus.setText(user.getName());
        }
    }
}
