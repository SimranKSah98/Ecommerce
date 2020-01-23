package com.example.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.ProductDescription;
import com.example.myapplication.controller.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import com.example.myapplication.activity.adapter.MerchantAdapter;

public class ProductDescriptionActivity extends AppCompatActivity {

   private Retrofit retrofit;
    private Call<BaseResponse<ProductDescription>> call;
    private List<ProductDescription> list = new ArrayList();

    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    private TextView productName, productPrice, merchantName, attributes, usp, description;
    private ImageView productImage;
    private Button addToCart;

    private RecyclerView merchantrecyclerView;
    private RecyclerView commentView;

 //   private MerchantAdapter merchantAdapter;
    private ProductDescription productDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        addToCart=new Button(this);
        addToCart.setOnClickListener(new CartButtonClick());

        initView();

    }


    private void CartButtonClicked()
    {

        SharedPreferences sharedPreferences=getSharedPreferences("user_details",MODE_PRIVATE);
        Boolean value=sharedPreferences.getBoolean("login",false);

        // resume from here

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

                    productName=findViewById(R.id.textView5);
                    productName.setText(productDescription.getName());



                    productPrice=(TextView)findViewById(R.id.textView6);
                    productPrice.setText(String.valueOf(productDescription.getPrice()));

                    merchantName =(TextView)findViewById(R.id.textView7);
                    merchantName.setText(productDescription.getMerchantName());

                    attributes =(TextView)findViewById(R.id.textView8);
                    attributes.setText(String.valueOf(productDescription.getAttributes()));

                    usp =(TextView)findViewById(R.id.textView9);
                    usp.setText(productDescription.getUsp());

                    description =(TextView)findViewById(R.id.textView10);
                    description.setText(productDescription.getDescription());

                    productImage=(ImageView)findViewById(R.id.imageView2);
                      Glide.with(productImage.getContext()).load(productDescription.getImage()).into(productImage);


                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductDescription>> call, Throwable t) {
                Log.e("Check", t.getMessage());
            }
        });

    }



    class CartButtonClick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            CartButtonClicked();
        }
    }





}
