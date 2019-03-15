package com.example.thecoffeehouse.data.model;

import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("api/v2/news")
     Observable <List<ResponseNews>> getNews();
    @GET("api/v2/news_promotion")
    Observable<List<ResponseForYou>> getForYou();
}
