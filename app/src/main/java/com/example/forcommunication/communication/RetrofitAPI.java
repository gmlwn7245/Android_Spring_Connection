package com.example.forcommunication.communication;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/User/SignUp")
    Call<ResponseBody> registerUser(JSONObject jsonObject);

    @GET("/Main")
    Call<ResponseBody> test();
}
