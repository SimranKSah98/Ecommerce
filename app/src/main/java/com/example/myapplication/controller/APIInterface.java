package com.example.myapplication.controller;

import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CartItem;
import com.example.myapplication.pojo.CartResponse;
import com.example.myapplication.pojo.Home;
import com.example.myapplication.pojo.ProductDescription;
import com.example.myapplication.pojo.Customer;
import com.example.myapplication.model.CustomerDetails;
import com.example.myapplication.model.LoginRequestBody;
import com.example.myapplication.model.ProductDetails;
import com.example.myapplication.model.Signupbody;
import com.example.myapplication.pojo.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("customer/signin")
    Call<CustomerDetails> getCus(@Body LoginRequestBody requestBody);

    @POST("customer/signup")
    Call<CustomerDetails> signup(@Body Signupbody signupBody);

    @GET("product/details")
    Call<List<ProductDetails>> getprod();

    @GET("customer/details/{customerEmailId}")
    Call<BaseResponse<Customer>> getCustomerdetails(@Path("customerEmailId") String string);

    @GET("search/{text}")
    Call<List<ProductDetails>> getsearchlist(@Path("text") String string);

    @GET("home")
    Call<BaseResponse<Home>> getProducts();

    @GET("category/product/{categoryid}")
    Call<BaseResponse<Home>> getCategoryProducts(@Path("categoryid") String categoryId);

    @GET("product/description/{id}")
    Call<BaseResponse<ProductDescription>> getProductDescription(@Path("id") String productId);

    @GET("cart/get/{customerEmailId}")
    Call<BaseResponse<CartResponse>> getCartItems(@Path("customerEmailId") String string);

    @GET("search/{string}")
    Call<BaseResponse<List<SearchResponse>>> getSearchList(@Path("string") String str);

    @POST("/cart/add/{customerId}")
    Call<BaseResponse<CartResponse>> updateCart(@Path("customerId") String string, @Body AddToCartRequestBody addToCartRequestBody);

    @GET("customer/order-history/{span}/{customerEmailId}")
    Call<BaseResponse<List<OrderHistory>>> getorderhistory(@Path("span") int span, @Path("customerEmailId") String userId);

    @GET("home/{categoryId}")
    Call<BaseResponse<CategoriesItem>> getRespectiveCategoryProducts(@Path("categoryId") String categoryId);

    @GET("/cart/getCartCount/{customerEmailId}")
    Call<BaseResponse<Integer>> getCartCount(@Path("customerEmailId") String string);

    @POST("/cart/mergeCart/{customerId}")
    Call<BaseResponse<CartResponse>> sendCartItemOnLogin(@Path("customerId") String string, @Body List<AddToCartRequestBody> addToCartRequestBody);

    @GET("/cart/buyNow/{customerEmailId}")
    Call<BaseResponse<Boolean>> buyNow(@Path("customerEmailId") String string);
}
