package com.github.lakeshire.discounts.biz.info;

import com.github.lakeshire.discounts.model.Info;

import java.util.List;

public interface InfoLoadListener {
    int ERR_FAIL = 0;
    int ERR_NO_CONTENT = 1;

    void onInfoLoadSuccess(List<Info> infos);
    void onInfoLoadFail(int err);
    void onSingleInfoLoadSuccess(Info info);
}
