package com.edkornev.vkgallery.utils.api.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kornev on 11/11/16.
 */
public class LikeResponse implements Parcelable {
    private Integer user_likes;
    private Long count;

    public LikeResponse() {
    }

    private LikeResponse(Parcel parcel) {
        this.user_likes = parcel.readInt();
        this.count = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(user_likes);
        parcel.writeLong(count);
    }

    public Integer getUser_likes() {
        return user_likes;
    }

    public void setUser_likes(Integer user_likes) {
        this.user_likes = user_likes;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static final Parcelable.Creator<LikeResponse> CREATOR = new Parcelable.Creator<LikeResponse>() {
        public LikeResponse createFromParcel(Parcel in) {
            return new LikeResponse(in);
        }

        public LikeResponse[] newArray(int size) {
            return new LikeResponse[size];
        }
    };
}
