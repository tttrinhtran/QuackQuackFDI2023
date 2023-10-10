package com.example.geocare.Profile;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Model.User;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.R;
import com.example.geocare.Scan.ScanActivity;
import com.example.geocare.Schedule.ScheduleActivity;

public class ProfileActivity extends AppCompatActivity {

    // For NavBar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;
    private ImageView setting;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getUser();


        fetch_UI();
        navBar();
        load_UI();

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Settings.class);
                startActivity(intent);
            }
        });
    }

    private void fetch_UI() {
        homeIcon = findViewById(R.id.NaviBarHomeIcon);
        producIcon = findViewById(R.id.NaviBarProductIcon);
        scanIcon = findViewById(R.id.NaviBarScanIcon);
        scheduleIcon = findViewById(R.id.NaviBarScheduleIcon);
        profileIcon = findViewById(R.id.NaviBarProfileIcon);
        setting = findViewById(R.id.Profile_setting_icon);

    }
    private void load_UI()
    {
        TextView userNametv=findViewById(R.id.Profile_user_name);
        TextView userSkinType= findViewById(R.id.Profile_user_skin_type);
        TextView userAge=findViewById(R.id.Profile_user_age);
        TextView userCondition=findViewById(R.id.Profile_user_skin_condition);

        userSkinType.setText(user.getUserSkinType());
        userNametv.setText(user.getUserName());
        userAge.setText(user.getUserAge());
        userCondition.setText(user.conditionListToString());



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

    void getUser()
    {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(User.class,this);
        user= (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }
}