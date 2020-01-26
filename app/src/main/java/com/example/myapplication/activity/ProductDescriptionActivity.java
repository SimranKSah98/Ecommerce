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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.AddToCartResponseBody;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.ProductDescription;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.example.myapplication.activity.adapter.MerchantAdapter;

public class ProductDescriptionActivity extends AppCompatActivity {
    //private  Button addToCart;

    private Retrofit retrofit;
    private Call<BaseResponse<ProductDescription>> call;
    private List<ProductDescription> list = new ArrayList();

    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    private TextView productName, productPrice, merchantName, attributes, usp, description;
    private ImageView productImage;
    private Button addToCart;
    private Toolbar toolbar;

    private RecyclerView merchantrecyclerView;
    private RecyclerView commentView;


    //   private MerchantAdapter merchantAdapter;
    private ProductDescription productDescription;
    private AddToCartResponseBody addToCartResponseBody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        // addToCart=new Button(this);
        //  addToCart.setOnClickListener(new CartButtonClick());

        initView();
        initBottomNavigation();

    }


    private void CartButtonClicked() {

        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        Boolean value = sharedPreferences.getBoolean("login", false);

        // resume from here

    }

    private void initBottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
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
                        startActivity(new Intent(getApplicationContext(),CategoryActivity.class));
                        overridePendingTransition(0,0);
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
                    productDescription = response.body().getData();

                    productName = findViewById(R.id.textView5);
                    productName.setText(productDescription.getName());


                    productPrice = (TextView) findViewById(R.id.textView6);
                    productPrice.setText(String.valueOf(productDescription.getPrice()));

                    merchantName = (TextView) findViewById(R.id.textView7);
                    merchantName.setText(productDescription.getMerchantName());

//                    Map<String,String> myMap= productDescription.getAttributes();
//                    ArrayList<String> keyList= (ArrayList<String>) myMap.keySet();
//                    keyList = new ArrayList<String>(keyList);

                    attributes = (TextView) findViewById(R.id.textView8);
                    attributes.setText(String.valueOf(productDescription.getAttributes()));

                    usp = (TextView) findViewById(R.id.textView9);
                    usp.setText(productDescription.getUsp());

                    description = (TextView) findViewById(R.id.textView10);
                    description.setText(productDescription.getDescription());

                    productImage = (ImageView) findViewById(R.id.imageView2);
                    Glide.with(productImage.getContext()).load(productDescription.getImage()).into(productImage);


                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductDescription>> call, Throwable t) {
                Log.e("Check", t.getMessage());
            }
        });

    }


    class CartButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            CartButtonClicked();
        }
    }


    public void initAddToCart() {
        addToCart = findViewById(R.id.addToCart);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // addToCartResponseBody.se   resume here naveen

    }


}
