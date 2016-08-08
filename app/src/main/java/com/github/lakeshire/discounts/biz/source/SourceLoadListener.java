package com.github.lakeshire.discounts.biz.source;

import com.github.lakeshire.discounts.model.Source;

import java.util.List;

public interface SourceLoadListener {
    int ERR_FAIL = 0;
    int ERR_NO_CONTENT = 1;

    void onSourceLoadSuccess(List<Source> sources);
    void onSourceLoadFail(int err);
}
