package com.edkornev.vkgallery.ui.preview.presenters;

import com.edkornev.vkgallery.ui.base.tasks.BaseTask;
import com.edkornev.vkgallery.ui.preview.tasks.PreviewLoadPhotoTask;
import com.edkornev.vkgallery.ui.preview.views.PreviewView;
import com.edkornev.vkgallery.utils.api.models.response.PhotoListResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;

import java.util.ArrayList;

/**
 * Created by kornev on 11/11/16.
 */
public class PreviewPresenter implements BaseTask.TaskListener<PhotoListResponse> {

    private static final String TAG = PreviewPresenter.class.getSimpleName();

    private PreviewView mView;
    private ArrayList<PhotoResponse> mPhotos = new ArrayList<>();
    private int mIsMore;
    private long mCount;

    public PreviewPresenter(PreviewView mView) {
        this.mView = mView;
    }

    public void loadPhotos() {
        if (mIsMore == 1) {
            PreviewLoadPhotoTask task = new PreviewLoadPhotoTask(this);
            task.execute((long) mPhotos.size());
        }
    }

    public void restoreState(ArrayList<PhotoResponse> photos, int isMore) {
        this.mPhotos = photos;
        this.mIsMore = isMore;

        mView.loadedPhotos();
    }

    public ArrayList<PhotoResponse> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(ArrayList<PhotoResponse> photos) {
        this.mPhotos = photos;
    }

    public int getMore() {
        return mIsMore;
    }

    public void setMore(int isMore) {
        this.mIsMore = isMore;
    }

    public long getCount() {
        return mCount;
    }

    public void setCount(long count) {
        this.mCount = count;
    }

    @Override
    public void onSuccess(PhotoListResponse response) {
        mPhotos.addAll(response.getItems());
        mCount = response.getCount();
        mIsMore = response.getMore();

        mView.loadedPhotos();
    }

    @Override
    public void onError(int resId) {
        mView.onShowError(resId);
    }
}
