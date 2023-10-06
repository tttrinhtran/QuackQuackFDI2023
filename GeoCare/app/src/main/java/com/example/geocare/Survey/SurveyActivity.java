package com.example.geocare.Survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geocare.R;

public class SurveyActivity extends AppCompatActivity {
    private ImageView opt1, opt2, opt3, opt4;
    private boolean isImage1 = false, isImage2 = false, isImage3 = false, isImage4 = false;

    ImageView back, next, back_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        opt1 = findViewById(R.id.skin_option1);
        opt2 = findViewById(R.id.skin_option2);
        opt3 = findViewById(R.id.skin_option3);
        opt4 = findViewById(R.id.skin_option4);

        back = findViewById(R.id.Survey1_back_button);
        next = findViewById(R.id.Survey1_next_button);
        back_icon = findViewById(R.id.Survey1_back_icon);

        setUpState();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImage1 || isImage2 || isImage3 || isImage4) {
                    if (isImage1) {
                    }
                    if (isImage2) {
                    }
                    if (isImage3) {
                    }
                    if (isImage4) {
                    }

                    //SKIN TYPE CHUYEN CHO PROFILE chac la share preference

                    Intent intent = new Intent(SurveyActivity.this, SurveyActivity2.class);
                    startActivity(intent);
                }
            }
        });


        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState1();
                checkState();
                if (isImage1){
                    if (isImage2) changeState2();
                    if (isImage3) changeState3();
                    if (isImage4) changeState4();
                }

            }
        });


        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState2();
                checkState();
                if (isImage2){
                    if (isImage1) changeState1();
                    if (isImage3) changeState3();
                    if (isImage4) changeState4();
                }
            }
        });

        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState3();
                checkState();
                if (isImage3){
                    if (isImage1) changeState1();
                    if (isImage2) changeState2();
                    if (isImage4) changeState4();
                }
            }
        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState4();
                checkState();
                if (isImage4){
                    if (isImage1) changeState1();
                    if (isImage2) changeState2();
                    if (isImage3) changeState3();
                }
            }
        });

    }

    void setUpState(){
        opt1.setImageResource(R.drawable.normal_skin);
        opt2.setImageResource(R.drawable.oily_skin);
        opt3.setImageResource(R.drawable.sensitive_skin);
        opt4.setImageResource(R.drawable.dry_skin);
    }

    void changeState1(){
        if (isImage1) {
            opt1.setImageResource(R.drawable.normal_skin);
        } else {
            opt1.setImageResource(R.drawable.normal_skin_option);

        }
        isImage1 = !isImage1;
    }

    void changeState2(){
        if (isImage2) {
            opt2.setImageResource(R.drawable.oily_skin);
        } else {
            opt2.setImageResource(R.drawable.oily_skin_option);
        }
        isImage2 = !isImage2;
    }

    void changeState3(){
        if (isImage3) {
            opt3.setImageResource(R.drawable.sensitive_skin);
        } else {
            opt3.setImageResource(R.drawable.sensitive_skin_option);
        }
        isImage3 = !isImage3;
    }

    void changeState4(){
        if (isImage4) {
            opt4.setImageResource(R.drawable.dry_skin);
        } else {
            opt4.setImageResource(R.drawable.dry_skin_option);
        }
        isImage4 = !isImage4;
    }

    void checkState(){
        if (isImage1 || isImage2 || isImage3 || isImage4){
            next.setImageResource(R.drawable.next_button_active);
        }
        else
            next.setImageResource(R.drawable.next_button);
    }
}