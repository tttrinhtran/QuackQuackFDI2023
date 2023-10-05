package com.example.geocare.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.R;
import com.example.geocare.Scan.ScanActivity;
import com.example.geocare.Schedule.ScheduleActivity;

public class ProfileActivity extends AppCompatActivity {

    // For NavBar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fetch_UI();
        navBar();
    }

    private void fetch_UI() {
        homeIcon = findViewById(R.id.NaviBarHomeIcon);
        producIcon = findViewById(R.id.NaviBarProductIcon);
        scanIcon = findViewById(R.id.NaviBarScanIcon);
        scheduleIcon = findViewById(R.id.NaviBarScheduleIcon);
        profileIcon = findViewById(R.id.NaviBarProfileIcon);
    }

    private void navBar() {
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        scanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });

        scheduleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ScheduleActivity.class);
                startActivity(i);
            }
        });

        producIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ProductActivity.class);
                startActivity(i);
            }
        });
    }
}