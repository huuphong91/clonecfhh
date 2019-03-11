package com.teamducati.cloneappcfh.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsPromotion {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("content")
    @Expose
    private Object content;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("action")
    @Expose
    private Object action;
    @SerializedName("key")
    @Expose
    private Object key;
    @SerializedName("button")
    @Expose
    private Object button;
    @SerializedName("deeplink")
    @Expose
    private Object deeplink;
    @SerializedName("effects")
    @Expose
    private Object effects;
    @SerializedName("shareUrl")
    @Expose
    private String shareUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getButton() {
        return button;
    }

    public void setButton(Object button) {
        this.button = button;
    }

    public Object getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(Object deeplink) {
        this.deeplink = deeplink;
    }

    public Object getEffects() {
        return effects;
    }

    public void setEffects(Object effects) {
        this.effects = effects;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }


}
