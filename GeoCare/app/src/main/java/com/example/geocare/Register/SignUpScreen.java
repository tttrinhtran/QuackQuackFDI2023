package com.example.geocare.Register;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Login.LoginScreen;
import com.example.geocare.Model.User;
import com.example.geocare.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpScreen extends AppCompatActivity {
    User user;
    FirebaseDatabaseController firebaseDatabaseController;
    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;

    TextInputEditText comfirmPasswordEditText;
    TextView signUpButton;

    SharedPreferenceManager sharedPreferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        firebaseDatabaseController=new FirebaseDatabaseController<>(User.class);
        loadUI();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });
    }
    void loadUI()
    {
        emailEditText=findViewById(R.id.RegisterScreenUsernameEditText);
        passwordEditText=findViewById(R.id.RegisterScreenPasswordEditText);
        comfirmPasswordEditText=findViewById(R.id.RegisterScreenConfirmPasswordEditText);
        signUpButton=findViewById(R.id.RegisterScreenButton);



    }
    void checkInput()
    {
        User temp;
        String userEmail= emailEditText.getText().toString();
        String userPassword=passwordEditText.getText().toString();
        String userConfirmPass=comfirmPasswordEditText.getText().toString();

        if(userEmail.isEmpty()||userPassword.isEmpty()||userConfirmPass.isEmpty())
        {
            Toast.makeText(SignUpScreen.this, "Please fulfill Username and Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        temp= (User) firebaseDatabaseController.retrieveObjectsFirestoreByID(KEY_COLLECTION_USERS,userEmail);
        if(temp!=null)
        {
            Toast.makeText(SignUpScreen.this, "This user already exist.", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(!userPassword.equals(userConfirmPass))
            {
                Toast.makeText(SignUpScreen.this, "The confirm password is not match.", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                user=new User();
                user.setUserEmail(userEmail);
                user.setUserPassword(userPassword);
                nextPage();
            }


        }

    }

    void nextPage()
    {
        sharedPreferenceManager=new SharedPreferenceManager<>(User.class,this);
                sharedPreferenceManager.storeSerializableObjectToSharedPreference(user,KEY_COLLECTION_USERS);
                Intent i=new Intent(SignUpScreen.this,PersonalInformation.class);
                startActivity(i);

    }
}