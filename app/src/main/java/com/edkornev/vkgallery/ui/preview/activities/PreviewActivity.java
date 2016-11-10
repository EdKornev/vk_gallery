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

/**
 * Created by kornev on 11/11/16.
 */
public class PreviewActivity extends BaseActivity implements PreviewView, ViewPager.OnPageChangeListener {

    private ViewPager mVPContent;
    private TextView mTVCount;

    private PreviewAdapter mAdapter;
    private PreviewPresenter mPresenter = new PreviewPresenter(this);

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
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.loadPhotos();
    }

    /* PreviewView */
    @Override
    public void loadedPhotos() {
        mAdapter.notifyDataSetChanged();

        mTVCount.setText(getString(R.string.activity_preview_tv_count, 1, mPresenter.getCount()));
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
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
