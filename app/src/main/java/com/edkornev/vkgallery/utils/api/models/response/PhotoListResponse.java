package com.edkornev.vkgallery.utils.api.models.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 10.11.2016.
 */
public class PhotoListResponse {
    private Long count;
    private Integer more;
    private List<PhotoResponse> items = new ArrayList<>();

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getMore() {
        return more;
    }

    public void setMore(Integer more) {
        this.more = more;
    }

    public List<PhotoResponse> getItems() {
        return items;
    }

    public void setItems(List<PhotoResponse> items) {
        this.items = items;
    }
}
