package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MerchantAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.AddToCartRequestBody;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CartResponse;
import com.example.myapplication.pojo.MerchantListItem;
import com.example.myapplication.pojo.ProductDescription;
import com.example.myapplication.pojo.ProductsItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.example.myapplication.activity.adapter.MerchantAdapter;

public class ProductDescriptionActivity extends AppCompatActivity implements MerchantAdapter.OtherMerchantListener {
    private Retrofit retrofit;
    private Call<BaseResponse<ProductDescription>> call;
    private Call<BaseResponse<CartResponse>> callAddToCart;
    private List<ProductDescription> list = new ArrayList();
    private ProgressBar progressBar;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private TextView productName, productPrice, merchantName, attributes, usp, description;
    private ImageView productImage;
    private Button addToCart;
    private Toolbar toolbar;
    private RecyclerView merchantrecyclerView;
    private RecyclerView commentView;
    private MerchantAdapter merchantAdapter;
    private ProductDescription productDescription;
    private ProductsItem productsItem;
    private AddToCartRequestBody addToCartRequestBody = new AddToCartRequestBody();
    private String merchantId, name;
    private List<AddToCartRequestBody> guestCartList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        initView();
        initAddToCart();
        initBottomNavigation();
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

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public void onMerchnatClick(MerchantListItem merchantListItem) {
        productPrice.setText(merchantListItem.getPrice());
        merchantName.setText(merchantListItem.getMerchantName());
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            productImage.setScaleX(mScaleFactor);
            productImage.setScaleY(mScaleFactor);
            return true;
        }
    }

    public void initRetrofitAndCallApi() {
        retrofit = App.getApp().getRetrofit();
        APIInterface api = retrofit.create(APIInterface.class);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        call = api.getProductDescription(getIntent().getStringExtra("productId"));
    }

    public void initView() {
        initRetrofitAndCallApi();
        apiCallback();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.pdp);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void apiCallback() {
        call.enqueue(new Callback<BaseResponse<ProductDescription>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProductDescription>> call, Response<BaseResponse<ProductDescription>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    productDescription = response.body().getData();
                    productName = findViewById(R.id.textView5);
                    productName.setText(productDescription.getName());
                    productPrice = (TextView) findViewById(R.id.textView6);
                    productPrice.setText(String.valueOf(productDescription.getPrice()));
                    merchantName = (TextView) findViewById(R.id.textView7);
                    merchantName.setText(productDescription.getMerchantName());
                    attributes = (TextView) findViewById(R.id.textView8);
                    attributes.setText(String.valueOf(productDescription.getAttributes()));
                    usp = (TextView) findViewById(R.id.textView9);
                    usp.setText(productDescription.getUsp());
                    description = (TextView) findViewById(R.id.textView10);
                    description.setText(productDescription.getDescription());
                    productImage = (ImageView) findViewById(R.id.imageView2);
                    Glide.with(productImage.getContext()).load(productDescription.getImage()).into(productImage);
                    merchantId = productDescription.getMerchantId();
                    name = productDescription.getName();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductDescription>> call, Throwable t) {
                Toast.makeText(ProductDescriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void initAddToCart() {
        addToCart = findViewById(R.id.addToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                final boolean value = sharedPreferences.getBoolean("login_details", false);
                if (value) {
                    addToCartRequestBody.setImage(productDescription.getImage());
                    addToCartRequestBody.setMerchantName(productDescription.getMerchantName());
                    addToCartRequestBody.setName(name);
                    addToCartRequestBody.setPrice(productDescription.getPrice());
                    addToCartRequestBody.setQuantity(1);
                    String productId = getIntent().getStringExtra("productId");
                    addToCartRequestBody.setProductId(productId);
                    addToCartRequestBody.setMerchantId(merchantId);
                    sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
                    String customerId = sharedPreferences.getString("customerEmailId", "");
                    retrofit = App.getApp().getRetrofit();
                    APIInterface api = retrofit.create(APIInterface.class);
                    callAddToCart = api.updateCart(customerId, addToCartRequestBody);
                    progressBar = findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.VISIBLE);
                    callAddToCart.enqueue(new Callback<BaseResponse<CartResponse>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<CartResponse>> call, Response<BaseResponse<CartResponse>> response) {
                            if (response.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ProductDescriptionActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProductDescriptionActivity.this, CartActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<CartResponse>> call, Throwable t) {
                            Toast.makeText(ProductDescriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProductDescriptionActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });
                } else if (!value) {
                    String guestCart = sharedPreferences.getString("guestCart", "");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<AddToCartRequestBody>>() {
                    }.getType();
                    List<AddToCartRequestBody> addToCartRequestBodies = gson.fromJson(guestCart, listType);
                    String productId = getIntent().getStringExtra("productId");
                    if (null != addToCartRequestBodies) {
                        guestCartList.clear();
                        guestCartList.addAll(addToCartRequestBodies);
                        for (AddToCartRequestBody addToCartRequestBody : guestCartList) {
                            if (addToCartRequestBody.getProductId().equals(productId)) {
                                guestCartList.remove(addToCartRequestBody);
                                addToCartRequestBody.setQuantity(addToCartRequestBody.getQuantity() + 1);
                                guestCartList.add(addToCartRequestBody);
                                setList("guestCart", guestCartList);
                                Toast.makeText(ProductDescriptionActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProductDescriptionActivity.this, CartActivity.class);
                                startActivity(intent);
                                return;
                            }
                        }
                    }
                    addToCartRequestBody.setImage(productDescription.getImage());
                    addToCartRequestBody.setMerchantName(productDescription.getMerchantName());
                    addToCartRequestBody.setName(name);
                    addToCartRequestBody.setPrice(productDescription.getPrice());
                    addToCartRequestBody.setQuantity(1);
                    addToCartRequestBody.setProductId(productId);
                    addToCartRequestBody.setMerchantId(merchantId);
                    guestCartList.add(addToCartRequestBody);
                    setList("guestCart", guestCartList);
                    Toast.makeText(ProductDescriptionActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductDescriptionActivity.this, CartActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        });
    }


    public <AddToCartRequestBody> void setList(String key, List<AddToCartRequestBody> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void set(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
