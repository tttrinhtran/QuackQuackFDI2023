package com.example.geocare.Login;

import static android.content.ContentValues.TAG;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Home.SplashHome;
import com.example.geocare.Model.User;
import com.example.geocare.R;
import com.example.geocare.Register.SignUpScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
   FirebaseDatabaseController<User> userFirebaseDatabaseController;
    User user;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    TextView signInBtn;
    TextView signUpBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setUpUI();
        mAuth=FirebaseAuth.getInstance();
        userFirebaseDatabaseController=new FirebaseDatabaseController<>(User.class);
        checkSignIn();

        onClickListener();
    }

    private void onClickListener() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(i);
            }
        });
    }


    private void setUpUI()
    {
       userEmailEditText=findViewById(R.id.LoginScreenUsernameEditText);
       userPasswordEditText=findViewById(R.id.LoginScreenPasswordEditText);
       signInBtn=findViewById(R.id.LoginScreenButton);
       signUpBtn = findViewById(R.id.SignUpButton);

    }
    void getInput()
    {
        String userEmail= userEmailEditText.getText().toString();
        String userPassword=userPasswordEditText.getText().toString();
        User tmp=userFirebaseDatabaseController.retrieveObjectsFirestoreByID(KEY_COLLECTION_USERS,userEmail);


        if(userEmail.isEmpty()||userPassword.isEmpty())
        {
            Toast.makeText(LoginScreen.this, "Please fulfill Username and Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            checkAccount(userEmail, userPassword);
        }

    }
    void checkAccount(String email, String password)
    {
        User tmp=userFirebaseDatabaseController.retrieveObjectsFirestoreByID(KEY_COLLECTION_USERS,email);
        tmp.getUserEmail();
        if(tmp!=null)
        { if(password.equals(tmp.getUserPassword())) {
            Toast.makeText(LoginScreen.this, "Login success", Toast.LENGTH_SHORT).show();
            user = tmp;
            saveUser();
            Intent intent=new Intent(LoginScreen.this, SplashHome.class);
            startActivity(intent);
            return;
        }
             else
            {
                Toast.makeText(LoginScreen.this, "Wrong password", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else
        {
            Toast.makeText(LoginScreen.this, "Login fail", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private void checkSignIn()
    {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getInput();

            }
        });



    }
    void saveUser()
    {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager<>(User.class, this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user,KEY_COLLECTION_USERS);

    }


}