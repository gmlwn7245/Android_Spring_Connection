package com.example.forcommunication.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.forcommunication.R;
import com.example.forcommunication.communication.BillDTO;
import com.example.forcommunication.communication.SpringConnection;
import com.example.forcommunication.communication.UserDTO;
import com.example.forcommunication.databinding.ActivityBillBinding;
import com.example.forcommunication.databinding.ActivityLoginBinding;

import org.json.JSONObject;

public class BillActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityBillBinding binding;
    private RequestQueue queue;

    //private CallRetrofit call;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);


        final Button RegisterBillButton = binding.RegisterBill;
        final Button UpdateBillButton = binding.UpdateBill;
        final Button GetBillButton = binding.GetBill;
        final Button GetChartButton = binding.GetChartBill;


        RegisterBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = "user001";
                String Date = "2023-6";
                int TotalFee = 10000;
                int WaterFee = 3000;
                int WaterUsage = 2000;
                int ElectricityFee = 7000;
                int ElectricityUsage = 5000;

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringConnection sc = new SpringConnection();
                        BillDTO billDTO = new BillDTO(UserId, Date, TotalFee, WaterFee, WaterUsage, ElectricityFee, ElectricityUsage);
                        String result = sc.HttpConnPOSTBill("Bill/InsertBill", billDTO);
                        System.out.println(result);
                    }
                });
                th.start();
            }
        });

        UpdateBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = "user001";
                String Date = "2023-6";
                int TotalFee = 10200;
                int WaterFee = 3033;
                int WaterUsage = 2000;
                int ElectricityFee = 7000;
                int ElectricityUsage = 5000;

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringConnection sc = new SpringConnection();
                        BillDTO billDTO = new BillDTO(UserId, Date, TotalFee, WaterFee, WaterUsage, ElectricityFee, ElectricityUsage);
                        String result = sc.HttpConnPOSTBill("Bill/UpdateBill", billDTO);
                        System.out.println(result);
                    }
                });
                th.start();
            }
        });

        GetBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = "user001";
                String Date = "2023-6";

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringConnection sc = new SpringConnection();
                        String query = "?userId="+UserId+"&"+"date="+Date;
                        String result = sc.HttpConnGETBill("Bill/GetBill"+query);
                        System.out.println(result);
                    }
                });
                th.start();
            }
        });


        GetChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = "user001";

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringConnection sc = new SpringConnection();
                        String query = "?userId="+UserId;
                        String result = sc.HttpConnGETBill("Bill/GetBillList"+query);
                        System.out.println(result);
                    }
                });
                th.start();
            }
        });
    }
}
