package com.edkornev.vkgallery.ui.preview.presenters;

import android.util.Log;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.ui.preview.views.PreviewView;
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
 * Created by kornev on 11/11/16.
 */
public class PreviewPresenter {

    private static final String TAG = PreviewPresenter.class.getSimpleName();

    private PreviewView mView;
    private List<PhotoResponse> mPhotos = new ArrayList<>();
    private long mCount;

    public PreviewPresenter(PreviewView mView) {
        this.mView = mView;
    }

    public void loadPhotos() {
        String accessToken = VKAccessToken.currentToken().accessToken;

        Call<BaseResponse<PhotoListResponse>> request = HttpApi.getInstance().getApi().getPhotos(1, 0L, 1, 1, accessToken, "5.60");
        request.enqueue(new Callback<BaseResponse<PhotoListResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<PhotoListResponse>> call, Response<BaseResponse<PhotoListResponse>> response) {
                if (response.code() == 200) {
                    BaseResponse<PhotoListResponse> body = response.body();
                    mPhotos.addAll(body.getResponse().getItems());
                    mCount = body.getResponse().getCount();

                    mView.loadedPhotos();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PhotoListResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                mView.onShowError(R.string.error_message_api);
            }
        });
    }

    public List<PhotoResponse> getPhotos() {
        return mPhotos;
    }

    public long getCount() {
        return mCount;
    }
}
