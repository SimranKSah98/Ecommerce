package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.LoginHistoryAdapter;
import com.example.myapplication.adapter.OrderHistoryAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.LoginHistory;
import com.example.myapplication.pojo.OrderHistory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHistoryActivity extends AppCompatActivity {
    List<LoginHistory> loginHistoryList = new ArrayList<>();
    LoginHistoryAdapter loginHistoryAdapter;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginhistory);
        initToolbar();
        initRecyclerView();
        initRetrofit();
        initBottomNavigation();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.login_history);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(LoginHistoryActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        loginHistoryAdapter = new LoginHistoryAdapter(loginHistoryList);
        recyclerView.setAdapter(loginHistoryAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Login History");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView=findViewById(R.id.textView3);
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
                        Boolean value = sharedPreferences.getBoolean("login_details", false);
                        if (!value) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        } else if (value) {
                            startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }


    private void initRetrofit() {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
         App.getApp().getRetrofit().create(APIInterface.class).getloginhistory(getIntent().getStringExtra("customeremailid")).enqueue(
                new Callback<BaseResponse<List<LoginHistory>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<LoginHistory>>> call, Response<BaseResponse<List<LoginHistory>>> response) {
                        textView.setText(getIntent().getStringExtra("customeremailid"));
                        progressBar.setVisibility(View.INVISIBLE);
                        loginHistoryList.clear();
                        loginHistoryList.addAll(response.body().getData());
                        loginHistoryAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<LoginHistory>>> call, Throwable t) {
                        Toast.makeText(LoginHistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }
        );
    }
}
