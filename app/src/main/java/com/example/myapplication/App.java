package com.example.myapplication;

import android.app.Application;

import com.facebook.FacebookSdk;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static Retrofit retrofit;
    private static App app;
    public static String BASE_URL = "http://10.177.68.47:8089";

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.setApplicationId("499064640741937");
        FacebookSdk.sdkInitialize(this);
        setApp(this);
    }

    public Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();

        return retrofit;
    }

    public static App getApp() {
        return app;
    }

    public static void setApp(App app) {
        App.app = app;
    }

}