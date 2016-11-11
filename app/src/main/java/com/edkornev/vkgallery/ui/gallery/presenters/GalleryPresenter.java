package com.edkornev.vkgallery.ui.gallery.presenters;

import android.util.Log;

import com.edkornev.vkgallery.ui.gallery.views.GalleryView;
import com.edkornev.vkgallery.utils.api.HttpApi;
import com.edkornev.vkgallery.utils.api.models.response.BaseResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoListResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;
import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eduard on 10.11.2016.
 */
public class GalleryPresenter {

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
            String accessToken = VKAccessToken.currentToken().accessToken;

            Call<BaseResponse<PhotoListResponse>> request = HttpApi.getInstance().getApi().getPhotos(1, mOffset, 1, 1, accessToken, "5.60", "52138567");
            request.enqueue(new Callback<BaseResponse<PhotoListResponse>>() {
                @Override
                public void onResponse(Call<BaseResponse<PhotoListResponse>> call, Response<BaseResponse<PhotoListResponse>> response) {
                    if (response.code() == 200) {
                        mPhotos.addAll(response.body().getResponse().getItems());
                        mCount = response.body().getResponse().getCount();
                        mIsMore = response.body().getResponse().getMore();
                        mOffset = mPhotos.size();

                        mView.loadedPhotos();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<PhotoListResponse>> call, Throwable t) {
                    Log.e(TAG, t.getMessage(), t);
                }
            });
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
}
