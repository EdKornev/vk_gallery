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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kornev on 11/11/16.
 */
public class PreviewPresenter {

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
            String accessToken = VKAccessToken.currentToken().accessToken;

            Call<BaseResponse<PhotoListResponse>> request = HttpApi.getInstance().getApi().getPhotos(1, (long) mPhotos.size(), 1, 1, accessToken, "5.60", "52138567");
            request.enqueue(new Callback<BaseResponse<PhotoListResponse>>() {
                @Override
                public void onResponse(Call<BaseResponse<PhotoListResponse>> call, Response<BaseResponse<PhotoListResponse>> response) {
                    if (response.code() == 200) {
                        BaseResponse<PhotoListResponse> body = response.body();
                        mPhotos.addAll(body.getResponse().getItems());
                        mCount = body.getResponse().getCount();
                        mIsMore = body.getResponse().getMore();

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
}
