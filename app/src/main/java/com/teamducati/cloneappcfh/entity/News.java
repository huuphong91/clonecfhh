package com.teamducati.cloneappcfh.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

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
    private String content;
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
    private String button;
    @SerializedName("deeplink")
    @Expose
    private String deeplink;
    @SerializedName("effects")
    @Expose
    private Object effects;
    @SerializedName("shareUrl")
    @Expose
    private String shareUrl;
}

