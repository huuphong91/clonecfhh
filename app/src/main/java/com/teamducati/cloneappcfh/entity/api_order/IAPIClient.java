package com.teamducati.cloneappcfh.entity.api_order;

import com.teamducati.cloneappcfh.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IAPIClient {
    @GET("/api/unknown")
    Call<DataItem> doGetListResources();

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<List<User>> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<List<User>> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
