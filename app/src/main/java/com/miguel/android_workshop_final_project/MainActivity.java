package com.miguel.android_workshop_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText emailInput, passwordInput;
    Button logInBtn;
    TextView signUpText, forgotPasswordText;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    int counter = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeViews();
        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser(); //Checking if the user is already logged in the app or not

        CheckUserLoggedInAndVerified(); //If the user is logged in, direct him to the next main menu

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateCredentials()){
                    TryLogIn();
                }
            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ResetPassword.class));
            }
        });
    }
    private void TryLogIn(){
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserVerified(); //Checks if the user is verified or not.
                }else{
                    counter--;
                    Toast.makeText(MainActivity.this,"Email or Password are wrong or don't exist. You Have " + counter + " attempts remaining.",Toast.LENGTH_LONG).show();
                    if(counter==0) logInBtn.setEnabled(false); //Disable the log in when user gets it wrong for 5 times.
                }
            }
        });
    }
    private void UserVerified(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user.isEmailVerified()){
            Toast.makeText(MainActivity.this,"Log In Successful!",Toast.LENGTH_SHORT).show();
            finish(); //Destroys the log in activity.
            startActivity(new Intent(MainActivity.this,MainMenu.class));
        }else{
            user.sendEmailVerification(); //sends the email again
            Toast.makeText(MainActivity.this,"You haven't verified your email yet. A new verification e-mail will be sent.",
                    Toast.LENGTH_LONG).show();
            firebaseAuth.signOut(); //signs out the user, because this function is called after the log in has been successful
        }
    }
    private void CheckUserLoggedInAndVerified(){
        if(user!=null && user.isEmailVerified()) startActivity(new Intent(MainActivity.this, MainMenu.class));
    }
    private void InitializeViews(){
        emailInput = (EditText) findViewById(R.id.emailEdit);
        passwordInput = (EditText) findViewById(R.id.passwordEdit);
        logInBtn = (Button) findViewById(R.id.logInBtn);
        signUpText = (TextView) findViewById(R.id.requestSignUpText);
        forgotPasswordText = (TextView) findViewById(R.id.forgotPasswordText);
    }
    private boolean CheckAdmin(){
        if(emailInput.getText().toString().equals("admin") && passwordInput.getText().toString().equals("admin")){ //Goes straigth to the main menu
            startActivity(new Intent(MainActivity.this, MainMenu.class));
            return true;
        }
        return false;
    }
    private boolean ValidateCredentials(){
        if(emailInput.getText().toString().isEmpty()){
            Toast.makeText(this,"Please Enter Your Username", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(passwordInput.getText().toString().isEmpty()){
            Toast.makeText(this,"Please Enter Your PassWord", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}