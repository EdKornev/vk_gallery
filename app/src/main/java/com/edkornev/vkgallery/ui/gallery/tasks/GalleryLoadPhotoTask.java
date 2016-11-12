package com.edkornev.vkgallery.ui.gallery.tasks;

import android.util.Log;

import com.edkornev.vkgallery.R;
import com.edkornev.vkgallery.ui.base.tasks.BaseTask;
import com.edkornev.vkgallery.utils.api.HttpApiService;
import com.edkornev.vkgallery.utils.api.models.response.BaseResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoListResponse;
import com.vk.sdk.VKAccessToken;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by kornev on 12/11/16.
 */
public class GalleryLoadPhotoTask extends BaseTask<Long, Void, PhotoListResponse> {

    private static final String TAG = GalleryLoadPhotoTask.class.getSimpleName();

    private static final int REQUEST_FIELD_EXTENDED = 1;
    private static final int REQUEST_FIELD_NO_SERVICE_ALBUMS = 1;
    private static final int REQUEST_FIELD_SKIP_HIDDEN = 1;

    public GalleryLoadPhotoTask(TaskListener<PhotoListResponse> taskListener) {
        super(taskListener);
    }

    @Override
    protected PhotoListResponse doInBackground(Long... longs) {
        String accessToken = VKAccessToken.currentToken().accessToken;
        long offset = longs[0];

        try {
            Call<BaseResponse<PhotoListResponse>> request = HttpApiService.getInstance().getApi().getPhotos(REQUEST_FIELD_EXTENDED, offset, REQUEST_FIELD_NO_SERVICE_ALBUMS, REQUEST_FIELD_SKIP_HIDDEN, accessToken, HttpApiService.API_VERSION, "52138567");
            Response<BaseResponse<PhotoListResponse>> response = request.execute();

            if (response.code() == 200) {
                return response.body().getResponse();
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(PhotoListResponse response) {
        if (response == null) {
            mTaskListener.onError(R.string.error_message_api);
        } else {
            mTaskListener.onSuccess(response);
        }
    }
}
