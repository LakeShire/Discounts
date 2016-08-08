package com.github.lakeshire.discounts.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.manager.UserManager;
import com.github.lakeshire.discounts.model.CollectParam;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.model.User;
import com.github.lakeshire.discounts.model.UserResult;
import com.github.lakeshire.discounts.view.tagview.Tag;
import com.github.lakeshire.discounts.view.tagview.TagListView;
import com.github.lakeshire.lemon.fragment.base.DBaseFragment;
import com.github.lakeshire.lemon.util.HttpUtil;
import com.github.lakeshire.lemon.util.ImageUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InfoDetailFragment extends DBaseFragment {

    private String mTitle;
    private TextView mTvInfoTitle;
    private TextView mTvInfoContent;
    private TextView mTvInfoUrl;
    private String mUrl;
    private String mContent;
    private TagListView mTagListView;
    private String[] mTagTitles;
    private List<Tag> mTags = new ArrayList<>();
    private ImageView mIvPic;
    private String mPic;
    private String mId;
    private boolean collected = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            if (!args.containsKey("extra_info_title")) {
                mId = args.getString("extra_id");
                HttpUtil.getInstance().get("http://lakeshire.top/info/" + mId, new HttpUtil.Callback() {
                    @Override
                    public void onFail(String error) {
                        showNetworkErrorLayout();
                    }

                    @Override
                    public void onSuccess(String response) {
                        if (response != null && !response.isEmpty()) {
                            List<Info> infos = JSON.parseArray(response, Info.class);
                            if (infos != null && !infos.isEmpty()) {
                                Info info = infos.get(0);
                                mUrl = info.getUrl();
                                mTitle = info.getTitle();
                                mContent = info.getDescription();
                                mPic = info.getPic();
                                generateTags(info.getTags());
                                updateUi();
                            }
                        }
                    }
                }, 0);
            } else {
                mId = args.getString("extra_id");
                mUrl = args.getString("extra_info_url");
                mTitle = args.getString("extra_info_title");
                mContent = args.getString("extra_info_description");
                mPic = args.getString("extra_info_pic");
                generateTags(args.getString("extra_info_tags"));
            }
        }
    }

    private void setCollected(final boolean b) {
        this.collected = b;
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showAction(b ? R.drawable.ic_collected : R.drawable.ic_collect);
                }
            });
        }
    }

    private void updateUi() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvInfoTitle.setText(mTitle);
                    mTvInfoContent.setText(mContent);
                    mTvInfoUrl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("extra_url", mUrl);
                            bundle.putString("extra_title", mTitle);
                            startFragment(WebFragment.class, bundle);
                        }
                    });
                    mTagListView.setDeleteMode(false);
                    ImageUtil.getInstance(getActivity()).setImageDecrease(mIvPic, mPic);
                    setTitle(mTitle);

                    if (mTagTitles != null) {
                        mTags.clear();
                        for (int i = 0; i < mTagTitles.length; i++) {
                            Tag tag = new Tag();
                            tag.setId(i);
                            tag.setChecked(true);
                            tag.setTitle(mTagTitles[i]);
                            mTags.add(tag);
                        }
                        mTagListView.setTags(mTags, false);
                    }

                    User user = UserManager.getUser();
                    if (user != null) {
                        List<Info> collections = user.getCollections();
                        for (Info i : collections) {
                            if (i.getId().equals(mId)) {
                                setCollected(true);
                                break;
                            }
                        }
                    }
                }
            });
        }
    }

    private void generateTags(String tagString) {
        if (tagString != null && !tagString.isEmpty()) {
            mTagTitles = tagString.split(",");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_info_detail;
    }

    @Override
    public void loadData() {
        super.loadData();
    }

    @Override
    protected void initUI() {
        User user = UserManager.getUser();
        if (user != null) {
            showAction(R.drawable.ic_collect);
        }
        showBack(true);

        mTvInfoTitle = (TextView) find(R.id.tv_info_title);
        mTvInfoContent = (TextView) find(R.id.tv_info_content);
        mTvInfoUrl = (TextView) find(R.id.tv_info_url);
        mTagListView = (TagListView) find(R.id.tagview);
        mIvPic = (ImageView) find(R.id.iv_pic);

        updateUi();
    }

    @Override
    public void doAction() {
        if (collected) {
            CollectParam param = new CollectParam();
            param.setUser(UserManager.getUser());
            Info info = new Info();
            info.setId(mId);
            info.setTitle(mTitle);
            param.setInfo(info);
            String json = JSON.toJSONString(param);
            try {
                HttpUtil.getInstance().post("http://lakeshire.top/collection/delete", json, new HttpUtil.Callback() {
                    @Override
                    public void onFail(String error) {
                        Logger.d("onFail");
                    }

                    @Override
                    public void onSuccess(String response) {
                        UserResult result = JSON.parseObject(response, UserResult.class);
                        if (result.getRet() == 0) {
                            User user = result.getUser();
                            UserManager.setUser(user);
                        }
                        setCollected(false);                    }
                }, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            CollectParam param = new CollectParam();
            Info info = new Info();
            info.setId(mId);
            info.setTitle(mTitle);
            param.setInfo(info);
            User user = UserManager.getUser();
            param.setUser(user);

            String json = JSON.toJSONString(param);
            try {
                HttpUtil.getInstance().post("http://lakeshire.top/collection/add", json, new HttpUtil.Callback() {
                    @Override
                    public void onFail(String error) {
                        Logger.d("onFail");
                    }

                    @Override
                    public void onSuccess(String response) {
                        UserResult result = JSON.parseObject(response, UserResult.class);
                        if (result.getRet() == 0) {
                            User user = result.getUser();
                            UserManager.setUser(user);
                        }
                        setCollected(true);
                    }
                }, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
