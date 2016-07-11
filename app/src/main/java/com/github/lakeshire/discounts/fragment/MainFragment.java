package com.github.lakeshire.discounts.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.base.DBaseFragment;
import com.github.lakeshire.discounts.fragment.base.PagerFragment;
import com.github.lakeshire.discounts.fragment.info.DiscoverFragment;
import com.github.lakeshire.discounts.fragment.myspace.MySpaceFragment;
import com.github.lakeshire.discounts.fragment.source.SourceFragment;
import com.github.lakeshire.discounts.view.viewpagerindicator.IconPagerAdapter;
import com.github.lakeshire.discounts.view.viewpagerindicator.IconTabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends DBaseFragment {

    private ViewPager mViewPager;
    private IconTabPageIndicator mIndicator;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initUI() {
        showAction(R.drawable.ic_search);
        showBack(true);
        setTitle("折扣信息聚合");

        mViewPager = (ViewPager) find(R.id.viewpager);
        mIndicator = (IconTabPageIndicator) find(R.id.indicator);
        List<PagerFragment> fragments = initFragments();
        FragmentAdapter adapter = new FragmentAdapter(fragments, getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    mTitleBar.setVisibility(View.GONE);
                } else {
                    mTitleBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<PagerFragment> initFragments() {
        List<PagerFragment> fragments = new ArrayList<>();

        PagerFragment infoFragment = new DiscoverFragment();
        infoFragment.setTabTitle("发现");
        infoFragment.setIconId(R.drawable.selector_tab_discover);
        fragments.add(infoFragment);

        PagerFragment categoryFragment = new SourceFragment();
        categoryFragment.setTabTitle("分类");
        categoryFragment.setIconId(R.drawable.selector_tab_cate);
        fragments.add(categoryFragment);

        PagerFragment meFragment = new MySpaceFragment();
        meFragment.setTabTitle("我的");
        meFragment.setIconId(R.drawable.selector_tab_me);
        fragments.add(meFragment);

        return fragments;
    }

    class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        private List<PagerFragment> mFragments;

        public FragmentAdapter(List<PagerFragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getIconResId(int index) {
            return mFragments.get(index).getIconId();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTabTitle();
        }
    }

    @Override
    public void loadData() {
        super.loadData();
    }

    /**
     * 主Fragment必须重写这个
     * @return
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }
}
