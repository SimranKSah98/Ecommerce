package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.model.CustomerDetails;
import com.example.myapplication.model.LoginRequestBody;
import com.example.myapplication.pojo.AddToCartRequestBody;
import com.example.myapplication.pojo.BaseResponse;
import com.example.myapplication.pojo.CartResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnSignup, btnLogin, btnfb;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 1;
    private GoogleSignInClient googleSignInClient;
    private CustomerDetails customerDetails;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        initView();
        initClickListerner();
        initBottomNavigation();
    }

    private void initView() {
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        signInButton = (SignInButton) findViewById(R.id.googlesign1);
        btnfb = (Button) findViewById(R.id.login_button);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.login);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initBottomNavigation() {
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


                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void initClickListerner() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FbActivity.class);
                startActivity(intent);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginRequestBody requestBody = new LoginRequestBody("", "manual", email, password, "");
                sendtoken(requestBody);
            }
        });
    }

    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedtask) {
        try {
            GoogleSignInAccount account = completedtask.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(account);
        } catch (ApiException e) {
            Toast.makeText(LoginActivity.this, "Signed In failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        LoginRequestBody requestBody = new LoginRequestBody("", "google", acct.getEmail(), "pass", acct.getIdToken());
        sendtoken(requestBody);
    }

    private void sendtoken(final LoginRequestBody requestBody) {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        App.getApp().getRetrofit().create(APIInterface.class).getCus(requestBody).enqueue(
                new Callback<CustomerDetails>() {
                    @Override
                    public void onResponse(Call<CustomerDetails> call, Response<CustomerDetails> response) {
                        if (response.isSuccessful()) {
                            customerDetails = response.body();
                            progressBar.setVisibility(View.INVISIBLE);
                            if (sharedPreferences.contains("guestCart")) {
                                sendCartItem(requestBody.getCustomerEmail());
                            } else {
                                login(requestBody.getCustomerEmail());
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerDetails> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );
    }

    void sendCartItem(final String emailId) {
        String cartItem = sharedPreferences.getString("guestCart", "");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<AddToCartRequestBody>>() {
        }.getType();
        List<AddToCartRequestBody> addToCartRequestBodies = gson.fromJson(cartItem, listType);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        App.getApp().getRetrofit().create(APIInterface.class).sendCartItemOnLogin(emailId, addToCartRequestBodies).enqueue(
                new Callback<BaseResponse<CartResponse>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<CartResponse>> call, Response<BaseResponse<CartResponse>> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            login(emailId);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<CartResponse>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void login(String emailId) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("customerEmailId", emailId);
        editor.putBoolean("login_details", true);
        editor.commit();
        Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}
