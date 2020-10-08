package com.miguel.android_workshop_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity {
    EditText emailInput, passwordInput, cfPassWordInput;
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
               if(ValidateData()){
                    RegisterNewUser();
               }
               else{
                   Toast.makeText(RegistrationActivity.this,"wtf happened", Toast.LENGTH_SHORT).show();
               }
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
                    Toast.makeText(RegistrationActivity.this,"Registration Successful!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class)); //Directs user to log in
                }else{
                    Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean ValidateData(){
        if(emailInput.getText().toString().isEmpty()){ //also should match an regex
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
    }
}