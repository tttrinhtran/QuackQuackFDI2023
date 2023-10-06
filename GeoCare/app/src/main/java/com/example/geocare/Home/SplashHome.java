package com.example.geocare.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.geocare.R;

public class SplashHome extends AppCompatActivity {
    private LottieAnimationView lottieAnimationView;
    private View supportView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        fetch_UI();
        init_UI();

        supportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashHome.this, SplashHome2.class);
                startActivity(i);
            }
        });


    }

    private void init_UI() {
        lottieAnimationView.animate().setDuration(3000);
    }

    private void fetch_UI() {
        lottieAnimationView = findViewById(R.id.lottieSplash);
        supportView = findViewById(R.id.SplashSupport);
    }
}