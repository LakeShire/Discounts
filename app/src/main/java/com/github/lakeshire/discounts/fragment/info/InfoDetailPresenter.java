package com.github.lakeshire.discounts.fragment.info;

import com.github.lakeshire.discounts.biz.info.IInfoBiz;
import com.github.lakeshire.discounts.biz.info.InfoBiz;
import com.github.lakeshire.discounts.biz.info.InfoCollectChangeListener;
import com.github.lakeshire.discounts.biz.info.InfoLoadListener;
import com.github.lakeshire.discounts.model.Info;

import java.util.List;

public class InfoDetailPresenter {

    private IInfoBiz mInfoBiz;
    private IDetailDisplayView mInfoDetailView;

    public InfoDetailPresenter(IDetailDisplayView infoDetailView) {
        this.mInfoDetailView = infoDetailView;
        this.mInfoBiz = new InfoBiz();
    }

    public void getInfo(String id) {
        mInfoBiz.getInfo(id, new InfoLoadListener() {
            @Override
            public void onInfoLoadSuccess(List<Info> infos) {

            }

            @Override
            public void onInfoLoadFail(int err) {
                if (err == InfoLoadListener.ERR_FAIL) {
                    mInfoDetailView.onDataLoadedFail();
                } else if (err == InfoLoadListener.ERR_NO_CONTENT) {
                    mInfoDetailView.onNoDataLoaded();
                }
            }

            @Override
            public void onSingleInfoLoadSuccess(Info info) {
                mInfoDetailView.onDetailLoad(info);
            }
        });
    }

    public void collect(String id, String title) {
        mInfoBiz.collectInfo(id, title, new InfoCollectChangeListener() {
            @Override
            public void onCollected() {
                mInfoDetailView.onCollectChanged(true);
            }

            @Override
            public void onUnCollected() {

            }

            @Override
            public void onFail() {
                mInfoDetailView.onCollectFail();
            }
        });
    }

    public void unCollect(String id, String title) {
        mInfoBiz.unCollectInfo(id, title, new InfoCollectChangeListener() {
            @Override
            public void onCollected() {

            }

            @Override
            public void onUnCollected() {
                mInfoDetailView.onCollectChanged(false);
            }

            @Override
            public void onFail() {
                mInfoDetailView.onCollectFail();
            }
        });
    }
}
