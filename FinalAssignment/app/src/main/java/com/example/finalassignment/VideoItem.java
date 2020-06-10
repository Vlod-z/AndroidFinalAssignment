package com.example.finalassignment;

public class VideoItem {
    private String description;
    private String nickname;
    private String avatar;
    private String feedurl;

    public VideoItem(String description,String nickname,String avatar,String feedurl) {
        this.description = description;
        this.nickname = nickname;
        this.avatar = avatar;
        this.feedurl = feedurl;
    }

    public String getDescription() {
        return description;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFeedurl() {
        return feedurl;
    }
}
