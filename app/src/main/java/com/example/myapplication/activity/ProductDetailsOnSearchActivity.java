package com.example.myapplication.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetailsOnSearchActivity extends AppCompatActivity implements PopularProductsAdapter.OnCardListener {

    private List<ProductsItem> productlist = new ArrayList();
    Call <BaseResponse<List<SearchResponse>>> call;
    List<SearchResponse> arraylist = new ArrayList<SearchResponse>();
    Retrofit retrofit;

    private RecyclerView recyclerView;
    private SearchPopularAdapter searchPopularAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecycleView();
    }

    private void initRecycleView()
    {
        setContentView(R.layout.activity_searchdetailspage);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ProductDetailsOnSearchActivity.this, 2);
        recyclerView = findViewById(R.id.searchrecycleview);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // initRetrofitAndCallApi();
    }

    public void initRetrofitAndCallApi(String str)
    {
        App.getApp().getRetrofit().create(APIInterface.class).getSearchList(str).enqueue(
                new Callback<BaseResponse<List<SearchResponse>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<SearchResponse>>> call, Response<BaseResponse<List<SearchResponse>>> response) {
                        arraylist=response.body().getData();
                        searchPopularAdapter =new SearchPopularAdapter(ProductDetailsOnSearchActivity.this, arraylist);
                        recyclerView.setAdapter(searchPopularAdapter);

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<SearchResponse>>> call, Throwable t) {

                    }
                }
        );
    }

    @Override
    public void onCardClick(String id) {

    }
}
