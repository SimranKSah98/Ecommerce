package com.example.myapplication.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.model.CustomerDetails;
import com.example.myapplication.model.Signupbody;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputName;
    private Button btnSignIn, btnSignUp, select, upload;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    String email, password, name;
    private ImageView imageView;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        initView();
//        select.setOnClickListener((View.OnClickListener) this);
//        upload.setOnClickListener((View.OnClickListener) this);
        initClickListerner();
    }

    private void initClickListerner() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                name = inputName.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Signupbody signupbody = new Signupbody(email, password, name, "tcytc", "en");
                sendtoken(signupbody);
            }
        });
    }

    private void initView() {
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputName = (EditText) findViewById(R.id.name);
        imageView = (ImageView) findViewById(R.id.imageView);
//        select = (Button) findViewById(R.id.buttonChoose);
//        upload = (Button) findViewById(R.id.buttonUpload);
    }


    public void sendtoken(Signupbody signupBody)
    {
        App.getApp().getRetrofit().create(APIInterface.class).signup(signupBody).enqueue
                (
                new Callback<CustomerDetails>() {
                    @Override
                    public void onResponse(Call<CustomerDetails> call, Response<CustomerDetails> response) {
                        if (response.isSuccessful()) {
                            // success logic
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignupActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerDetails> call, Throwable t) {
                        Toast.makeText(SignupActivity.this, "failed", Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }
}