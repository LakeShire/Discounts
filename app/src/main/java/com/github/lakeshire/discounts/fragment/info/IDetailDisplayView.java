package com.github.lakeshire.discounts.fragment.info;

import com.github.lakeshire.discounts.fragment.IDataDisplayView;

/**
 * Created by nali on 2016/6/29.
 */
public interface IDetailDisplayView extends IDataDisplayView {
    void onDetailLoad(Object object);

    void onCollectChanged(boolean collected);

    void onCollectFail();
}
