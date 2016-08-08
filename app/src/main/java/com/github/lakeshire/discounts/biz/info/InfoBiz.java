package com.github.lakeshire.discounts.biz.info;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.discounts.model.CollectParam;
import com.github.lakeshire.discounts.model.CollectResult;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.model.InfoResult;
import com.github.lakeshire.discounts.model.User;
import com.github.lakeshire.lemon.util.HttpUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

public class InfoBiz implements IInfoBiz {

    @Override
    public void getInfos(String source, int pageId, final InfoLoadListener listener) {
        HttpUtil.getInstance().get("http://lakeshire.top/info/infos" + (source == null ? "" : "?source=" + source), new HttpUtil.Callback() {
            @Override
            public void onFail(String error) {
                listener.onInfoLoadFail(InfoLoadListener.ERR_FAIL);
            }

            @Override
            public void onSuccess(String response) {
                InfoResult result = JSON.parseObject(response, InfoResult.class);
                if (result.getRet() == 0) {
                    List<Info> infos = result.getInfos();
                    if (infos.isEmpty()) {
                        listener.onInfoLoadFail(InfoLoadListener.ERR_NO_CONTENT);
                    } else {
                        listener.onInfoLoadSuccess(infos);
                    }
                }
            }
        }, 0);
    }

    @Override
    public void getInfo(String id, final InfoLoadListener listener) {
        HttpUtil.getInstance().get("http://lakeshire.top/info/" + id, new HttpUtil.Callback() {
            @Override
            public void onFail(String error) {
                listener.onInfoLoadFail(InfoLoadListener.ERR_FAIL);
            }

            @Override
            public void onSuccess(String response) {
                if (response != null && !response.isEmpty()) {
                    List<Info> infos = JSON.parseArray(response, Info.class);
                    if (infos != null && !infos.isEmpty()) {
                        Info info = infos.get(0);

                        User user = UserManager.getUser();
                        if (user != null) {
                            List<Info> collections = user.getCollections();
                            for (Info i : collections) {
                                if (i.getId().equals(info.getId())) {
                                    info.setCollected(true);
                                    break;
                                }
                            }
                        }

                        listener.onSingleInfoLoadSuccess(info);
                    }
                }
            }
        }, 0);
    }

    @Override
    public void collectInfo(String id, String title, final InfoCollectChangeListener listener) {
        CollectParam param = new CollectParam();
        param.setUser(UserManager.getUser());
        Info info = new Info();
        info.setId(id);
        info.setTitle(title);
        param.setInfo(info);
        String json = JSON.toJSONString(param);
        Logger.d("collect: " + json);
        try {
            HttpUtil.getInstance().post("http://lakeshire.top/collection/add", json, new HttpUtil.Callback() {
                @Override
                public void onFail(String error) {
                    Logger.d("collect fail");
                    listener.onFail();
                }

                @Override
                public void onSuccess(String response) {
                    Logger.d("collect success: " + response);
                    CollectResult cp = JSON.parseObject(response, CollectResult.class);
                    if (cp.getRet() == 0) {
                        UserManager.getUser().setCollections(cp.getUser().getCollections());
                        listener.onCollected();
                    } else {
                        listener.onFail();
                    }
                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unCollectInfo(String id, String title, final InfoCollectChangeListener listener) {
        CollectParam param = new CollectParam();
        param.setUser(UserManager.getUser());
        Info info = new Info();
        info.setId(id);
        info.setTitle(title);
        param.setInfo(info);
        String json = JSON.toJSONString(param);
        Logger.d("uncollect: " + json);
        try {
            HttpUtil.getInstance().post("http://lakeshire.top/collection/delete", json, new HttpUtil.Callback() {
                @Override
                public void onFail(String error) {
                    Logger.d("uncollect fail");
                    listener.onFail();
                }

                @Override
                public void onSuccess(String response) {
                    Logger.d("uncollect success: " + response);
                    CollectResult cp = JSON.parseObject(response, CollectResult.class);
                    if (cp.getRet() == 0) {
                        UserManager.getUser().setCollections(cp.getUser().getCollections());
                        listener.onUnCollected();
                    } else {
                        listener.onFail();
                    }
                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
