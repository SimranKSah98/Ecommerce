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

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.CategoryProductAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CategoriesItem;
import com.example.myapplication.pojo.ProductDescription;
import com.example.myapplication.pojo.ProductsItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductsActivity extends AppCompatActivity implements CategoryProductAdapter.OnCardListener {
    private List<ProductsItem> productsItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryProductAdapter categoryAdaptor;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoryproducts);
        onCategoryClick();
        initRecyclerView();
        initBottomNavigation();
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.categoryLayoutRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryAdaptor = new CategoryProductAdapter(productsItemList, this);
        recyclerView.setAdapter(categoryAdaptor);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("categoryName"));
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
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }


    private void onCategoryClick() {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        App.getApp().getRetrofit().create(APIInterface.class).getRespectiveCategoryProducts(getIntent().getStringExtra("categoryId")).enqueue(
                new Callback<BaseResponse<CategoriesItem>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<CategoriesItem>> call, Response<BaseResponse<CategoriesItem>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        productsItemList.clear();
                        productsItemList.addAll(response.body().getData().getProducts());
                        categoryAdaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<CategoriesItem>> call, Throwable t) {
                        Toast.makeText(CategoryProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );
    }

    @Override
    public void onCardClick(String id) {
        Intent intent = new Intent(CategoryProductsActivity.this, ProductDescriptionActivity.class);
        intent.putExtra("productId", id);
        startActivity(intent);
    }
}
