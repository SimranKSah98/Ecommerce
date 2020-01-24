package com.example.myapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ListViewAdapter;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.ProductDescription;
import com.example.myapplication.pojo.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView list;
    ListViewAdapter adapter;
    SearchView searchView;
    List<SearchResponse> arraylist = new ArrayList<SearchResponse>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        list = (ListView) findViewById(R.id.listview);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener(){
            @Override
            public boolean onClose() {
                list.setAdapter(null);
                return false;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        App.getApp().getRetrofit().create(APIInterface.class).getSearchList(query).enqueue(
                new Callback<BaseResponse<List<SearchResponse>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<SearchResponse>>> call, Response<BaseResponse<List<SearchResponse>>> response) {

                        arraylist=response.body().getData();
                        adapter = new ListViewAdapter(SearchActivity.this, arraylist);
                        list.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<SearchResponse>>> call, Throwable t) {
                        Log.e("Check",t.getMessage());
                    }

                });
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length()==0){
            arraylist.clear();
            adapter.notifyDataSetChanged();
        }
        return false;
    }
}