package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ListViewAdapter;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CategoriesItem;
import com.example.myapplication.pojo.Home;
import com.example.myapplication.pojo.ProductsItem;
import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.adapter.PopularProductsAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.SearchResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements PopularProductsAdapter.OnCardListener, CategoryItemAdapter.OnCategoryNameListener, SearchView.OnQueryTextListener {

    private Retrofit retrofit;
    private Call<BaseResponse<Home>> call;
    private List<ProductsItem> list = new ArrayList();
    private List<CategoriesItem> categoriesList = new ArrayList();

    private TextView textView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerView categoryRecyclerView;
    private CategoryItemAdapter categoryItemAdapter;
    private PopularProductsAdapter popularProductsAdapter;
    private Home home;


    ListView listView;
    ListViewAdapter adapter;
    SearchView searchView;
    List<SearchResponse> arraylist = new ArrayList<SearchResponse>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);

        if (!sharedPreferences.contains("login_details")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("login_details", false);
            editor.commit();
        }

        initRecyclerView();
        initBottomNavigation();
        initRetrofitAndCallApi();
        apiCallback();

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(HomeActivity.this);
    }


    private void initRecyclerView() {
        categoryRecyclerView = findViewById(R.id.recycler_view_categories);
        categoryItemAdapter = new CategoryItemAdapter(categoriesList, this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryItemAdapter);
        popularProductsAdapter = new PopularProductsAdapter(list, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HomeActivity.this, 2);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(popularProductsAdapter);

        listView = (ListView) findViewById(R.id.listview);
        listView.bringToFront();

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
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void initRetrofitAndCallApi() {
        retrofit = App.getApp().getRetrofit();
        APIInterface api = retrofit.create(APIInterface.class);
        call = api.getProducts();
    }

    private void apiCallback() {

        call.enqueue(new Callback<BaseResponse<Home>>() {
            @Override
            public void onResponse(Call<BaseResponse<Home>> call, Response<BaseResponse<Home>> response) {
                if (response.isSuccessful()) {
                    home = response.body().getData();
                    categoriesList.clear();
                    list.clear();
                    categoriesList.addAll(home.getCategories());
                    for (int i = 0; i < home.getCategories().size(); i++) {
                        list.addAll(home.getCategories().get(i).getProducts());
                    }
                    categoryItemAdapter.notifyDataSetChanged();
                    popularProductsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Home>> call, Throwable t) {
                Log.e("Check", t.getMessage());
            }
        });





    }

    @Override
    public void onCardClick(String id) {
     //   Toast.makeText(HomeActivity.this, id, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        intent.putExtra("productId", id);
        startActivity(intent);
    }


    @Override
    public void onTextClick(String categoryId) {
        list.clear();
        for (int i = 0; i < home.getCategories().size(); i++) {
            if (home.getCategories().get(i).getId() == categoryId)
                list.addAll(home.getCategories().get(i).getProducts());
        }
        popularProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        App.getApp().getRetrofit().create(APIInterface.class).getSearchList(query).enqueue(
                new Callback<BaseResponse<List<SearchResponse>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<SearchResponse>>> call, Response<BaseResponse<List<SearchResponse>>> response) {

                        arraylist.clear();
                        arraylist = response.body().getData();
                        adapter = new ListViewAdapter(HomeActivity.this, arraylist);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(HomeActivity.this, ProductDetailsOnSearchActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<SearchResponse>>> call, Throwable t) {
                        Log.e("Check", t.getMessage());
                    }

                });
        arraylist.clear();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listView.clearChoices();
        listView.setAdapter(null);
        return false;
    }

}
