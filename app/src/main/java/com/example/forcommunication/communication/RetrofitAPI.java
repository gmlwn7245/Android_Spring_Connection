package com.example.forcommunication.communication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;

// Retrofit 사용
public interface RetrofitAPI {
    //회원가입
    @POST("/Main/RetrofitSignUp")
    Call<UserDTO> SignUp(@FieldMap HashMap<String,String> userMap);
    //로그인
    @POST("/Main/RetrofitSignIn")
    Call<UserDTO> SignIn(@FieldMap HashMap<String,String> userMap);

    @GET("/Main")
    Call<ResponseBody> test();
}
