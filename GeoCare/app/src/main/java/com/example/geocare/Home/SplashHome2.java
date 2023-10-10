package com.example.geocare.Home;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;

public class SplashHome2 extends AppCompatActivity {

    Intent i;
    String d,c;
    int pick;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_home2);

        getUserData();
    }

    private void getUserData() {
        SharedPreferenceManager instance = new SharedPreferenceManager(User.class,this);
        currentUser = (User) instance.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }

    private void getData() {
        i = getIntent();
        d = (String) i.getStringExtra("DISTRICT");
        c = (String) i.getStringExtra("CITY");
        pick = (int) i.getIntExtra("PICK", 0);
    }
}