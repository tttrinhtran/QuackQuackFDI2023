package com.example.geocare.Schedule;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geocare.Constants;
import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Model.User;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.Profile.ProfileActivity;
import com.example.geocare.R;
import com.example.geocare.Scan.ScanActivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    TextView date, percent, routine_day;
    int numberSteps = 0;
    RecyclerView recyclerViewRoutine;

    //for NavBar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;
    ItemAdapter adapter;
    private List<ProductItem> productList = new ArrayList<>();
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        getUserData();
        setUpScreen();
    }

    // Implement swipe-to-delete using ItemTouchHelper
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            // Handle item deletion here
            int position = viewHolder.getAdapterPosition();
            if( position != 0 ) {
                Toast.makeText( getApplicationContext(), "Please do step by step", Toast.LENGTH_SHORT ).show();
                adapter.notifyItemChanged(position);
            }
            else {
                adapter.removeItem(position);
                ProgressBar(numberSteps);
            }

        }
    };

    private void getUserData() {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(User.class,this);
        currentUser = (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }

    private void setUpScreen() {

        // Initialize
        fetch_UI(); navBar();
        CurrentDaySet();
        DaySchedule();


        // Set up recycle view for daily routine
        setUpproductList();

        recyclerViewRoutine = findViewById(R.id.Schedule_recyclerview_forRoutine);
        recyclerViewRoutine.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter(productList);
        recyclerViewRoutine.setAdapter(adapter);


        // Set up swipe for daily routine
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewRoutine);
    }

    private void setUpproductList() {
//        if (true){
//            productList.addAll(Type.productList_OilySunnyMorning);
//            numberSteps = productList.size();
//        }

        FirebaseDatabaseController<ProductItem> instance = new FirebaseDatabaseController<>(ProductItem.class);

        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        String day = "Sun";

        if (currentHour <= 17 && currentHour >= 3) day = "Sun";
        else day = "Night";

        // Micellar
        String micellarID = "Cleanser-";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) micellarID += "Oil";
        else micellarID += currentUser.getUserSkinType();
        micellarID += "-" + day ;

        String micellarImageString = "cleanser_";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) micellarImageString += "oil";
        else micellarImageString += currentUser.getUserSkinType().toLowerCase();

        ProductItem micellar = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, micellarID );
        if( micellar != null ) micellar.setImageIDResource(getResources().getIdentifier(micellarImageString, "drawable", getPackageName()));

        if( micellar != null ) productList.add(micellar);

        // Cleanser
        String cleanserID = "Cleanser-";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) cleanserID += "Oil";
        else cleanserID += currentUser.getUserSkinType();
        cleanserID += "-" + day ;

        String cleanserImageString = "cleanser_";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) cleanserImageString += "oil";
        else cleanserImageString += currentUser.getUserSkinType().toLowerCase();

        ProductItem cleanser = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, cleanserID );
        if( cleanser != null ) cleanser.setImageIDResource(getResources().getIdentifier(cleanserImageString, "drawable", getPackageName()));

        if( cleanser != null ) productList.add(cleanser);

        // Toner
        String tonerID = "Toner-";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) tonerID += "Oil";
        else tonerID += currentUser.getUserSkinType();
        tonerID += "-" + day ;

        String tonerImageString = "toner_";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) tonerImageString += "oil";
        else tonerImageString += currentUser.getUserSkinType().toLowerCase();

        ProductItem toner = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, tonerID );
        if( toner != null ) toner.setImageIDResource(getResources().getIdentifier(tonerImageString, "drawable", getPackageName()));

        if( toner != null ) productList.add(toner);

        // Serum
        String serumID = "Serum-";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) serumID += "Oil";
        else serumID += currentUser.getUserSkinType();
        serumID += "-" + day ;

        String serumImageString = "serum_";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) serumImageString += "oil";
        else serumImageString += currentUser.getUserSkinType().toLowerCase();

        ProductItem serum = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, serumID );
        if( serum != null ) serum.setImageIDResource(getResources().getIdentifier(serumImageString, "drawable", getPackageName()));

        if( serum != null ) productList.add(serum);

        // Mask
        String maskID = "Mask-";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) maskID += "Oil";
        else maskID += currentUser.getUserSkinType();
        maskID += "-" + day ;

        String maskImageString = "mask_";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) maskImageString += "oil";
        else maskImageString += currentUser.getUserSkinType().toLowerCase();

        ProductItem mask = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, maskID );
        if( mask != null ) mask.setImageIDResource(getResources().getIdentifier(maskImageString, "drawable", getPackageName()));

        if( mask != null ) productList.add(mask);

        // Moisturizer
        String moisturizerID = "Mask-";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) moisturizerID += "Oil";
        else moisturizerID += currentUser.getUserSkinType();
        moisturizerID += "-" + day ;

        String moisturizerImageString = "mask_";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) moisturizerImageString += "oil";
        else moisturizerImageString += currentUser.getUserSkinType().toLowerCase();

        ProductItem moisturizer = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, moisturizerID );
        if( moisturizer != null ) moisturizer.setImageIDResource(getResources().getIdentifier(moisturizerImageString, "drawable", getPackageName()));

        if( moisturizer != null ) productList.add(moisturizer);

        // Sunscreen
        String sunscreenID = "Mask-";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) sunscreenID += "Oil";
        else sunscreenID += currentUser.getUserSkinType();
        sunscreenID += "-" + day ;

        String sunscreenImageString = "mask_";
        if(Objects.equals(currentUser.getUserSkinType(), "Oily")) sunscreenImageString += "oil";
        else sunscreenImageString += currentUser.getUserSkinType().toLowerCase();

        ProductItem sunscreen = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, sunscreenID );
        if( sunscreen != null ) sunscreen.setImageIDResource(getResources().getIdentifier(sunscreenImageString, "drawable", getPackageName()));

        if( sunscreen != null ) productList.add(sunscreen);

        // Number of step
        numberSteps = productList.size();
    }


    void ProgressBar(float maxProduct){
        float per = 1/maxProduct;
        int currentProgress = progressBar.getProgress();
        int maxProgress = progressBar.getMax();
        int incrementAmount = (int) (per * maxProgress); // 10% increment
        int newProgress = Math.min(currentProgress + incrementAmount, maxProgress);
        String currentPercent = Integer.toString(newProgress);
        currentPercent += "%";

        // Update the progress bard
        //rounded if not divisible
        if ((100-currentProgress > per*100) && (100-currentProgress < per*100*2)){
            progressBar.setProgress(100);
            percent.setText("100%");
        }
        else {
            progressBar.setProgress(newProgress);
            percent.setText(currentPercent);
        }
    }

    private void CurrentDaySet() {
        LocalDate currentDate = LocalDate.now();

        // Get the day of the week from the current date
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();

        // Get the name of the day (full name)
        String currentDayName = currentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.US);

        // Get the day of the month
        int currentDayOfMonth = currentDate.getDayOfMonth();

        // Get the name of the month (abbreviated name)
        Month currentMonth = currentDate.getMonth();
        String currentMonthAbbreviated = currentMonth.getDisplayName(TextStyle.SHORT, Locale.US);

        // Combine the components to create the desired format
        String formattedDate = currentDayName + " " + currentDayOfMonth + ", " + currentMonthAbbreviated;

        date.setText(formattedDate);
    }


    private void DaySchedule() {

        LocalDate currentDate = LocalDate.now();
        int currentDayOfMonth = currentDate.getDayOfMonth();

        // Get the week number of the current date
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Monday
        LocalDate currentFetchDay = currentDate.with(weekFields.dayOfWeek(), 2 );
        ((TextView)findViewById(R.id.NumberDate1)).setText(String.valueOf(currentFetchDay.getDayOfMonth()));
        if( currentFetchDay.isEqual(currentDate) ) {
            ((TextView)findViewById(R.id.NumberDate1)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
            ((TextView)findViewById(R.id.TitleDate1)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
        }
        // Tuesday
        currentFetchDay = currentFetchDay.plusDays(1);
        ((TextView)findViewById(R.id.NumberDate2)).setText(String.valueOf(currentFetchDay.getDayOfMonth()));
        if( currentFetchDay.isEqual(currentDate) ) {
            ((TextView)findViewById(R.id.NumberDate2)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
            ((TextView)findViewById(R.id.TitleDate2)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
        }
        // Wednesday
        currentFetchDay = currentFetchDay.plusDays(1);
        ((TextView)findViewById(R.id.NumberDate3)).setText(String.valueOf(currentFetchDay.getDayOfMonth()));
        if( currentFetchDay.isEqual(currentDate) ) {
            ((TextView)findViewById(R.id.NumberDate3)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
            ((TextView)findViewById(R.id.TitleDate3)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
        }
        // Thursday
        currentFetchDay = currentFetchDay.plusDays(1);
        ((TextView)findViewById(R.id.NumberDate4)).setText(String.valueOf(currentFetchDay.getDayOfMonth()));
        if( currentFetchDay.isEqual(currentDate) ) {
            ((TextView)findViewById(R.id.NumberDate4)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
            ((TextView)findViewById(R.id.TitleDate4)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
        }
        // Friday
        currentFetchDay = currentFetchDay.plusDays(1);
        ((TextView)findViewById(R.id.NumberDate5)).setText(String.valueOf(currentFetchDay.getDayOfMonth()));
        if( currentFetchDay.isEqual(currentDate) ) {
            ((TextView)findViewById(R.id.NumberDate5)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
            ((TextView)findViewById(R.id.TitleDate5)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
        }
        // Saturday
        currentFetchDay = currentFetchDay.plusDays(1);
        ((TextView)findViewById(R.id.NumberDate6)).setText(String.valueOf(currentFetchDay.getDayOfMonth()));
        if( currentFetchDay.isEqual(currentDate) ) {
            ((TextView)findViewById(R.id.NumberDate6)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
            ((TextView)findViewById(R.id.TitleDate6)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
        }
        // Sunday
        currentFetchDay = currentFetchDay.plusDays(1);
        ((TextView)findViewById(R.id.NumberDate7)).setText(String.valueOf(currentFetchDay.getDayOfMonth()));
        if( currentFetchDay.isEqual(currentDate) ) {
            ((TextView)findViewById(R.id.NumberDate7)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
            ((TextView)findViewById(R.id.TitleDate7)).setTextColor(ContextCompat.getColor(this, R.color.blue_deep));
        }

        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();

        if (currentHour <= 17 && currentHour >= 3) routine_day.setText("Morning routine");
        else routine_day.setText("Night routine");
    }

    private void fetch_UI() {
        homeIcon = findViewById(R.id.NaviBarHomeIcon);
        producIcon = findViewById(R.id.NaviBarProductIcon);
        scanIcon = findViewById(R.id.NaviBarScanIcon);
        scheduleIcon = findViewById(R.id.NaviBarScheduleIcon);
        profileIcon = findViewById(R.id.NaviBarProfileIcon);
        date = findViewById(R.id.Schedule_current_date);
        progressBar = findViewById(R.id.linearProgressIndicator);
        percent = findViewById(R.id.Schedule_percent);
        routine_day = findViewById(R.id.Schedule_routine_day);
    }

    private void navBar() {
        scheduleIcon.setImageResource(R.drawable.schedule_icon_filled);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScheduleActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        producIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScheduleActivity.this, ProductActivity.class);
                startActivity(i);
            }
        });

        scanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScheduleActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScheduleActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }


}
