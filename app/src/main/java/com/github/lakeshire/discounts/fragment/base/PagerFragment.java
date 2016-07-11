package com.github.lakeshire.discounts.fragment.base;

public abstract class PagerFragment extends DBasePullFragment {

    private String tabTitle;
    private int iconId;

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String title) {
        this.tabTitle = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
