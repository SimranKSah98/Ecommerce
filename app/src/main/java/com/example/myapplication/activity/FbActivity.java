package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.model.CustomerDetails;
import com.example.myapplication.model.LoginRequestBody;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FbActivity extends AppCompatActivity {

    private static final String TAG = "FacebookLogin";
    private CallbackManager callbackManager;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        registerFacebookCallback();
        setContentView(R.layout.activity_login);
        initViewAndClickListener();
    }

    private void registerFacebookCallback() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Toke: " + loginResult.getAccessToken().getToken());
                LoginRequestBody requestBody = new LoginRequestBody(loginResult.getAccessToken().toString(), "facebook", loginResult.getAccessToken().getUserId(), "pass", loginResult.getAccessToken().getToken());
                sendtoken(requestBody);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void sendtoken(LoginRequestBody requestBody) {
        App.getApp().getRetrofit().create(APIInterface.class).getCus(requestBody).enqueue(
                new Callback<CustomerDetails>() {
                    @Override
                    public void onResponse(Call<CustomerDetails> call, Response<CustomerDetails> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(FbActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerDetails> call, Throwable t) {
                        Toast.makeText(FbActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void initViewAndClickListener() {
        btn = findViewById(R.id.login_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(FbActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}