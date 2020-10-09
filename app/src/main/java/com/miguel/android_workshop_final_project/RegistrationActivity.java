package com.miguel.android_workshop_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity {
    EditText emailInput, passwordInput, cfPassWordInput, usernameInput;
    Button signUpBtn;
    TextView requestLogInText;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

       InitializingViews();
       firebaseAuth = FirebaseAuth.getInstance();

       signUpBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(ValidateData()) RegisterNewUser();
           }
       });

       requestLogInText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
           }
       });

    }
    private void RegisterNewUser(){
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    SendEmailVerification();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class)); //Directs user to log in
                    finish();
                }else{
                    Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void SendEmailVerification(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    SendUserToDatabase();
                    firebaseAuth.signOut();
                    Toast.makeText(RegistrationActivity.this,"Registration Successful! Verification Email Sent!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrationActivity.this,"Registration Not Successful. Verification Email Was Not Sent. Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void SendUserToDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(firebaseAuth.getUid()); //Every user has an unique Uid.
        myRef.setValue(new UserProfile(emailInput.getText().toString(),usernameInput.getText().toString()));
    }
    private boolean ValidateData(){
        if(usernameInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Input An Username",Toast.LENGTH_SHORT).show();
            return false;
        }else if(emailInput.getText().toString().isEmpty()){ //also should match an regex
            Toast.makeText(this, "Please Input An Email",Toast.LENGTH_SHORT).show();
            return false;
        }else if(passwordInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Input A Password",Toast.LENGTH_SHORT).show();
            return false;
        }else if(cfPassWordInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Confirm The Password",Toast.LENGTH_SHORT).show();
            return false;
        }else if(cfPassWordInput.getText().toString().compareTo(passwordInput.getText().toString())!=0){
            Toast.makeText(this, "Confirmation Of Password Is Different From Password",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void InitializingViews(){
        emailInput = (EditText) findViewById(R.id.emailSignUpEdit);
        passwordInput = (EditText) findViewById(R.id.passWordSignUpEdit);
        cfPassWordInput = (EditText) findViewById(R.id.confirmPasswordSignUpEdit);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        requestLogInText = (TextView) findViewById(R.id.requestLogInText);
        usernameInput = (EditText) findViewById(R.id.usernameSignUpEdit);
    }
}