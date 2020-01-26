package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.pojo.AddToCartRequestBody;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CartItem;
import com.example.myapplication.pojo.CartResponse;
import com.example.myapplication.pojo.ProductsBoughtItem;
import com.example.myapplication.pojo.ProductsItem;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.controller.APIInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity implements CartAdapter.Cartpostion {

    private Call<BaseResponse<CartResponse>> call;
    private String address;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private Retrofit retrofit;
    private List<ProductsBoughtItem> cartItems = new ArrayList();
    private TextView productName, productPrice;
    private ElegantNumberButton elegantNumberButton;
    private int initialQuantity, finalQuantity, changeInQuantity;
    private ProgressBar progressBar;
    private ProductsBoughtItem productsBoughtItem;
    SharedPreferences sharedPreferences;
    private AddToCartRequestBody addToCartRequestBody = new AddToCartRequestBody();

    CartResponse cartResponse;
    private Toolbar toolbar;
    Button btn_checkout, btn_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        initBottomNavigation();
        initRecyclerView();
        initRetrofitAndCallApi();
        apiCallback();
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.cart);
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

    private void initRecyclerView() {
        cartAdapter = new CartAdapter(cartItems);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CartActivity.this, 1);
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        btn_checkout = findViewById(R.id.button);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_checkout.setVisibility(v.INVISIBLE);
                btn_buy.setVisibility(v.VISIBLE);
            }
        });
        btn_buy = findViewById(R.id.buttonbuy);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cartRecyclerView.setAdapter(cartAdapter);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.cart);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void initRetrofitAndCallApi() {
        retrofit = App.getApp().getRetrofit();
        APIInterface api = retrofit.create(APIInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean("login_details", false);
        if (value) {
            String customerEmail = sharedPreferences.getString("customerEmailId", null);
            call = api.getCartItems(customerEmail);
        } else if (value == false) {
            String cartItem = sharedPreferences.getString("guestCart", "");
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<AddToCartRequestBody>>() {
            }.getType();
            List<AddToCartRequestBody> addToCartRequestBodies = gson.fromJson(cartItem, listType);
            for (int i = 0; i < addToCartRequestBodies.size(); i++) {
                ProductsBoughtItem productsBoughtItem = new ProductsBoughtItem();
                productsBoughtItem.setImage(addToCartRequestBodies.get(i).getImage());
                productsBoughtItem.setPrice(addToCartRequestBodies.get(i).getPrice());
                productsBoughtItem.setName(addToCartRequestBodies.get(i).getName());
                productsBoughtItem.setQuantity(addToCartRequestBodies.get(i).getQuantity());
                cartItems.add(productsBoughtItem);
            }
            cartAdapter.notifyDataSetChanged();
        }
    }

    public void apiCallback() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        Boolean value = sharedPreferences.getBoolean("login_details", false);
        if (value == true) {
            progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<BaseResponse<CartResponse>>() {
                @Override
                public void onResponse(Call<BaseResponse<CartResponse>> call, Response<BaseResponse<CartResponse>> response) {

                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);

                        cartResponse = response.body().getData();

                        cartItems.addAll(cartResponse.getProductsBought());
                        cartAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<CartResponse>> call, Throwable t) {
                    Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    @Override
    public void onCardclick(ProductsBoughtItem item, final int newquantity, final int position) {
        boolean value = sharedPreferences.getBoolean("login_details", false);
        if (value) {
            addToCartRequestBody.setProductId(item.getProductId());
            addToCartRequestBody.setImage(item.getImage());
            addToCartRequestBody.setName(item.getName());
            addToCartRequestBody.setMerchantId(item.getMerchantId());
            addToCartRequestBody.setMerchantName(item.getMerchantName());
            addToCartRequestBody.setPrice(item.getPrice());
            addToCartRequestBody.setQuantity(newquantity);
            App.getApp().getRetrofit().create(APIInterface.class).updateCart(sharedPreferences.getString("customerEmailId", ""), addToCartRequestBody).enqueue(
                    new Callback<BaseResponse<AddToCartRequestBody>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<AddToCartRequestBody>> call, Response<BaseResponse<AddToCartRequestBody>> response) {
                            Toast.makeText(CartActivity.this, "Updated Cart Item", Toast.LENGTH_SHORT).show();
                            if (newquantity == 0) {
                                cartItems.remove(position);
                            }
                            cartAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<AddToCartRequestBody>> call, Throwable t) {
                            Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        } else if (value == false) {
            String cartItem = sharedPreferences.getString("guestCart", "");
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<AddToCartRequestBody>>() {
            }.getType();
            List<AddToCartRequestBody> addToCartRequestBodies = gson.fromJson(cartItem, listType);
            if (newquantity == 0) {
                addToCartRequestBodies.remove(position);
            } else {
                addToCartRequestBodies.get(position).setQuantity(newquantity);
            }
            cartAdapter.notifyDataSetChanged();
        }
    }
}
