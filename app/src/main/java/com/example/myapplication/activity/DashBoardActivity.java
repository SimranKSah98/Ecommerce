package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.Customer;
import com.example.myapplication.pojo.BaseResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashBoardActivity extends AppCompatActivity {

private Button logout;
private Retrofit retrofit;
private Call<BaseResponse<Customer>> call;
private  Customer customer;
TextView customerEmail,customerName,customerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initBottomNavigation();
        initLogout();
        retrofitAndApiCall();
        apicallback();
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
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
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

    public void retrofitAndApiCall()
    {
        retrofit= App.getApp().getRetrofit();
        APIInterface api=retrofit.create(APIInterface.class);
        SharedPreferences sharedPreferences=getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        String customerEmail=sharedPreferences.getString("customerEmailId",null);

        call=api.getCustomerdetails(customerEmail);
        Log.i("NAVEEN",customerEmail);
    }

   private void apicallback()
   {
       call.enqueue(new Callback<BaseResponse<Customer>>() {
           @Override
           public void onResponse(Call<BaseResponse<Customer>> call, Response<BaseResponse<Customer>> response) {

               if(response.isSuccessful())
               {
                   customer=response.body().getData();

                   customerEmail=findViewById(R.id.customer_email);
                   customerEmail.setText(customer.getEmailId());

                   customerName=findViewById(R.id.customer_name);
                   customerName.setText(customer.getCustomerName());




               }



           }

           @Override
           public void onFailure(Call<BaseResponse<Customer>> call, Throwable t) {

           }
       });
   }



    private void initLogout()
    {
        logout=findViewById(R.id.button3);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("com.example.myapplication.activity",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(DashBoardActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
