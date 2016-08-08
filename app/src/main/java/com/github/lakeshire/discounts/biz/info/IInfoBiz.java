package com.github.lakeshire.discounts.biz.info;

public interface IInfoBiz {
    void getInfos(String source, int pageId, InfoLoadListener listener);
    void getInfo(String id, InfoLoadListener listener);
    void collectInfo(String id, String title, InfoCollectChangeListener listener);
    void unCollectInfo(String id, String title, InfoCollectChangeListener listener);
}
