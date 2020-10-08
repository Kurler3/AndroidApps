package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    Button logInBtn;
    TextView signUpText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = (EditText) findViewById(R.id.usernameEdit);
        passwordInput = (EditText) findViewById(R.id.passwordEdit);
        logInBtn = (Button) findViewById(R.id.logInBtn);
        signUpText = (TextView) findViewById(R.id.requestSignUpText);


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateCredentials()){
                    //check if this user exists in the database
                }
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    private boolean ValidateCredentials(){
        if(usernameInput.getText().toString().isEmpty()){
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