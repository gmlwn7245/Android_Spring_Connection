package com.example.forcommunication.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.forcommunication.R;
import com.example.forcommunication.communication.SpringConnection;
import com.example.forcommunication.communication.UserDTO;
import com.example.forcommunication.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private RequestQueue queue;
    //private CallRetrofit call;
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
        final Button CheckIdButton = binding.checkId;
        final Button ChangePWButton = binding.ChangePW;

//        final Button RetrofitSignInButton = binding.RetrofitSignIn;
//        final Button RetrofitSignUpButton = binding.RetrofitSignUp;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {

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
                        SpringConnection sc = new SpringConnection();
                        UserDTO userDTO = new UserDTO(UserId,Name,Email,Password);
                        String Message = sc.HttpConnPOSTUser("Main/SignUp", userDTO) + "님 회원가입 완료.";
                        System.out.println(Message);
                    }
                });
                th.start();
            }
        });


        SignInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("--SignIn--");
                String UserId = useridEditText.getText().toString();
                String Name = usernameEditText.getText().toString();
                String Email = emailEditText.getText().toString();
                String Password = passwordEditText.getText().toString();

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                         SpringConnection sc = new SpringConnection();
                         UserDTO userDTO = new UserDTO(UserId,Name,Email,Password);
                         String Message = sc.HttpConnPOSTUser("Main/SignIn", userDTO) + "님 로그인 완료.";
                         System.out.println(Message);
                    }
                });
                th.start();
                System.out.println(UserId + ':' + Name + ':' + Email + ':' + Password);

            }
        });

        ChangePWButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("--ChangePW--");
                String UserId = useridEditText.getText().toString();
                String Password = passwordEditText.getText().toString();

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringConnection sc = new SpringConnection();
                        UserDTO userDTO = new UserDTO(UserId,"","",Password);
                        String Message = sc.HttpConnPOSTUser("Main/ChangePW", userDTO);
                        System.out.println(Message);
                    }
                });
                th.start();

            }
        });

        CheckIdButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("--CheckId--");
                String UserId = useridEditText.getText().toString();

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringConnection sc = new SpringConnection();
                        String query = "?userId="+UserId;
                        String Message = sc.HttpConnGETUser("Main/CheckId"+query);
                        System.out.println(Message);
                    }
                });
                th.start();
            }
        });



/*
                // 레트로핏
                RetrofitSignInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("--RetrofitSignIn--");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        String UserId = useridEditText.getText().toString();
                        String Password = passwordEditText.getText().toString();

//                        UserDTO userDTO = new UserDTO();
//                        userDTO.setUserId(UserId);
//                        userDTO.setPassword(Password);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("UserId", UserId);
                        map.put("Password",Password);

                        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                        retrofitAPI.SignIn(map).enqueue(new Callback<UserDTO>() {
                            @Override
                            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                System.out.println(response.body().getName());
                            }
                            @Override
                            public void onFailure(Call<UserDTO> call, Throwable t) {
                                System.out.println("fail");
                            }
                        });
                    }
                });

                RetrofitSignUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("--RetrofitSignUp--");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        String UserId = useridEditText.getText().toString();
                        String Name = usernameEditText.getText().toString();
                        String Email = emailEditText.getText().toString();
                        String Password = passwordEditText.getText().toString();

                        HashMap<String, String> userMap = new HashMap<String, String>();
                        userMap.put("UserId", UserId);
                        userMap.put("Password",Password);
                        userMap.put("Email",Email);
                        userMap.put("Name",Name);

                        // 인터페이스와 객체 연결
                        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

                        // 응답 요청
                        Call<UserDTO> call = retrofitAPI.SignUp(userMap);

                        // 응답 콜백 구현
                        call.enqueue(new Callback<UserDTO>() {
                            @Override
                            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                               System.out.println(response.body().getName());
                            }

                            @Override
                            public void onFailure(Call<UserDTO> call, Throwable t) {
                                System.out.println("fail");
                            }
                        });
                    }
                });
*/

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