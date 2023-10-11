package com.example.geocare.Home;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;
import static com.example.geocare.Constants.KEY_COLLECTION_WEATHER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;
import com.example.geocare.Schedule.ProductItem;
import com.example.geocare.Schedule.ScheduleActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashHome2 extends AppCompatActivity {

    Intent intent;
    String district , city;
    int pick;

    Weather weatherInfo;
    private TextView title; private TextView gotoSchedule;
    private ConstraintLayout splashHome2;
    private ConstraintLayout layoutItem1; private TextView title1, des1; private ImageView imageItem1;
    private ConstraintLayout layoutItem2; private TextView title2, des2; private ImageView imageItem2;
    private ConstraintLayout layoutItem3; private TextView title3, des3; private ImageView imageItem3;


    User currentUser;
    
    ArrayList<ProductItem> product; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_home2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        getUserData();
        getData();
        fetch_UI();
        init_UI(); 

        initData();

        setUpLayout();

        setUpAnimation();

        onClickListener();
    }

    private void onClickListener() {
        gotoSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashHome2.this, ScheduleActivity.class);
                saveWeather();
                i.putExtra("DISTRICT", district);
                i.putExtra("CITY", city);
                i.putExtra("PICK", pick);
                startActivity(i);
            }
        });
    }

    private void saveWeather() {
        SharedPreferenceManager sharedPreferenceManagerWeather=new SharedPreferenceManager(Weather.class,this);
        sharedPreferenceManagerWeather.storeSerializableObjectToSharedPreference(weatherInfo,KEY_COLLECTION_WEATHER);
    }

    private void setUpAnimation() {
        title.animate()
                .alpha(1f)
                .translationX(0)
                .setDuration(500)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // After the title animation is done, animate the layout for item 1
                        layoutItem1.animate()
                                .alpha(1f)
                                .translationY(0)
                                .setDuration(500)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Continue with the animations for item 2 and item 3
                                        layoutItem2.animate()
                                                .alpha(1f)
                                                .translationY(0)
                                                .setDuration(500)
                                                .withEndAction(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        layoutItem3.animate()
                                                                .alpha(1f)
                                                                .translationY(0)
                                                                .setDuration(500)
                                                                .withEndAction(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        // Finally, animate the button
                                                                        gotoSchedule.animate()
                                                                                .alpha(1f)
                                                                                .translationY(0)
                                                                                .setDuration(500);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void setUpLayout() {
        List<Integer> colors = new ArrayList<>();

        // Add elements to the list
        colors.add(R.color.light_yellow);
        colors.add(R.color.blue_deep);
        colors.add(R.color.blue_rain);
        colors.add(R.color.blue_snow);

        List<Integer> colorText = new ArrayList<>();

        // Add elements to the list
        colorText.add(R.color.blue_deep);
        colorText.add(R.color.white);
        colorText.add(R.color.white);
        colorText.add(R.color.blue_deep);

        List<Integer> borderDecor = new ArrayList<>();
        borderDecor.add(R.drawable.border_adapter_blue);
        borderDecor.add(R.drawable.border_adapter_white);
        borderDecor.add(R.drawable.border_adapter_white);
        borderDecor.add(R.drawable.border_adapter_blue);

        splashHome2.setBackgroundColor(ContextCompat.getColor(this, colors.get(pick - 1)));

        // text
        title.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        layoutItem1.setBackgroundResource(borderDecor.get(pick-1));
        layoutItem2.setBackgroundResource(borderDecor.get(pick-1));
        layoutItem3.setBackgroundResource(borderDecor.get(pick-1));

        title1.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        des1.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));

        title2.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        des2.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));

        title3.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        des3.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));

        gotoSchedule.setBackgroundColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        if(pick == 1|| pick == 4){
            gotoSchedule.setTextColor(ContextCompat.getColor(this, colorText.get(1)));
        } else if (pick == 2 || pick == 3) {
            gotoSchedule.setTextColor(ContextCompat.getColor(this, colorText.get(0)));
        }
    }

    private void init_UI() {
        title.setTranslationX(-100); title.setAlpha(0);
        layoutItem1.setTranslationY(100); layoutItem1.setAlpha(0);
        layoutItem2.setTranslationY(100); layoutItem2.setAlpha(0);
        layoutItem3.setTranslationY(100); layoutItem3.setAlpha(0);
        gotoSchedule.setTranslationY(100); gotoSchedule.setAlpha(0);
    }

    private void fetch_UI() {
        splashHome2 = findViewById(R.id.SplashHomeLayout2);
        title = findViewById(R.id.Splash2Title);
        layoutItem1 = findViewById(R.id.Splash2_Item1);
        layoutItem2 = findViewById(R.id.Splash2_Item2);
        layoutItem3 = findViewById(R.id.Splash2_Item3);

        title1 = findViewById(R.id.itemStepName1);
        des1 = findViewById(R.id.itemDes1);
        imageItem1 = findViewById(R.id.ItemImage1);

        title2 = findViewById(R.id.itemStepName2);
        des2 = findViewById(R.id.itemDes2);
        imageItem2 = findViewById(R.id.ItemImage2);

        title3 = findViewById(R.id.itemStepName3);
        des3 = findViewById(R.id.itemDes3);
        imageItem3 = findViewById(R.id.ItemImage3);

        gotoSchedule = findViewById(R.id.Splash2Button);
    }

    private void initData() {
        product = currentUser.getUserProductList();

        // item 1
        title1.setText(product.get(0).getRoutineName());
        des1.setText(product.get(0).getRoutineSmallDes());
        imageItem1.setImageResource(product.get(0).getImageIDResource()); imageItem1.setScaleType(ImageView.ScaleType.CENTER_CROP);


        // item 2
        title2.setText(product.get(1).getRoutineName());
        des2.setText(product.get(1).getRoutineSmallDes());
        imageItem2.setImageResource(product.get(1).getImageIDResource()); imageItem2.setScaleType(ImageView.ScaleType.CENTER_CROP);


        // item 3
        title3.setText(product.get(2).getRoutineName());
        des3.setText(product.get(2).getRoutineSmallDes());
        imageItem3.setImageResource(product.get(2).getImageIDResource()); imageItem3.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void getUserData() {
        SharedPreferenceManager instance = new SharedPreferenceManager(User.class,this);
        currentUser = (User) instance.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }

    private void getData() {
        intent = getIntent();
        getWeather();
        district = (String) intent.getStringExtra("DISTRICT");
        city = (String) intent.getStringExtra("CITY");
        pick = (int) intent.getIntExtra("PICK", 0);
    }

    private void getWeather() {
        SharedPreferenceManager sharedPreferenceManagerWeather=new SharedPreferenceManager(Weather.class,this);
        weatherInfo= (Weather) sharedPreferenceManagerWeather.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_WEATHER);
    }
}