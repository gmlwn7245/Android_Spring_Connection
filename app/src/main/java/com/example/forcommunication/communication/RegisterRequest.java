package com.example.forcommunication.communication;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest{

}

//public class RegisterRequest extends StringRequest {
//    final static private String URL = "http://localhost:8080/User/SignUp";
//    private RequestQueue queue;

//    public RegisterRequest(String userID, String userPassword, String userName, String userEmail, Response.Listener<String> listener) {
//        super(Method.POST, URL, listener, null);
//        JsonObjectRequest req = new JsonObjectRequest(
//                Request.Method.POST, URL, null, new Response.Listener<JSONObject>(){
//                @Override
//                 public void onResponse(JSONObject response) {
//                      //textView.setText("Response: " + response.toString());
//                }
//        }
//        );
//        map = new HashMap<>();
//        map.put("UserId", userID);
//        map.put("Password", userPassword);
//        map.put("Name", userName);
//        map.put("Email", userEmail);
//
//    }
//}
