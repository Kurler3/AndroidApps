package com.miguel.android_workshop_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    EditText emailResetInput;
    Button resetPasswordBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

       InitializeViews();
       firebaseAuth = FirebaseAuth.getInstance();

       resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(ValidateData()){
                   String email = emailResetInput.getText().toString().trim();
                   firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(ResetPassword.this,"Password Reset Email Sent!",Toast.LENGTH_SHORT).show();
                               finish();
                               startActivity(new Intent(ResetPassword.this, MainActivity.class));
                           }else{
                               Toast.makeText(ResetPassword.this,"Password Reset Not Sent Successfully",Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }
       });
    }
    private void InitializeViews(){
        resetPasswordBtn = (Button) findViewById(R.id.resetPasswordBtn);
        emailResetInput = (EditText) findViewById(R.id.emailResetInput);
    }
    private boolean ValidateData(){
        if(emailResetInput.getText().toString().isEmpty()){
            Toast.makeText(ResetPassword.this, "You need to enter your email!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}