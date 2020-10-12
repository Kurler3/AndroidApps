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
import android.widget.Toast;

public class WaterReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {
    int intervalHours[] = {1,2,4,6};
    Spinner intervalSpinner;
    TextView intervalStartText, intervalEndText, hourIntervalWarning;
    boolean isSettingStartTime;
    SpannableString startTime, endTime;
    int startHour, startMinutes, endHour, endMinutes, hourInterval;


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
        hourIntervalWarning = findViewById(R.id.hourIntervalWarning);


        startHour = 9;
        endHour=20;
        hourInterval = 1;
        startMinutes = 0;
        endMinutes = 0;
        //No need to set the minutes for both to 0 because they are 0 already
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if(isSettingStartTime && IsStartTimeChoosenValid(i,i1)){

            SettingStartTime(i,i1);

            startHour = i;
            startMinutes = i1;
        }else if(!isSettingStartTime && IsEndTimeChoosenValid(i,i1)){

            SettingEndTime(i,i1);

            endHour = i;
            endMinutes = i1;
        }
    }
    private void SettingStartTime(int i, int i1){
        if(i<10 && i1<10){
            intervalStartText.setText("0" + i + ":" + "0" + i1);
        }else if(i<10){
            intervalStartText.setText("0" + i + ":" + i1);
        }else if(i1<10){
            intervalStartText.setText(i + ":" + "0" + i1);
        }else{
            intervalStartText.setText(i + ":" + i1);
        }
    }
    private void SettingEndTime(int i, int i1){
        if(i<10 && i1<10){
            intervalEndText.setText("0" + i + ":" + "0" + i1);
        }else if(i<10){
            intervalEndText.setText("0" + i + ":" + i1);
        }else if(i1<10){
            intervalEndText.setText(i + ":" + "0" + i1);
        }else{
            intervalEndText.setText(i + ":" + i1);
        }
    }
    private boolean IsStartTimeChoosenValid(int hour, int minutes){
        if(hour > endHour){
            Toast.makeText(WaterReminder.this,"Start hour cannot be greater than End hour!",Toast.LENGTH_LONG).show();
            return false;
        }else if( ((endHour - hour) < hourInterval) || ((endHour - hour)==0 && (endMinutes-minutes)<0) || ((hourInterval == (endHour-hour)) && ((endMinutes-minutes)<0)) ){
            Toast.makeText(WaterReminder.this,"Reminding interval is bigger than the difference between start and end hours!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean IsEndTimeChoosenValid(int hour, int minutes){
        if(hour < startHour){
            Toast.makeText(WaterReminder.this,"End hour cannot be smaller than Start hour!",Toast.LENGTH_LONG).show();
            return false;
        }else if( ((endHour - hour) < hourInterval) || ((hour - startHour)==0 && (minutes-startMinutes)<0) || ((hourInterval == (hour-startHour)) && ((minutes-startMinutes)<0))){
            Toast.makeText(WaterReminder.this,"Reminding interval is bigger than the difference between start and end hours!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean isHourIntervalChoosenValid(int hourChosen){
        if(hourChosen > (endHour-startHour)){
            return false;
        }else if((hourChosen == (endHour-startHour)) && ((endMinutes-startMinutes)<0)){
            return false;
        }
        return true;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(isHourIntervalChoosenValid(intervalHours[i])){
            //update the hour interval
            hourInterval = intervalHours[i];
            hourIntervalWarning.setText("");


        }else{
             hourIntervalWarning.setText("Hour Reminder Interval Not Appropriate. Change It Please.");
             hourInterval=intervalHours[i];
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}