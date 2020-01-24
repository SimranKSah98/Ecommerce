package com.example.myapplication.controller;

import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CartItem;
import com.example.myapplication.pojo.Home;
import com.example.myapplication.pojo.ProductDescription;
import com.example.myapplication.model.Customer;
import com.example.myapplication.model.CustomerDetails;
import com.example.myapplication.model.LoginRequestBody;
import com.example.myapplication.model.ProductDetails;
import com.example.myapplication.model.Signupbody;
import com.example.myapplication.pojo.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("/customer/signin")
    Call<CustomerDetails> getCus(@Body LoginRequestBody requestBody);

    @POST("/customer/signup")
    Call<CustomerDetails> signup(@Body Signupbody signupBody);

    @GET("/product/details")
    Call<List<ProductDetails>> getprod();

    @GET("/user/dashboard/{id}")
    Call<List<Customer>> getCustomerdetails(@Path("id") String string);

    @GET("/search/{text}")
    Call<List<ProductDetails>> getsearchlist(@Path("text") String string);

    @GET("/home")
    Call<BaseResponse<Home>> getProducts();

    @GET("/category/product/{categoryid}")
    Call<BaseResponse<Home>> getCategoryProducts(@Path("categoryid") String categoryId);

    @GET("/product/description/{id}")
    Call<BaseResponse<ProductDescription>> getProductDescription(@Path("id") String productId);

    @GET
    Call<BaseResponse<CartItem>> getCartItems();

    @GET("/search/{string}")
    Call<BaseResponse<List<SearchResponse>>> getSearchList(@Path("string") String str);

}
