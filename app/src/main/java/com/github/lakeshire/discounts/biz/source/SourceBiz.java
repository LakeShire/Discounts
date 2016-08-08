package com.github.lakeshire.discounts.biz.source;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.biz.info.InfoLoadListener;
import com.github.lakeshire.discounts.model.Source;
import com.github.lakeshire.discounts.util.HttpUtil;

import java.util.List;

public class SourceBiz implements ISourceBiz {

    @Override
    public void getSources(final SourceLoadListener listener) {
        HttpUtil.getInstance().get("http://lakeshire.top/source/all", new HttpUtil.Callback() {
            @Override
            public void onFail(String error) {
                listener.onSourceLoadFail(InfoLoadListener.ERR_FAIL);
            }

            @Override
            public void onSuccess(String response) {
                if (response != null && !response.isEmpty()) {
                    List<Source> sources = JSON.parseArray(response, Source.class);
                    if (sources.isEmpty()) {
                        listener.onSourceLoadFail(InfoLoadListener.ERR_NO_CONTENT);
                    } else {
                        listener.onSourceLoadSuccess(sources);
                    }
                }
            }
        }, 0);
    }
}
