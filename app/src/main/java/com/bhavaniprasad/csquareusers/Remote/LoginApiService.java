package com.bhavaniprasad.csquareusers.Remote;

import com.bhavaniprasad.csquareusers.Model.LoginRequest;
import com.bhavaniprasad.csquareusers.Model.UsersList;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApiService {
    @POST("/api/login")
    @FormUrlEncoded
    Call<LoginRequest> login(@Field("email") String email,@Field("password") String Password);

    @GET("/api/users")
    Call<JsonObject> totalcount();

    @GET("/api/users")
    Call<UsersList> getUsersList(@Query("page") int page);

}

