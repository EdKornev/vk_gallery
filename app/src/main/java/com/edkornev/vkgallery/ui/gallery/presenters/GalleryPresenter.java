package com.edkornev.vkgallery.ui.gallery.presenters;

import android.util.Log;

import com.edkornev.vkgallery.ui.gallery.views.GalleryView;
import com.edkornev.vkgallery.utils.api.HttpApi;
import com.edkornev.vkgallery.utils.api.models.response.BaseResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoListResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;
import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eduard on 10.11.2016.
 */
public class GalleryPresenter {

    private static final String TAG = GalleryPresenter.class.getSimpleName();

    private GalleryView mView;
    private String mAccessToken;
    private List<PhotoResponse> mPhotos = new ArrayList<>();

    public GalleryPresenter(GalleryView view) {
        this.mView = view;
        this.mAccessToken = VKAccessToken.currentToken().accessToken;
    }

    public void loadPhotos() {
        Call<BaseResponse<PhotoListResponse>> request = HttpApi.getInstance().getApi().getPhotos(1, 0L, 1, 1, mAccessToken, "5.60");
        request.enqueue(new Callback<BaseResponse<PhotoListResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<PhotoListResponse>> call, Response<BaseResponse<PhotoListResponse>> response) {
                if (response.code() == 200) {
                    mPhotos.addAll(response.body().getResponse().getItems());

                    mView.loadedPhotos();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PhotoListResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public List<PhotoResponse> getPhotos() {
        return mPhotos;
    }
}
