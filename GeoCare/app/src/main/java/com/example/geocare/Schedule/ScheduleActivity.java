package com.example.geocare.Schedule;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;
import static com.example.geocare.Constants.KEY_PRODUCT_ITEM;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.example.geocare.Model.RoutineModel;
import com.example.geocare.Model.User;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.Profile.ProfileActivity;
import com.example.geocare.R;
import com.example.geocare.Scan.ScanActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    Intent intent;
    String d; String c; int pick;

    //for NavBar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;
    ItemAdapter adapter;
    private List<ProductItem> productList = new ArrayList<>();
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getUserData();
        getData();
        setUpScreen();
    }

    private void getData() {
        intent = getIntent();
        d = (String) intent.getStringExtra("DISTRICT");
        c = (String) intent.getStringExtra("CITY");
        pick = (int) intent.getIntExtra("PICK", 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUser();
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
                ProgressBar( numberSteps, 1 );
                currentUser.setUserNumberDone(currentUser.getUserNumberDone() + 1);
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

        productList = currentUser.getUserProductList();

        // Number of step
        numberSteps = productList.size() + currentUser.getUserNumberDone();
        ProgressBar(numberSteps, currentUser.getUserNumberDone());

    }


    void ProgressBar( float maxProduct, int number ){
        float per = 1 / maxProduct;
        int currentProgress = progressBar.getProgress();
        int maxProgress = progressBar.getMax();
        int incrementAmount = (int) (per * maxProgress); // 10% increment
        int newProgress = Math.min( currentProgress + incrementAmount * number, maxProgress );
        String currentPercent = Integer.toString(newProgress);
        currentPercent += "%";

        // Update the progress bard
        //rounded if not divisible
        if ( ( 100 - currentProgress > per * 100 ) && ( 100 - currentProgress < per * 100 * 2 ) ) {
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
                passFirstProductList();
                Intent i = new Intent(ScheduleActivity.this, HomeActivity.class);
                i.putExtra("DISTRICT", d);
                i.putExtra("CITY", c);
                i.putExtra("PICK", pick);
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

    private void saveUser()
    {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager<>(User.class, this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(currentUser,KEY_COLLECTION_USERS);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(KEY_COLLECTION_USERS).document(currentUser.getUserEmail());
        documentReference.update(
                "UserNumberDone", currentUser.getUserNumberDone()
        );
    }
    void passFirstProductList()
    {
        SharedPreferenceManager sharedPreferenceManagerItem=new SharedPreferenceManager<>(ProductItem.class, this);
        sharedPreferenceManagerItem.storeSerializableObjectToSharedPreference(productList.get(0),KEY_PRODUCT_ITEM);

    }
}
