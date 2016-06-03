package com.github.lakeshire.discounts.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.util.ImageUtil;
import com.github.lakeshire.discounts.view.tagview.Tag;
import com.github.lakeshire.discounts.view.tagview.TagListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/2.
 */
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString("extra_info_url");
            mTitle = args.getString("extra_info_title");
            mContent = args.getString("extra_info_description");
            mPic = args.getString("extra_info_pic");
            generateTags(args.getString("extra_info_tags"));
        }
    }

    private void generateTags(String tagString) {
        if (tagString != null && !tagString.isEmpty()) {
            mTagTitles = tagString.split(",");
        }
    }

    @Override
    void initUI() {
        showAction(R.drawable.ic_search);
        showBack(true);
        setTitle(mTitle);

        mTvInfoTitle = (TextView) find(R.id.tv_info_title);
        mTvInfoTitle.setText(mTitle);
        mTvInfoContent = (TextView) find(R.id.tv_info_content);
        mTvInfoContent.setText(mContent);
        mTvInfoUrl = (TextView) find(R.id.tv_info_url);
        mTvInfoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("extra_url", mUrl);
                bundle.putString("extra_title", mTitle);
                startFragment(WebFragment.class, bundle);
            }
        });

        mTagListView = (TagListView) find(R.id.tagview);
        mTagListView.setDeleteMode(false);

        mIvPic = (ImageView) find(R.id.iv_pic);
        ImageUtil.getInstance(getActivity()).setImage(mIvPic, mPic, 0, 0, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_info_detail;
    }

    @Override
    public void loadData() {
        super.loadData();
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
    }
}
