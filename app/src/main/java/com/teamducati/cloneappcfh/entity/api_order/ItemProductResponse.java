package com.teamducati.cloneappcfh.entity.api_order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemProductResponse implements Serializable {

    @SerializedName("data")
    public List<DataItem> data;

    @SerializedName("suggestion")
    public List<SuggestionItem> suggestion;

    @SerializedName("message")
    private String message;

    @SerializedName("key")
    private String key;

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
}