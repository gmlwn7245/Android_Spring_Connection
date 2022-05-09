package com.example.forcommunication.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.forcommunication.R;
import com.example.forcommunication.communication.RetrofitAPI;
import com.example.forcommunication.communication.UserDTO;
import com.example.forcommunication.ui.login.LoginViewModel;
import com.example.forcommunication.ui.login.LoginViewModelFactory;
import com.example.forcommunication.databinding.ActivityLoginBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private RequestQueue queue;
    //private CallRetrofit call;
    String url = "http://10.0.2.2:8080/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final EditText emailEditText = binding.email;
        final EditText useridEditText = binding.userId;
        final Button SignInButton = binding.SignIn;
        final Button SignUpButton = binding.SignUp;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                SignInButton.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                }
//                if (loginFormState.getUsernameError() != null) {
//                    useridEditText.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    emailEditText.setError(getString(loginFormState.getPasswordError()));
//                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                //loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                //        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = useridEditText.getText().toString();
                String Name = usernameEditText.getText().toString();
                String Email = emailEditText.getText().toString();
                String Password = passwordEditText.getText().toString();

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String page = url+"Main/SignUp";
                            URL urls = new URL(page);
                            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

                            StringBuilder sb = new StringBuilder();
                            if(conn!=null){
                                conn.setConnectTimeout(10000);
                                conn.setRequestMethod("POST");
                                conn.setRequestProperty("Content-Type", "application/json");
                                conn.setUseCaches(false);
                                conn.setDoOutput(true);
                                conn.setDoInput(true);

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("userId",UserId);
                                jsonObject.put("name",Name);
                                jsonObject.put("email",Email);
                                jsonObject.put("password",Password);

                                OutputStream os = conn.getOutputStream();
                                os.write(jsonObject.toString().getBytes());
                                os.flush();

                                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                                    InputStream is = conn.getInputStream();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    byte[] byteBuffer = new byte[1024];
                                    int nLength;
                                    while((nLength = is.read(byteBuffer, 0, byteBuffer.length))!= -1){
                                        baos.write(byteBuffer,0,nLength);
                                    }
                                    byte[] byteData = baos.toByteArray();

                                    JSONObject responseJSON = new JSONObject(new String(byteData));
                                    System.out.println(responseJSON.get("Name"));
                                }
                                conn.disconnect();
                            }
                            System.out.println(sb.toString());

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                th.start();

            }
        });


        SignInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String UserId = useridEditText.getText().toString();
                String Name = usernameEditText.getText().toString();
                String Email = emailEditText.getText().toString();
                String Password = passwordEditText.getText().toString();

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String page = url+"Main/SignIn";
                            URL urls = new URL(page);
                            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
                            StringBuilder sb = new StringBuilder();
                            if(conn!=null){
                                conn.setConnectTimeout(10000);
                                conn.setRequestMethod("POST");
                                conn.setRequestProperty("Content-Type", "application/json");
                                conn.setUseCaches(false);
                                conn.setDoOutput(true);
                                conn.setDoInput(true);

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("userId",UserId);
                                jsonObject.put("password",Password);

                                OutputStream os = conn.getOutputStream();
                                os.write(jsonObject.toString().getBytes());
                                os.flush();

                                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                                    InputStream is = conn.getInputStream();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    byte[] byteBuffer = new byte[1024];
                                    int nLength;
                                    while((nLength = is.read(byteBuffer, 0, byteBuffer.length))!= -1){
                                        baos.write(byteBuffer,0,nLength);
                                    }
                                    byte[] byteData = baos.toByteArray();

                                    JSONObject responseJSON = new JSONObject(new String(byteData));
                                    System.out.println(responseJSON.get("Name"));
                                }
                                conn.disconnect();
                            }
                            System.out.println(sb.toString());

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                th.start();

 /*               try {
//                    URL urlCon = new URL(url);
//                    HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
//                    httpCon.setRequestMethod("POST"); //전송방식
//                    httpCon.setDoOutput(true); //데이터를 쓸 지 설정
//                    httpCon.setDoInput(true); //데이터를 읽어올지 설정
//                    httpCon.setRequestProperty("Content-Type","application/json");
//                    httpCon.setRequestProperty("Accept","application/json");
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UserId", "tTest0509");
                    jsonObject.put("Name", "tTest0509Name");
                    jsonObject.put("Email","tTest0509@naver.com");
                    jsonObject.put("Password","tTest0509PW");

                    UserDTO userDTO = new UserDTO();
                    userDTO.setUserId(UserId);
                    userDTO.setEmail(Email);
                    userDTO.setName(Name);
                    userDTO.setPassword(Password);

                    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                    System.out.println("try");

                    //RequestBody requestBody = RequestBody.create(JSONObject,jsonObject);
                    retrofitAPI.registerUser(jsonObject).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            System.out.println("success");
                            System.out.println(response);
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.out.println("fail");
                        }
                    });


                }catch (JSONException e){
                    e.printStackTrace();
                }catch (Exception e){

                }*/



                queue = Volley.newRequestQueue(getApplicationContext());

//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                textView.setText("Response: " + response.toString());
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                               //  TODO: Handle error
//
//                            }
//                        });





                //JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create()

                System.out.println(UserId + ':' + Name + ':' + Email + ':' + Password);

            }
        });



    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}