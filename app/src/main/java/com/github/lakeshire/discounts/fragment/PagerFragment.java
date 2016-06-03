package com.github.lakeshire.discounts.fragment;

import com.github.lakeshire.discounts.fragment.base.BasePullFragment;

/**
 * Created by nali on 2016/6/3.
 */
public abstract class PagerFragment extends BasePullFragment {

    private String title;
    private int iconId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
