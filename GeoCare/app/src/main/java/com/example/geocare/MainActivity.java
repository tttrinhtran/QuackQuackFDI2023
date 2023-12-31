package com.example.geocare;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Home.SplashHome;
import com.example.geocare.Home.SplashHome2;
import com.example.geocare.Login.LoginScreen;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.Profile.ProfileActivity;
import com.example.geocare.Register.SignUpScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
        startActivity(intent);
    }
}