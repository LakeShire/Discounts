package com.github.lakeshire.discounts.biz.info;

/**
 * Created by nali on 2016/6/29.
 */
public interface InfoCollectChangeListener {

    void onCollected();

    void onUnCollected();

    void onFail();
}
