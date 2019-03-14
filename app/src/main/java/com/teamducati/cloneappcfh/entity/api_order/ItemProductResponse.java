package com.teamducati.cloneappcfh.entity.api_order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemProductResponse implements Parcelable {

    @SerializedName("data")
    public List<DataItem> data;

    @SerializedName("suggestion")
    public List<SuggestionItem> suggestion;

    @SerializedName("message")
    private String message;

    @SerializedName("key")
    private String key;

    protected ItemProductResponse(Parcel in) {
        message = in.readString();
        key = in.readString();
    }

    public static final Creator<ItemProductResponse> CREATOR = new Creator<ItemProductResponse>() {
        @Override
        public ItemProductResponse createFromParcel(Parcel in) {
            return new ItemProductResponse(in);
        }

        @Override
        public ItemProductResponse[] newArray(int size) {
            return new ItemProductResponse[size];
        }
    };

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setSuggestion(List<SuggestionItem> suggestion) {
        this.suggestion = suggestion;
    }

    public List<SuggestionItem> getSuggestion() {
        return suggestion;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return
                "ItemProductResponse{" +
                        "data = '" + data + '\'' +
                        ",suggestion = '" + suggestion + '\'' +
                        ",message = '" + message + '\'' +
                        ",key = '" + key + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(key);
    }
}