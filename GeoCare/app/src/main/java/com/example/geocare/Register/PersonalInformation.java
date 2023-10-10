package com.example.geocare.Register;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;
import com.google.android.material.textfield.TextInputEditText;

public class PersonalInformation extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    User user;
    TextInputEditText userNameEditText;
   EditText userAgeEditText;
TextView confirmBtn;
FirebaseDatabaseController firebaseDatabaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        getData();
        loadInput();
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();

            }
        });

    }
    void loadInput() {
        userNameEditText = findViewById(R.id.InfoRegisterUserNameEditText);
        userAgeEditText = findViewById(R.id.InfoRegisterAgeText);
        confirmBtn = findViewById(R.id.InfoRegisterConfirmButton);
    }

    void getInfo()
    {

                String userName=userNameEditText.getText().toString() ;
                String userAge=userAgeEditText.getText().toString();

                if(userName.isEmpty()||userAge.isEmpty())
                {
                    Toast.makeText(PersonalInformation.this, "Please fill the blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setUserAge(userAge);
                user.setUserName(userName);
                sharedPreferenceManager=new SharedPreferenceManager<>(User.class, this);
                sharedPreferenceManager.storeSerializableObjectToSharedPreference(user,KEY_COLLECTION_USERS);
        Intent intent=new Intent(PersonalInformation.this, SurveySkinType.class);
        startActivity(intent);




    }

    void getData()
    {
        sharedPreferenceManager=new SharedPreferenceManager<>(User.class, this);
        user= (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }


    

}