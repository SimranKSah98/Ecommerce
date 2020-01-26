package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.OrderHistoryAdapter;
import com.example.myapplication.adapter.SearchPopularAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.OrderHistory;
import com.example.myapplication.pojo.SearchResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {
    List<OrderHistory> orderHistoryList = new ArrayList<>();
    OrderHistoryAdapter orderHistoryAdapter;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);
        initToolbar();
        initRecyclerView();
        initRetrofit();
        initBottomNavigation();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.orderhistory);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(OrderHistoryActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        orderHistoryAdapter = new OrderHistoryAdapter(orderHistoryList);
        recyclerView.setAdapter(orderHistoryAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order History");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        App.getApp().getRetrofit().create(APIInterface.class).getorderhistory(1, getIntent().getStringExtra("customeremailid")).enqueue(
                new Callback<BaseResponse<List<OrderHistory>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<OrderHistory>>> call, Response<BaseResponse<List<OrderHistory>>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        orderHistoryList.clear();
                        orderHistoryList.addAll(response.body().getData());
                        orderHistoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<OrderHistory>>> call, Throwable t) {
                        Toast.makeText(OrderHistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );
    }
}
