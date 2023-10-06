package com.example.geocare.Profile;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    void getUserData()
    {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager<>(User.class, this);
        user= (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);

    }
}