package com.example.geocare.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.Profile.ProfileActivity;
import com.example.geocare.R;
import com.example.geocare.Scan.ScanActivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button completeStepButton;
    TextView date, percent;
    int current = 0;
    RecyclerView recyclerView;

    //for NavBar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        recyclerView = findViewById(R.id.Schedule_recycler_view_for_day);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Set horizontal orientation
        date = findViewById(R.id.Schedule_current_date);
        progressBar = findViewById(R.id.linearProgressIndicator);
        completeStepButton = findViewById(R.id.donebutton); //thay bang swipe cho nay ne
        percent = findViewById(R.id.Schedule_percent);


        fetch_UI(); navBar();
        CurrentDaySet();
        DaySchedule();

        completeStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar(7);  //nay thi truyen vao so luong sp ban dau la duoc
            }
        });


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

// Get the week number of the current date
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeekNumber = currentDate.get(weekFields.weekOfWeekBasedYear());

// Get the start date (Sunday) of the current week
        LocalDate startOfWeek = currentDate.with(weekFields.dayOfWeek(), 1);

// Get the end date (Saturday) of the current week
        LocalDate endOfWeek = currentDate.with(weekFields.dayOfWeek(), 7);

        int startDayOfWeekValue = startOfWeek.getDayOfWeek().getValue();
        int endDatOfWeekValue = endOfWeek.getDayOfWeek().getValue();

        List<DayOfWeek> daysList = getCurrentWeek();
        DaysAdapter adapter = new DaysAdapter(daysList);
        recyclerView.setAdapter(adapter);
    }

    private List<DayOfWeek> getCurrentWeek() {
        List<DayOfWeek> daysList = new ArrayList<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            daysList.add(day);
        }

        return daysList;
    }

    private void fetch_UI() {
        homeIcon = findViewById(R.id.NaviBarHomeIcon);
        producIcon = findViewById(R.id.NaviBarProductIcon);
        scanIcon = findViewById(R.id.NaviBarScanIcon);
        scheduleIcon = findViewById(R.id.NaviBarScheduleIcon);
        profileIcon = findViewById(R.id.NaviBarProfileIcon);
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
