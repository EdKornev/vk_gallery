package com.edkornev.vkgallery.ui.preview.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.ui.base.activities.BaseActivity;
import com.edkornev.vkgallery.ui.preview.adapters.PreviewAdapter;
import com.edkornev.vkgallery.ui.preview.presenters.PreviewPresenter;
import com.edkornev.vkgallery.ui.preview.views.PreviewView;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;

import java.util.ArrayList;

/**
 * Created by kornev on 11/11/16.
 */
public class PreviewActivity extends BaseActivity implements PreviewView, ViewPager.OnPageChangeListener {

    private static final String KEY_STATE_POSITION = "position";
    private static final String KEY_STATE_LIST = "list";
    private static final String KEY_STATE_MORE = "more";

    public static final String KEY_INTENT_LIST = "list";
    public static final String KEY_INTENT_MORE = "more";
    public static final String KEY_INTENT_POSITION = "position";
    public static final String KEY_INTENT_COUNT = "count";

    private ViewPager mVPContent;
    private TextView mTVCount;
    private TextView mTVLikes;
    private TextView mTVShare;

    private PreviewAdapter mAdapter;
    private PreviewPresenter mPresenter = new PreviewPresenter(this);
    private int mCurrentPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_preview);

        mAdapter = new PreviewAdapter(getSupportFragmentManager(), mPresenter);

        mVPContent = (ViewPager) findViewById(R.id.vp_content);
        mVPContent.setAdapter(mAdapter);
        mVPContent.addOnPageChangeListener(this);

        mTVCount = (TextView) findViewById(R.id.tv_count);
        mTVLikes = (TextView) findViewById(R.id.tv_likes);
        mTVShare = (TextView) findViewById(R.id.tv_shares);

        if (getIntent() != null) {
            ArrayList<PhotoResponse> photos = getIntent().getExtras().getParcelableArrayList(KEY_INTENT_LIST);
            mPresenter.setPhotos(photos);
            mPresenter.setMore(getIntent().getExtras().getInt(KEY_INTENT_MORE, 1));
            mPresenter.setCount(getIntent().getExtras().getLong(KEY_INTENT_COUNT, 0));

            mAdapter.notifyDataSetChanged();
            mVPContent.setCurrentItem(getIntent().getExtras().getInt(KEY_INTENT_POSITION, 0), false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_STATE_POSITION, mVPContent.getCurrentItem());
        savedInstanceState.putParcelableArrayList(KEY_STATE_LIST, mPresenter.getPhotos());
        savedInstanceState.putInt(KEY_STATE_MORE, mPresenter.getMore());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<PhotoResponse> photos = savedInstanceState.getParcelableArrayList(KEY_STATE_LIST);

        mPresenter.restoreState(
                photos,
                savedInstanceState.getInt(KEY_STATE_MORE)
        );

        mCurrentPosition = savedInstanceState.getInt(KEY_STATE_POSITION);
        mVPContent.setCurrentItem(mCurrentPosition, false);
    }

    /* PreviewView */
    @Override
    public void loadedPhotos() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowError(int resId) {
        super.showErrorDialog(getString(resId));
    }

    /* OnPageChangeListener */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mTVCount.setText(getString(R.string.activity_preview_tv_count, position + 1, mPresenter.getCount()));

        PhotoResponse photoResponse = mPresenter.getPhotos().get(position);
        mTVLikes.setText(getString(R.string.activity_preview_tv_likes, photoResponse.getLikes().getCount()));
        mTVShare.setText(getString(R.string.activity_preview_tv_shares, photoResponse.getReposts().getCount()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
