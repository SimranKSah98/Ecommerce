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
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    //private Call<BaseResponse<Integer>> callCartCount;
    private TextView textView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerView categoryRecyclerView;
    private CategoryItemAdapter categoryItemAdapter;
    private LinearLayoutManager linearLayoutManager;
    private PopularProductsAdapter popularProductsAdapter;
    private Home home;
    private ProgressBar progressBar;
    //private int cartCount;
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
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(HomeActivity.this, linearLayoutManager.getOrientation());
        categoryRecyclerView.addItemDecoration(dividerItemDecoration);
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
                        return true;

                    case R.id.cart:
                        sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
                        boolean value1 = sharedPreferences.getBoolean("login_details", false);
                        if (value1) {
                            //if (cartCount > 0) {
                            startActivity(new Intent(getApplicationContext(), CartActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                            /*} else if (cartCount == 0) {
                                Toast.makeText(HomeActivity.this, "No products in cart", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                overridePendingTransition(0, 0);
                                return true;
                            }*/


                        } else if (value1 == false) {
                            String cartEmptyCheck = sharedPreferences.getString("guestCart", "");
                            if (null == cartEmptyCheck || cartEmptyCheck.isEmpty()) {
                                Toast.makeText(HomeActivity.this, "No products in cart", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                overridePendingTransition(0, 0);
                                return true;
                            } else {
                                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                                overridePendingTransition(0, 0);
                                return true;
                            }
                        }

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
        retrofit = App.getApp().getRetrofit();
        APIInterface api = retrofit.create(APIInterface.class);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        call = api.getProducts();
        //SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        //callCartCount=api.getCartCount()
    }

    private void apiCallback() {
        call.enqueue(new Callback<BaseResponse<Home>>() {
            @Override
            public void onResponse(Call<BaseResponse<Home>> call, Response<BaseResponse<Home>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    home = response.body().getData();
                    categoriesList.clear();
                    list.clear();
                    //  cartCount = home.getCartCount();
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
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onCardClick(String id,String name) {
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        intent.putExtra("productId", id);
        intent.putExtra("name",name);

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
    public boolean onQueryTextSubmit(final String query) {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        App.getApp().getRetrofit().create(APIInterface.class).getSearchList(query).enqueue(
                new Callback<BaseResponse<List<SearchResponse>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<SearchResponse>>> call, Response<BaseResponse<List<SearchResponse>>> response) {
                        if (!response.body().getData().isEmpty()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            arraylist.clear();
                            arraylist.addAll(response.body().getData());
                            adapter = new ListViewAdapter(HomeActivity.this, arraylist);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(HomeActivity.this, ProductDetailsOnSearchActivity.class);
                                    intent.putExtra("QueryText", query);
                                    startActivity(intent);

                                }
                            });
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            arraylist.clear();
                            SearchResponse searchResponse = new SearchResponse();
                            searchResponse.setProductName("No Search result");
                            arraylist.add(searchResponse);
                            adapter = new ListViewAdapter(HomeActivity.this, arraylist);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<SearchResponse>>> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listView.clearChoices();
        listView.setAdapter(null);
        return false;
    }

}
