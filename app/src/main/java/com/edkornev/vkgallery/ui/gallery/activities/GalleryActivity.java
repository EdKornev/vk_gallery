package com.edkornev.vkgallery.ui.gallery.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.ui.base.activities.BaseActivity;
import com.edkornev.vkgallery.ui.gallery.adapters.GalleryAdapter;
import com.edkornev.vkgallery.ui.gallery.presenters.GalleryPresenter;
import com.edkornev.vkgallery.ui.gallery.views.GalleryView;

/**
 * Created by Eduard on 10.11.2016.
 */
public class GalleryActivity extends BaseActivity implements GalleryView {

    private RecyclerView mRVResults;

    private GalleryAdapter mAdapter;
    private GalleryPresenter mPresenter = new GalleryPresenter(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridLayoutManager manager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mAdapter = new GalleryAdapter(this, mPresenter);

        mRVResults = (RecyclerView) findViewById(R.id.rv_results);
        mRVResults.setLayoutManager(manager);
        mRVResults.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.loadPhotos();
    }

    /* Gallery view */
    @Override
    public void loadedPhotos() {
        mAdapter.notifyDataSetChanged();
    }
}
