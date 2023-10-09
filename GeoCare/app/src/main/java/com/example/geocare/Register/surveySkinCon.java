package com.example.geocare.Register;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;

import java.util.ArrayList;

public class surveySkinCon extends AppCompatActivity {
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
        setContentView(R.layout.activity_survey_skin_con);
        getData();

        nextBtn=findViewById(R.id.Survey2_next_button);
        backBtn=findViewById(R.id.Survey2_back_button);
        setUpRecyclerView();


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextBtn.setImageResource(R.drawable.next_button_active);
                Toast.makeText(surveySkinCon.this, "Good", Toast.LENGTH_SHORT).show();
                saveUser();



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
        surveySkinType.add(new surveyItem("Wrinkle","wrinkles"));
        surveySkinType.add(new surveyItem("Acne","acne"));
        surveySkinType.add(new surveyItem("Dryness","dryness"));
        surveySkinType.add(new surveyItem("Redness","redness"));
        surveySkinType.add(new surveyItem("Sun damage","sun_damage"));
        surveySkinType.add(new surveyItem("Dark circle","dark_circles"));
        surveySkinType.add(new surveyItem("Hormonal acne","hormonal_acne"));
        surveySkinType.add(new surveyItem("Oiliness","oiliness"));

    }

    void setUpRecyclerView()
    {
        recyclerView=findViewById(R.id.skinSurveyRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        surveyAdapter = new surveyAdapter( surveySkinType,surveySkinCon.this, user, nextBtn);
        recyclerView.setAdapter(surveyAdapter);

    }
    void saveUser()
    {
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user, KEY_COLLECTION_USERS);
    }
}