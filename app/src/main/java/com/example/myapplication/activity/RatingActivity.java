package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {
    RatingBar ratingbar;
    Button button;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order History");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick() {
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final int rating = (int) ratingbar.getRating();
                progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                App.getApp().getRetrofit().create(APIInterface.class).setProductRating(rating, getIntent().getStringExtra("productId")).enqueue(
                        new Callback<BaseResponse<Integer>>() {
                            @Override
                            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                                int rate = response.body().getData();
                                Toast.makeText(RatingActivity.this, "Thank you of Rating", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                                Toast.makeText(RatingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                        }
                );

            }

        });
    }
}
