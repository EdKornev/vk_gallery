package com.edkornev.vkgallery.utils.api.models.response;

/**
 * Created by Eduard on 10.11.2016.
 */
public class PhotoResponse {
    private Long id;
    private String photo_604;
    private String photo_1280;
    private String text;
    private Integer real_offset;
    private LikeModel likes;
    private ShareModel reposts;

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

    public LikeModel getLikes() {
        return likes;
    }

    public void setLikes(LikeModel likes) {
        this.likes = likes;
    }

    public ShareModel getReposts() {
        return reposts;
    }

    public void setReposts(ShareModel reposts) {
        this.reposts = reposts;
    }

    private class LikeModel {
        private Integer user_likes;
        private Long count;

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
    }

    private class ShareModel {
        private Long count;

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }
}
