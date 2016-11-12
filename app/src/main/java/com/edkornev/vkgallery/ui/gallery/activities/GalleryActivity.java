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
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;

import java.util.ArrayList;

/**
 * Created by Eduard on 10.11.2016.
 */
public class GalleryActivity extends BaseActivity implements GalleryView {

    private static final String KEY_STATE_MANAGER = "scrolls";
    private static final String KEY_STATE_LIST = "list";
    private static final String KEY_STATE_MORE = "more";
    private static final String KEY_STATE_COUNT = "count";

    private RecyclerView mRVResults;

    private GalleryAdapter mAdapter;
    private GridLayoutManager mManager;
    private GalleryPresenter mPresenter = new GalleryPresenter(this);

    private Parcelable mManagerSavedState;
    private boolean mIsNeedLoading = true;
    private int mPastVisibleItem, mVisibleItemCount, mTotalItemCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mAdapter = new GalleryAdapter(this, mPresenter);

        mRVResults = (RecyclerView) findViewById(R.id.rv_results);
        mRVResults.setLayoutManager(mManager);
        mRVResults.setAdapter(mAdapter);
        mRVResults.addOnScrollListener(mScrollListener);

        if (savedInstanceState == null) {
            mPresenter.loadPhotos();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(KEY_STATE_MANAGER, mManager.onSaveInstanceState());
        savedInstanceState.putParcelableArrayList(KEY_STATE_LIST, mPresenter.getPhotos());
        savedInstanceState.putInt(KEY_STATE_MORE, mPresenter.getMore());
        savedInstanceState.putLong(KEY_STATE_COUNT, mPresenter.getCount());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mManagerSavedState = savedInstanceState.getParcelable(KEY_STATE_MANAGER);
        ArrayList<PhotoResponse> photos = savedInstanceState.getParcelableArrayList(KEY_STATE_LIST);

        mPresenter.restoreState(
                photos,
                savedInstanceState.getInt(KEY_STATE_MORE),
                savedInstanceState.getLong(KEY_STATE_COUNT)
        );
    }

    /* Gallery view */
    @Override
    public void loadedPhotos() {
        mAdapter.notifyDataSetChanged();

        if (mManagerSavedState != null) {
            mManager.onRestoreInstanceState(mManagerSavedState);
        }

        mIsNeedLoading = true;
    }

    @Override
    public void onClickPhoto(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PreviewActivity.KEY_INTENT_LIST, mPresenter.getPhotos());
        bundle.putInt(PreviewActivity.KEY_INTENT_MORE, mPresenter.getMore());
        bundle.putInt(PreviewActivity.KEY_INTENT_POSITION, position);
        bundle.putLong(PreviewActivity.KEY_INTENT_COUNT, mPresenter.getCount());

        Intent activity = new Intent(this, PreviewActivity.class);
        activity.putExtras(bundle);

        startActivity(activity);
    }

    @Override
    public void showError(int resId) {
        super.showErrorDialog(getString(resId));
    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if(dy > 0) {
                mVisibleItemCount = mManager.getChildCount();
                mTotalItemCount = mManager.getItemCount();
                mPastVisibleItem = mManager.findFirstVisibleItemPosition();

                if (mIsNeedLoading) {
                    if ((mVisibleItemCount + mPastVisibleItem) >= mTotalItemCount) {
                        mIsNeedLoading = false;

                        mPresenter.loadPhotos();
                    }
                }
            }
        }
    };
}
