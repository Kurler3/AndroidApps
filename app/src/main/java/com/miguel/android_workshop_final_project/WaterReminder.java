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

import java.util.ArrayList;

public class WaterReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {
    public static final String START_HOUR = "startHour";
    public static final String END_HOUR = "endHour";
    public static final String START_MINUTES = "startMinutes";
    public static final String END_MINUTES = "endMinutes";
    public static final String HOUR_INTERVAL = "hourInterval";

    ArrayList<Integer> intervalHoursArray;
    Spinner intervalSpinner;
    TextView intervalStartText, intervalEndText, hourIntervalWarning;
    boolean isSettingStartTime;
    int startHour, startMinutes, endHour, endMinutes, hourInterval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder);

        InstanteateViews();
        HourSelectSpinner();
        LoadData();

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
        intervalHoursArray = new ArrayList<>();

        intervalHoursArray.add(1);
        intervalHoursArray.add(2);
        intervalHoursArray.add(4);
        intervalHoursArray.add(6);
        startHour = 9;
        endHour=20;
        hourInterval = 1;
        startMinutes = 0;
        endMinutes = 0;
        //No need to set the minutes for both to 0 because they are 0 already
        SaveData();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if(isSettingStartTime && IsStartTimeChoosenValid(i,i1)){
            SettingStartTime(i,i1);

            startHour = i;
            startMinutes = i1;
            SaveData();
        }else if(!isSettingStartTime && IsEndTimeChoosenValid(i,i1)){
            SettingEndTime(i,i1);

            endHour = i;
            endMinutes = i1;
            SaveData();
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
        if(isHourIntervalChoosenValid(intervalHoursArray.get(i))){
            //update the hour interval
            hourInterval = intervalHoursArray.get(i);
            hourIntervalWarning.setText("");
        }else{
             hourIntervalWarning.setText("Hour Reminder Interval Not Appropriate. Change It Please.");
             hourInterval=intervalHoursArray.get(i);
        }
        SaveData();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void SaveData(){
        TinyDB tinyDB = new TinyDB(getApplicationContext());

        tinyDB.putInt(START_HOUR,startHour);
        tinyDB.putInt(START_MINUTES,startMinutes);
        tinyDB.putInt(END_HOUR,endHour);
        tinyDB.putInt(END_MINUTES,endMinutes);
        tinyDB.putInt(HOUR_INTERVAL,hourInterval);
    }
    private void LoadData(){
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        //Starting time
        int startHour = tinyDB.getInt(START_HOUR);
        int startMinutes = tinyDB.getInt(START_MINUTES);
        SettingStartTime(startHour,startMinutes);
        //Ending time
        int endHour = tinyDB.getInt(END_HOUR);
        int endMinutes = tinyDB.getInt(END_MINUTES);
        SettingEndTime(endHour,endMinutes);
        //Spinner
        int hourInterval = tinyDB.getInt(HOUR_INTERVAL);
        intervalSpinner.setSelection(intervalHoursArray.indexOf(hourInterval));
    }
}