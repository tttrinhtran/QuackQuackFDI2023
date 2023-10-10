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

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;

import java.util.ArrayList;

public class surveySkinCon extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    User user;
    ArrayList<surveyItem>  surveySkinCon;
    RecyclerView recyclerView;
    surveyAdapter surveyAdapter;
    ImageView nextBtn;
    ImageView backBtn;
    FirebaseDatabaseController firebaseDatabaseController;

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
                addUserToDb();




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
        surveySkinCon=new ArrayList<>();
        surveySkinCon.add(new surveyItem("Wrinkle","wrinkles"));
        surveySkinCon.add(new surveyItem("Acne","acne"));
        surveySkinCon.add(new surveyItem("Dryness","dryness"));
        surveySkinCon.add(new surveyItem("Redness","redness"));
        surveySkinCon.add(new surveyItem("Sun damage","sun_damage"));
        surveySkinCon.add(new surveyItem("Dark circle","dark_circles"));
        surveySkinCon.add(new surveyItem("Hormonal acne","hormonal_acne"));
        surveySkinCon.add(new surveyItem("Oiliness","oiliness"));

    }

    void setUpRecyclerView()
    {
        recyclerView=findViewById(R.id.skinSurveyRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        surveyAdapter = new surveyAdapter( surveySkinCon,surveySkinCon.this, user, nextBtn, false);
        recyclerView.setAdapter(surveyAdapter);

    }
    void saveUser()
    {
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user, KEY_COLLECTION_USERS);
    }
    void addUserToDb()
    {
        firebaseDatabaseController=new FirebaseDatabaseController<>(User.class);
        firebaseDatabaseController.addToFirestore(KEY_COLLECTION_USERS,user.getUserEmail(),user);

    }
}