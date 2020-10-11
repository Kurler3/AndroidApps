package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class WaterReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {
    Spinner intervalSpinner;
    TextView intervalStartText, intervalEndText;
    boolean isSettingStartTime;
    SpannableString startTime, endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder);

        InstanteateViews();
        HourSelectSpinner();
        //HighlightText();

        intervalStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSettingStartTime=true;
                AlarmTimePicker startTimePicker = new AlarmTimePicker();
                startTimePicker.show(getSupportFragmentManager(),"Choose Start Time");
            }
        });
        intervalEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSettingStartTime=false;
                AlarmTimePicker endTimePicker = new AlarmTimePicker();
                endTimePicker.show(getSupportFragmentManager(),"Choose End Time");
            }
        });

    }
    /*private void HighlightText(){
        startTime = new SpannableString("mm/hh");
        endTime = new SpannableString("mm/hh");

        startTime.setSpan(new BackgroundColorSpan(Color.BLACK),0,startTime.length(),0);
        endTime.setSpan(new BackgroundColorSpan(Color.BLACK),0,endTime.length(),0);

        intervalStartText.setText(startTime);
        intervalEndText.setText(endTime);
    }*/
    private void HourSelectSpinner(){
        intervalSpinner = findViewById(R.id.waterReminderIntervalSpinner);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.water_reminder_intervals,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        intervalSpinner.setAdapter(arrayAdapter);

        intervalSpinner.setOnItemSelectedListener(this);
    }
    private void InstanteateViews(){
        intervalSpinner = findViewById(R.id.waterReminderIntervalSpinner);
        intervalStartText = findViewById(R.id.waterIntervalStartText);
        intervalEndText = findViewById(R.id.waterIntervalEndText);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if(isSettingStartTime){
            //do some logic between the end and the interval times

            intervalStartText.setText(i + "/" + i1);
        }else{
            //some logic between the start and the interval times

            intervalEndText.setText(i + "/" + i1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //handle here the hour interval selected
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}