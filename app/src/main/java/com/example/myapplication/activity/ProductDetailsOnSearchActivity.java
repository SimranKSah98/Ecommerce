package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.PopularProductsAdapter;
import com.example.myapplication.adapter.SearchPopularAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.ProductsItem;
import com.example.myapplication.pojo.SearchResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetailsOnSearchActivity extends AppCompatActivity implements SearchPopularAdapter.OnCardListener {

    private List<ProductsItem> productlist = new ArrayList();
    Call<BaseResponse<List<SearchResponse>>> call;
    List<SearchResponse> arraylist = new ArrayList<SearchResponse>();
    Retrofit retrofit;
    Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SearchPopularAdapter searchPopularAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdetailspage);
        initRecycleView();
        initRetrofitAndCallApi();
        initBottomNavigation();
    }

    private void initRecycleView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Results");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.searchrecycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ProductDetailsOnSearchActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        searchPopularAdapter = new SearchPopularAdapter(arraylist, this);
        recyclerView.setAdapter(searchPopularAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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

    public void initRetrofitAndCallApi() {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        App.getApp().getRetrofit().create(APIInterface.class).getSearchList(getIntent().getStringExtra("QueryText")).enqueue(
                new Callback<BaseResponse<List<SearchResponse>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<SearchResponse>>> call, Response<BaseResponse<List<SearchResponse>>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        arraylist.clear();
                        arraylist.addAll(response.body().getData());
                        searchPopularAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<SearchResponse>>> call, Throwable t) {
                        Toast.makeText(ProductDetailsOnSearchActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );
    }

    @Override
    public void onCardClick(String id) {
        Intent intent = new Intent(ProductDetailsOnSearchActivity.this, ProductDescriptionActivity.class);
        intent.putExtra("productId", id);
        startActivity(intent);
    }
}
