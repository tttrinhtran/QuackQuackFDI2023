package com.example.geocare.Survey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.geocare.R;

public class SurveyActivity2 extends AppCompatActivity {
    private ImageView opt1, opt2, opt3, opt4, opt5, opt6, opt7, opt8;
    private boolean isImage1 = false, isImage2 = false, isImage3 = false, isImage4 = false;
    private boolean isImage5 = false, isImage6 = false, isImage7 = false, isImage8 = false;

    ImageView back, next, back_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        fetchUI();
        setUpState();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImage1){}
                if (isImage2){}
                if (isImage3){}
                if (isImage4){}
                if (isImage5){}
                if (isImage6){}
                if (isImage7){}
                if (isImage8){}

                //CHUYEN qUA PROFILE TRONG SKIN CONDITION
            }
        });
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState1();
                checkState();
            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState2();
                checkState();
            }
        });

        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState3();
                checkState();
            }
        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState4();
                checkState();
            }
        });

        opt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState5();
                checkState();
            }
        });

        opt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState6();
                checkState();
            }
        });

        opt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState7();
                checkState();
            }
        });

        opt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState8();
                checkState();
            }
        });

    }

    private void setUpState() {
        opt1.setImageResource(R.drawable.wrinkless);
        opt2.setImageResource(R.drawable.acne);
        opt3.setImageResource(R.drawable.dryness);
        opt4.setImageResource(R.drawable.redness);
        opt5.setImageResource(R.drawable.sun_damage);
        opt6.setImageResource(R.drawable.dark_circles);
        opt7.setImageResource(R.drawable.hormonal_acne);
        opt8.setImageResource(R.drawable.oiliness);

    }

    private void fetchUI() {
        opt1 = findViewById(R.id.wrinkless);
        opt2 = findViewById(R.id.acne);
        opt3 = findViewById(R.id.dryness);
        opt4 = findViewById(R.id.redness);
        opt5 = findViewById(R.id.sun_damage);
        opt6 = findViewById(R.id.dark_circles);
        opt7 = findViewById(R.id.hormonal_acne);
        opt8 = findViewById(R.id.oiliness);

        back = findViewById(R.id.Survey2_back_button);
        next = findViewById(R.id.Survey2_next_button);
        back_icon = findViewById(R.id.Survey2_back_icon);
    }

    void checkState(){
        if (isImage1 || isImage2 || isImage3 || isImage4
            || isImage5 || isImage6 || isImage7 || isImage8){
            next.setImageResource(R.drawable.next_button_active);
        }
        else
            next.setImageResource(R.drawable.next_button);
    }

    void changeState1(){
        if (isImage1) {
            opt1.setImageResource(R.drawable.wrinkless);
        } else {
            opt1.setImageResource(R.drawable.wrinkles_option);

        }
        isImage1 = !isImage1;
    }

    void changeState2() {
        if (isImage2) {
            opt2.setImageResource(R.drawable.acne);
        } else {
            opt2.setImageResource(R.drawable.acne_option);
        }
        isImage2 = !isImage2;
    }

    void changeState3() {
        if (isImage3) {
            opt3.setImageResource(R.drawable.dryness);
        } else {
            opt3.setImageResource(R.drawable.dryness_option);
        }
        isImage3 = !isImage3;
    }

    void changeState4() {
        if (isImage4) {
            opt4.setImageResource(R.drawable.redness);
        } else {
            opt4.setImageResource(R.drawable.redness_option);
        }
        isImage4 = !isImage4;
    }

    void changeState5() {
        if (isImage5) {
            opt5.setImageResource(R.drawable.sun_damage);
        } else {
            opt5.setImageResource(R.drawable.sun_damage_option);
        }
        isImage5 = !isImage5;
    }

    void changeState6() {
        if (isImage6) {
            opt6.setImageResource(R.drawable.dark_circles);
        } else {
            opt6.setImageResource(R.drawable.dark_circles_option);
        }
        isImage6 = !isImage6;
    }

    void changeState7() {
        if (isImage7) {
            opt7.setImageResource(R.drawable.hormonal_acne);
        } else {
            opt7.setImageResource(R.drawable.hormonal_acne_option);
        }
        isImage7 = !isImage7;
    }

    void changeState8() {
        if (isImage8) {
            opt8.setImageResource(R.drawable.oiliness);
        } else {
            opt8.setImageResource(R.drawable.oiliness_option);
        }
        isImage8 = !isImage8;
    }


}