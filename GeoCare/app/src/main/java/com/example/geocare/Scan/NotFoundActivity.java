package com.example.geocare.Scan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.geocare.Home.HomeActivity;
import com.example.geocare.R;

public class NotFoundActivity extends AppCompatActivity {

    ImageView back_button;
    View supportView;
    TextView irritant;
    String receivedString;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_found);

        back_button = findViewById(R.id.NotFound_back_button);
        irritant = findViewById(R.id.NotFound_irritantList);
        supportView = findViewById(R.id.SUpportViewNotFound);

        receivedString = getIntent().getStringExtra("Irritant");

        if (receivedString != null && receivedString != ""){
            if (receivedString == "None") {
                irritant.setTextColor(ContextCompat.getColor(this, R.color.blue_description));
            }
            else{
                irritant.setTextColor(ContextCompat.getColor(this, R.color.red_irritant));
            }
            irritant.setText(receivedString);

        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotFoundActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        supportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotFoundActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}