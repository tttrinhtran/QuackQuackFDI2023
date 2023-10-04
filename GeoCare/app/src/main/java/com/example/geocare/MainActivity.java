package com.example.geocare;

import static com.example.geocare.Constants.KEY_COLLECTION_PRODUCT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Model.RoutineModel;
import com.example.geocare.Product.Item;
import com.example.geocare.Product.ProductActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<RoutineModel> itemTest;
    FirebaseDatabaseController firebaseDatabaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabaseController=new FirebaseDatabaseController<>(RoutineModel.class);

       itemTest=new ArrayList<>();


        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}