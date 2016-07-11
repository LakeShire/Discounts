package com.github.lakeshire.discounts.fragment.info;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.WebFragment;
import com.github.lakeshire.discounts.fragment.base.DBaseFragment;
import com.github.lakeshire.discounts.model.Info;
import com.github.lakeshire.discounts.util.ImageUtil;
import com.github.lakeshire.discounts.view.tagview.Tag;
import com.github.lakeshire.discounts.view.tagview.TagListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class InfoDetailFragment extends DBaseFragment implements IDetailDisplayView {

    public static final String EXTRA_INFO_ID = "extra_info_id";
    public static final String EXTRA_INFO_URL = "extra_info_url";
    public static final String EXTRA_INFO_TITLE = "extra_info_title";
    public static final String EXTRA_INFO_DESCRIPTION = "extra_info_description";
    public static final String EXTRA_INFO_TAGS = "extra_info_tags";
    public static final String EXTRA_INFO_PIC = "extra_info_pic";

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
    private boolean isCollected = false;
    private InfoDetailPresenter mInfoDetailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInfoDetailPresenter = new InfoDetailPresenter(this);
        Bundle args = getArguments();
        if (args != null) {
            mId = args.getString(EXTRA_INFO_ID);
            mInfoDetailPresenter.getInfo(mId);
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

                    showAction(isCollected ? R.drawable.ic_collected : R.drawable.ic_collect);
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
    protected void initUI() {
        showAction(R.drawable.ic_collect);
        showBack(true);

        mTvInfoTitle = (TextView) find(R.id.tv_info_title);
        mTvInfoContent = (TextView) find(R.id.tv_info_content);
        mTvInfoUrl = (TextView) find(R.id.tv_info_url);
        mTagListView = (TagListView) find(R.id.tagview);
        mIvPic = (ImageView) find(R.id.iv_pic);

        updateUi();
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
    public void doAction() {
        if (isCollected) {
            mInfoDetailPresenter.unCollect(mId, mTitle);
        } else {
            mInfoDetailPresenter.collect(mId, mTitle);
        }
    }

    @Override
    public void onDetailLoad(Object object) {
        Info info = (Info) object;
        mUrl = info.getUrl();
        mTitle = info.getTitle();
        mContent = info.getDescription();
        mPic = info.getPic();
        isCollected = info.isCollected();
        generateTags(info.getTags());
        updateUi();
    }

    @Override
    public void onCollectChanged(final boolean collected) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Logger.d("onCollectChanged: " + collected);
                    isCollected = collected;
                    showAction(collected ? R.drawable.ic_collected : R.drawable.ic_collect);
                }
            });
        }
    }

    @Override
    public void onCollectFail() {
        Toast.makeText(getActivity(), "收藏状态变更失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataLoaded() {

    }

    @Override
    public void onDataLoadedFail() {

    }

    @Override
    public void onNoDataLoaded() {

    }
}
