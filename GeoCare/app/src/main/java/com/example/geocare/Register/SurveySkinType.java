package com.example.geocare.Register;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.Product.MyAdapter;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.R;

import java.util.ArrayList;

public class SurveySkinType extends AppCompatActivity {

    SharedPreferenceManager sharedPreferenceManager;
    User user;
    ArrayList<surveyItem> surveySkinType;
    RecyclerView recyclerView;
    surveyAdapter surveyAdapter;
    ImageView nextBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_skin_type);
        nextBtn=findViewById(R.id.Survey1_next_button);
        backBtn=findViewById(R.id.Survey1_back_button);

        getData();
        setUpRecyclerView();


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    nextBtn.setImageResource(R.drawable.next_button_active);
                    saveUser();
                    Intent intent=new Intent(SurveySkinType.this, surveySkinCon.class);
                    startActivity(intent);


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void getData()
    {
        sharedPreferenceManager=new SharedPreferenceManager<>(User.class, this);
        user= (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
        surveySkinType=new ArrayList<>();
        surveySkinType.add(new surveyItem("Dry skin","dry_skin"));
        surveySkinType.add(new surveyItem("Oil skin","oily_skin"));
        surveySkinType.add(new surveyItem("Normal skin","normal_skin"));
        surveySkinType.add(new surveyItem("Conbinaion skin","sensitive_skin"));

    }

    void setUpRecyclerView()
    {
        recyclerView=findViewById(R.id.skinSurveyRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        surveyAdapter = new surveyAdapter( surveySkinType,SurveySkinType.this, user, nextBtn);
        recyclerView.setAdapter(surveyAdapter);

    }
    void saveUser()
    {
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user, KEY_COLLECTION_USERS);
    }

}