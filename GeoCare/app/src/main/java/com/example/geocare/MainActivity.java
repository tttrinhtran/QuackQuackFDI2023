package com.example.geocare;

import static com.example.geocare.Constants.KEY_COLLECTION_PRODUCT;
import static com.example.geocare.Constants.KEY_COLLECTION_ROUTINE;
import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Model.RoutineModel;
import com.example.geocare.Model.User;
import com.example.geocare.Product.Item;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.Profile.ProfileActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        startActivity(intent);
    }
}