package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String newDateInput;
    Button chooseDateBtn,cancelBtn,confirmBtn;
    TextView newDateText;
    EditText newEventTitleInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        InstanteateViews();

        chooseDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  EventDatePickerFragment datePicker = new EventDatePickerFragment();
                  datePicker.show(getSupportFragmentManager(),"Choose Date");
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckDataInputed()){
                    String newEventTitle = newEventTitleInput.getText().toString();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("title",newEventTitle);
                    resultIntent.putExtra("date",newDateInput);


                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private void InstanteateViews(){
        chooseDateBtn = findViewById(R.id.chooseNewEventDateBtn);
        cancelBtn = findViewById(R.id.addEventCancelBtn);
        confirmBtn = findViewById(R.id.addEventConfirmBtn);
        newEventTitleInput = findViewById(R.id.newEventTitleInput);
        newDateText = findViewById(R.id.newDateText);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        EventsList.year = i;
        EventsList.month = i1;
        EventsList.day = i2;

        newDateInput = i2 + "/" + i1 + "/" + i;
        newDateText.setText("Date:" +  newDateInput);

    }
    private boolean CheckDataInputed(){
        if(newDateInput.isEmpty()){
            Toast.makeText(AddEventActivity.this,"Please Enter A Date",Toast.LENGTH_LONG).show();
            return false;
        }else if(newEventTitleInput.getText().toString().isEmpty()){
            Toast.makeText(AddEventActivity.this,"Please Enter A Title",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}