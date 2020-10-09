package com.miguel.android_workshop_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {
    Button profileBtn, toDoListBtn, moodBtn, alarmBtn, eventsBtn, waterReminderBtn, othersBtn, aboutBtn;
    TextView greetingsText;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        InitializeViews();
        SettingButtonsOnClick();

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference(firebaseAuth.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile profile = snapshot.getValue(UserProfile.class); //Gets user information
                if(profile.userName!=null || profile!=null) {
                    greetingsText.setText("Welcome, " + profile.userName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainMenu.this,error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SettingButtonsOnClick(){
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        toDoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(MainMenu.this, ToDoList.class));
            }
        });

        moodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        eventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        waterReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        othersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_menu_layout,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logOutBtn:
                LogOut();
                return true;
            case R.id.settingsMenuOption:
                //Something
                return true;
            default:
                return true;
        }
    }
    private void LogOut(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainMenu.this, MainActivity.class)); //Directs user back to log in activity
    }
    private void InitializeViews(){
        greetingsText = (TextView) findViewById(R.id.greetingsText);

        profileBtn = (Button) findViewById(R.id.profileBtn);
        toDoListBtn = (Button) findViewById(R.id.toDoListBtn);
        moodBtn = (Button) findViewById(R.id.moodBtn);
        alarmBtn = (Button) findViewById(R.id.alarmBtn);
        eventsBtn = (Button) findViewById(R.id.eventsBtn);
        waterReminderBtn = (Button) findViewById(R.id.waterReminderBtn);
        othersBtn = (Button) findViewById(R.id.othersBtn);
        aboutBtn = (Button) findViewById(R.id.aboutBtn);
    }
}