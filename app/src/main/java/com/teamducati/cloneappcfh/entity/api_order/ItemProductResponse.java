package com.teamducati.cloneappcfh.entity.api_order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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