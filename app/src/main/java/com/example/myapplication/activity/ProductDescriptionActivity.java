package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.myapplication.pojo.MerchantListItem;
import com.example.myapplication.pojo.ProductDescription;
import com.example.myapplication.pojo.ProductsItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.example.myapplication.activity.adapter.MerchantAdapter;

public class ProductDescriptionActivity extends AppCompatActivity implements MerchantAdapter.OtherMerchantListener {
    //private  Button addToCart;

    private Retrofit retrofit;
    private Call<BaseResponse<ProductDescription>> call;
    private Call<BaseResponse<AddToCartRequestBody>> callAddToCart;
    private List<ProductDescription> list = new ArrayList();


    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    private TextView productName, productPrice, merchantName, attributes, usp, description;
    private ImageView productImage;
    private Button addToCart;
    private Toolbar tbtoolbarmain;

    private ProductDescription productDescription;
    private ProductsItem productsItem;
    private AddToCartRequestBody addToCartRequestBody = new AddToCartRequestBody();
    private String merchantId, name;
   // SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
  //  SharedPreferences.Editor editor=sharedPreferences.edit();
   private List<AddToCartRequestBody> guestCartList=new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


        initView();
        initAddToCart();


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
        call = api.getProductDescription(getIntent().getStringExtra("productId"));


    }


    public void initView() {
        tbtoolbarmain = findViewById(R.id.toolbar);
        tbtoolbarmain.setTitle("Back");
        tbtoolbarmain.setTitleTextColor(getResources().getColor(android.R.color.white));
        tbtoolbarmain.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        tbtoolbarmain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        initRetrofitAndCallApi();
        apiCallback();

        //   merchantrecyclerView = findViewById(R.id.recycler_view);
        //    merchantrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //  merchantrecyclerView.setAdapter(merchantAdapter);
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
                Log.e("Check", t.getMessage());
            }
        });


    }

    private void initAddToCart() {

        addToCart = findViewById(R.id.addToCart);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
                  SharedPreferences.Editor editor=sharedPreferences.edit();
                final Boolean value = sharedPreferences.getBoolean("login_details", false);

                if (value == true) {


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
                    //  System.out.println(customerId);
                    callAddToCart = api.updateCart(customerId, addToCartRequestBody);


                    callAddToCart.enqueue(new Callback<BaseResponse<AddToCartRequestBody>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<AddToCartRequestBody>> call, Response<BaseResponse<AddToCartRequestBody>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ProductDescriptionActivity.this, "Added to cart", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ProductDescriptionActivity.this, CartActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<AddToCartRequestBody>> call, Throwable t) {
                            Log.e("addToCart", t.getMessage());
                        }


                    });


                }

                else if(value==false){

                    String guestCart = sharedPreferences.getString("guestCart","");
                    Gson gson=new Gson();
                    Type listType = new TypeToken<ArrayList<AddToCartRequestBody>>(){}.getType();
                    List<AddToCartRequestBody> addToCartRequestBodies=gson.fromJson(guestCart,listType);
                    String productId = getIntent().getStringExtra("productId");
                    if(null!=addToCartRequestBodies){
                        guestCartList.addAll(addToCartRequestBodies);
                        for(AddToCartRequestBody addToCartRequestBody : guestCartList){
                            if(addToCartRequestBody.getProductId().equals(productId)){
                                guestCartList.remove(addToCartRequestBody);
                                addToCartRequestBody.setQuantity(addToCartRequestBody.getQuantity()+1);
                                guestCartList.add(addToCartRequestBody);
                                setList("guestCart",guestCartList);
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

                    setList("guestCart",guestCartList);



                }
            }
        });
    }


    public <AddToCartRequestBody> void setList(String key, List<AddToCartRequestBody> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }


    public  void set(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
         SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


}
