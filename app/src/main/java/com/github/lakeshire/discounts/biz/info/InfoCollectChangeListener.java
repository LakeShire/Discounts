package com.github.lakeshire.discounts.biz.info;

public interface InfoCollectChangeListener {
    void onCollected();
    void onUnCollected();
    void onFail();
}
