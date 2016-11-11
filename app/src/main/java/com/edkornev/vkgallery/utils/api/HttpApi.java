package com.edkornev.vkgallery.utils.api;

import com.edkornev.vkgallery.utils.api.models.response.BaseResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoListResponse;
import com.edkornev.vkgallery.utils.api.models.response.PhotoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Eduard on 10.11.2016.
 */
public class HttpApi {

    private static final String DOMAIN = "https://api.vk.com";
    private static final String METHODS = DOMAIN + "/method";

    private static HttpApi instance;

    private Api mApi;

    public static HttpApi getInstance() {
        if (instance == null) {
            synchronized (HttpApi.class) {
                if (instance == null) {
                    instance = new HttpApi();
                }
            }
        }

        return instance;
    }

    public Api getApi() {
        if (mApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = retrofit.create(Api.class);
        }

        return mApi;
    }

    public interface Api {
        @GET(METHODS + "/photos.getAll")
        Call<BaseResponse<PhotoListResponse>> getPhotos(@Query("extended") Integer extended,
                                                        @Query("offset") Long offset,
                                                        @Query("no_service_albums") Integer noServiceAlbums,
                                                        @Query("skip_hidden") Integer skipHidden,
                                                        @Query("access_token") String accessToken,
                                                        @Query("v") String v,
                                                        @Query("owner_id") String id);
    }
}
