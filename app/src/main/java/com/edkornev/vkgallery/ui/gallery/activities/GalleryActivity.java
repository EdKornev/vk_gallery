package com.edkornev.vkgallery.ui.gallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.ui.base.activities.BaseActivity;
import com.edkornev.vkgallery.ui.gallery.adapters.GalleryAdapter;
import com.edkornev.vkgallery.ui.gallery.presenters.GalleryPresenter;
import com.edkornev.vkgallery.ui.gallery.views.GalleryView;
import com.edkornev.vkgallery.ui.preview.activities.PreviewActivity;

/**
 * Created by Eduard on 10.11.2016.
 */
public class GalleryActivity extends BaseActivity implements GalleryView {

    private static final String KEY_MANAGER = "scrolls";

    private RecyclerView mRVResults;

    private GalleryAdapter mAdapter;
    private GridLayoutManager mManager;
    private GalleryPresenter mPresenter = new GalleryPresenter(this);

    private Parcelable mManagerSavedState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mAdapter = new GalleryAdapter(this, mPresenter);

        mRVResults = (RecyclerView) findViewById(R.id.rv_results);
        mRVResults.setLayoutManager(mManager);
        mRVResults.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.loadPhotos();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(KEY_MANAGER, mManager.onSaveInstanceState());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mManagerSavedState = savedInstanceState.getParcelable(KEY_MANAGER);
    }

    /* Gallery view */
    @Override
    public void loadedPhotos() {
        mAdapter.notifyDataSetChanged();

        if (mManagerSavedState != null) {
            mManager.onRestoreInstanceState(mManagerSavedState);
        }
    }

    @Override
    public void onClickPhoto(int position) {
        Intent activity = new Intent(this, PreviewActivity.class);
        startActivity(activity);
    }
}
