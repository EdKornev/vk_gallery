package com.edkornev.vkgallery.utils.api.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kornev on 11/11/16.
 */
public class ShareResponse implements Parcelable {
    private Long count;

    public ShareResponse() {
    }

    private ShareResponse(Parcel parcel) {
        this.count = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(count);
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static final Parcelable.Creator<ShareResponse> CREATOR = new Parcelable.Creator<ShareResponse>() {
        public ShareResponse createFromParcel(Parcel in) {
            return new ShareResponse(in);
        }

        public ShareResponse[] newArray(int size) {
            return new ShareResponse[size];
        }
    };
}
