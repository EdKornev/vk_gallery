package com.edkornev.vkgallery.utils.api.models.response;

/**
 * Created by Eduard on 10.11.2016.
 */
public class BaseResponse<T> {

    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
