package com.edkornev.vkgallery.utils.api.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduard on 10.11.2016.
 */
public class PhotoResponse implements Parcelable {
    private Long id;
    private String photo_604;
    private String photo_1280;
    private String text;
    private Integer real_offset;
    private LikeResponse likes;
    private ShareResponse reposts;

    public PhotoResponse() {
    }

    private PhotoResponse(Parcel parcel) {
        this.id = parcel.readLong();
        this.photo_604 = parcel.readString();
        this.photo_1280 = parcel.readString();
        this.text = parcel.readString();
        this.real_offset = parcel.readInt();
        this.likes = parcel.readParcelable(LikeResponse.class.getClassLoader());
        this.reposts = parcel.readParcelable(ShareResponse.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(photo_604);
        parcel.writeString(photo_1280);
        parcel.writeString(text);
        parcel.writeInt(real_offset);
        parcel.writeParcelable(likes, i);
        parcel.writeParcelable(reposts, i);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto_604() {
        return photo_604;
    }

    public void setPhoto_604(String photo_604) {
        this.photo_604 = photo_604;
    }

    public String getPhoto_1280() {
        return photo_1280;
    }

    public void setPhoto_1280(String photo_1280) {
        this.photo_1280 = photo_1280;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getReal_offset() {
        return real_offset;
    }

    public void setReal_offset(Integer real_offset) {
        this.real_offset = real_offset;
    }

    public LikeResponse getLikes() {
        return likes;
    }

    public void setLikes(LikeResponse likes) {
        this.likes = likes;
    }

    public ShareResponse getReposts() {
        return reposts;
    }

    public void setReposts(ShareResponse reposts) {
        this.reposts = reposts;
    }

    public static final Parcelable.Creator<PhotoResponse> CREATOR = new Parcelable.Creator<PhotoResponse>() {
        public PhotoResponse createFromParcel(Parcel in) {
            return new PhotoResponse(in);
        }

        public PhotoResponse[] newArray(int size) {
            return new PhotoResponse[size];
        }
    };
}
