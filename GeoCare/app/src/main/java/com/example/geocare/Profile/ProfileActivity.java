package com.example.geocare.Profile;

import static com.example.geocare.Constants.KEY_COLLECTION_PRODUCT;
import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Home.HomeActivity;
import com.example.geocare.Model.User;
import com.example.geocare.Product.Item;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.R;
import com.example.geocare.Register.surveyAdapter;
import com.example.geocare.Register.surveySkinCon;
import com.example.geocare.Scan.ScanActivity;
import com.example.geocare.Schedule.ScheduleActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    // For NavBar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;
    private ImageView setting;

    User user;
    ArrayList<Item> whistlistList;
    ArrayList<Item> shelfList;
    FirebaseDatabaseController firebaseDatabaseController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        firebaseDatabaseController=new FirebaseDatabaseController<>(Item.class);
        getUser();
        fetch_UI();
        load_UI();
        loadData();
        setRecyclerview();

        navBar();

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Settings.class);
                startActivity(intent);
            }
        });
    }

    private void fetch_UI() {
        homeIcon = findViewById(R.id.NaviBarHomeIcon);
        producIcon = findViewById(R.id.NaviBarProductIcon);
        scanIcon = findViewById(R.id.NaviBarScanIcon);
        scheduleIcon = findViewById(R.id.NaviBarScheduleIcon);
        profileIcon = findViewById(R.id.NaviBarProfileIcon);
        setting = findViewById(R.id.Profile_setting_icon);

    }
    private void load_UI()
    {
        TextView userNametv=findViewById(R.id.Profile_user_name);
        TextView userSkinType= findViewById(R.id.Profile_user_skin_type);
        TextView userAge=findViewById(R.id.Profile_user_age);
        TextView userCondition=findViewById(R.id.Profile_user_skin_condition);

        userSkinType.setText(user.getUserSkinType());
        userNametv.setText(user.getUserName());
        userAge.setText(user.getUserAge());
        userCondition.setText(user.conditionListToString());

    }
    void loadData()
    {

        ArrayList<String> whistlistString=new ArrayList<>();
        whistlistString=user.getUserFavorite();
        for(int i=0; i<whistlistString.size(); i++)
        {
            if(whistlistList==null)
            {
                whistlistList=new ArrayList<>();
            }
            whistlistList.add((Item) firebaseDatabaseController.retrieveObjectsFirestoreByID(KEY_COLLECTION_PRODUCT, whistlistString.get(i)));

        }
        ArrayList<String>shelfString=new ArrayList<>();
        shelfString=user.getUserSelf();
        for(int i=0; i<shelfString.size(); i++)
        {
            if(shelfList==null)
            {
                shelfList=new ArrayList<>();
            }
            shelfList.add((Item) firebaseDatabaseController.retrieveObjectsFirestoreByID(KEY_COLLECTION_PRODUCT, shelfString.get(i)));

        }
    }
    void setRecyclerview()
    {
        RecyclerView recyclerView=findViewById(R.id.Profile_wishlisted_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WhislistApdater whislistApdater = new WhislistApdater(whistlistList,this);
        recyclerView.setAdapter(whislistApdater);

//        RecyclerView recyclerViewShelf=findViewById(R.id.Profile_shelf_recyclerview);
//        recyclerViewShelf.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
//        ShelfAdapter shelfAdapter = new ShelfAdapter(shelfList,this);
//        recyclerViewShelf.setAdapter(shelfAdapter);


    }


    private void navBar() {
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        scanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });

        scheduleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ScheduleActivity.class);
                startActivity(i);
            }
        });

        producIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ProductActivity.class);
                startActivity(i);
            }
        });
    }

    void getUser()
    {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(User.class,this);
        user= (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }

}