package com.example.geocare.Scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geocare.Home.HomeActivity;
import com.example.geocare.R;

public class NotFoundActivity extends AppCompatActivity {

    ImageView back_button;
    TextView irritant;
    String receivedString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_found);

        back_button = findViewById(R.id.NotFound_back_button);
        irritant = findViewById(R.id.NotFound_irritantList);

        receivedString = getIntent().getStringExtra("Irritant");

        if (receivedString != null && receivedString != "")
            irritant.setText(receivedString);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotFoundActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}