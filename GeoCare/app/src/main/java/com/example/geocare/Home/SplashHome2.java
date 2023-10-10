package com.example.geocare.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.geocare.R;

public class SplashHome2 extends AppCompatActivity {

    Intent i;
    String d,c;
    int pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_home2);
    }

    private void getData() {
        i = getIntent();
        d = (String) i.getStringExtra("DISTRICT");
        c = (String) i.getStringExtra("CITY");
        pick = (int) i.getIntExtra("PICK", 0);

    }
}