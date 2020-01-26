package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CategoriesItem;
import com.example.myapplication.pojo.Home;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.OnClickListener {
    private List<CategoriesItem> categoryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdaptor;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initRecyclerView();
        initBottomNavigation();
        callHomeApi();
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.categoryLayoutRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryAdaptor = new CategoryAdapter(categoryList, this);
        recyclerView.setAdapter(categoryAdaptor);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.category);
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
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dashboard:
                        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
                        boolean value = sharedPreferences.getBoolean("login_details", false);
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
                        return true;
                }
                return false;
            }
        });
    }

    public void callHomeApi() {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        App.getApp().getRetrofit().create(APIInterface.class).getProducts().enqueue(
                new Callback<BaseResponse<Home>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Home>> call, Response<BaseResponse<Home>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        categoryList.clear();
                        categoryList.addAll(response.body().getData().getCategories());
                        categoryAdaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Home>> call, Throwable t) {
                        Toast.makeText(CategoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public void onClick(String categoryId, String categoryName) {
        Intent intent = new Intent(CategoryActivity.this, CategoryProductsActivity.class);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("categoryName", categoryName);
        startActivity(intent);
    }
}