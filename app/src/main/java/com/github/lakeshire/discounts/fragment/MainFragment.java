package com.github.lakeshire.discounts.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.view.viewpagerindicator.IconPagerAdapter;
import com.github.lakeshire.discounts.view.viewpagerindicator.IconTabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/2.
 */
public class MainFragment extends DBaseFragment {

    private ViewPager mViewPager;
    private IconTabPageIndicator mIndicator;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    void initUI() {
        showAction(R.drawable.ic_search);
        showBack(true);
        setTitle("折扣信息聚合");

        mViewPager = (ViewPager) find(R.id.viewpager);
        mIndicator = (IconTabPageIndicator) find(R.id.indicator);
        List<PagerFragment> fragments = initFragments();
        FragmentAdapter adapter = new FragmentAdapter(fragments, getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }

    private List<PagerFragment> initFragments() {
        List<PagerFragment> fragments = new ArrayList<>();

        PagerFragment sourceFragment = new DiscoverFragment();
        sourceFragment.setTitle("发现");
        sourceFragment.setIconId(R.drawable.selector_tab_discover);
        fragments.add(sourceFragment);

        PagerFragment categoryFragment = new SourceFragment();
        categoryFragment.setTitle("分类");
        categoryFragment.setIconId(R.drawable.selector_tab_cate);
        fragments.add(categoryFragment);

        PagerFragment meFragment = new MeFragment();
        meFragment.setTitle("我的");
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
            return mFragments.get(position).getTitle();
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
