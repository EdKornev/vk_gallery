package com.edkornev.vkgallery.ui.gallery.presenters;

import com.edkornev.vkgallery.ui.base.tasks.BaseTask;
import com.edkornev.vkgallery.ui.gallery.tasks.GalleryLoadPhotoTask;
import com.edkornev.vkgallery.ui.gallery.views.GalleryView;
import com.edkornev.vkgallery.utils.api.models.response.PhotoListResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;

import java.util.ArrayList;

/**
 * Created by Eduard on 10.11.2016.
 */
public class GalleryPresenter implements BaseTask.TaskListener<PhotoListResponse> {

    private static final String TAG = GalleryPresenter.class.getSimpleName();

    private GalleryView mView;
    private ArrayList<PhotoResponse> mPhotos = new ArrayList<>();
    private int mIsMore = 1;
    private long mOffset = 0;
    private long mCount = 0;

    public GalleryPresenter(GalleryView view) {
        this.mView = view;
    }

    public void loadPhotos() {
        if (mIsMore == 1) {
            GalleryLoadPhotoTask task = new GalleryLoadPhotoTask(this);

            task.execute(mOffset);
        }
    }

    public void clickPhoto(int position) {
        mView.onClickPhoto(position);
    }

    public void restoreState(ArrayList<PhotoResponse> photos, int isMore, long count) {
        this.mPhotos = photos;
        this.mIsMore = isMore;
        this.mOffset = photos.size();
        this.mCount = count;

        mView.loadedPhotos();
    }

    public ArrayList<PhotoResponse> getPhotos() {
        return mPhotos;
    }

    public int getMore() {
        return mIsMore;
    }

    public long getCount() {
        return mCount;
    }

    @Override
    public void onSuccess(PhotoListResponse response) {
        mPhotos.addAll(response.getItems());
        mCount = response.getCount();
        mIsMore = response.getMore();
        mOffset = mPhotos.size();

        mView.loadedPhotos();
    }

    @Override
    public void onError(int resId) {
        mView.showError(resId);
    }
}
