package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import org.joda.time.LocalTime;

import java.sql.Time;
import java.util.ArrayList;

public class AlarmsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    RecyclerView alarmListRecyclerView;
    AlarmAdapter alarmAdapter;
    RecyclerView.LayoutManager layoutManager;
    ImageButton addAlarmBtn;
    public static ArrayList<Alarm> alarmArray;
    public static ArrayList<Boolean> alarmCheckedArray;
    AlarmTimePicker alarmTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        InstanteateViews();
        CreateAlarmArrayList();
        CreatingRecyclerView();


        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 alarmTimePicker = new AlarmTimePicker();
                 alarmTimePicker.show(getSupportFragmentManager(),"Pick A Time For New Alarm");
            }
        });
    }
    private void InstanteateViews(){
        alarmListRecyclerView = (RecyclerView) findViewById(R.id.alarmsListRecyclerView);
        addAlarmBtn = (ImageButton) findViewById(R.id.addAlarmBtn);
    }
    private void CreateAlarmArrayList(){
        alarmArray = new ArrayList<>();
        alarmCheckedArray = new ArrayList<>();

    }
    private void CreatingRecyclerView(){
        alarmAdapter = new AlarmAdapter(this,alarmArray);
        layoutManager = new LinearLayoutManager(this);

        alarmListRecyclerView.setAdapter(alarmAdapter);
        alarmListRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) { //After picking the time, this method gets called back
        int positionToAdd = GetIdealPositionToAdd(i,i1);

        String newAlarmTime = FormatAlarmTime(i,i1);

        Alarm alarmToAdd = new Alarm(newAlarmTime,true);
        alarmToAdd.setHour(i);
        alarmToAdd.setMinute(i1);

        alarmArray.add(positionToAdd,new Alarm(newAlarmTime,true));
        alarmCheckedArray.add(positionToAdd,true);

        alarmAdapter.notifyItemInserted(positionToAdd);
    }
    private String FormatAlarmTime(int hour, int minute){
        String timeToFormat;
        if(hour<10 && minute<10){
            timeToFormat = ("0" + hour + ":" + "0" + minute);
        }else if(hour<10){
            timeToFormat ="0" + hour + ":" + minute;
        }else if(minute<10){
            timeToFormat = hour + ":" + "0" + minute;
        }else{
            timeToFormat = hour + ":" + minute;
        }
        return timeToFormat;
    }
    private int GetIdealPositionToAdd(int hour, int minute){
        int i = 0;
        for(int j=0;j<alarmArray.size();j++){
            if(alarmArray.get(j).getHour()<hour){
                i++;
            }else if(alarmArray.get(j).getHour()==hour){
                if(alarmArray.get(j).getMinute()<minute){
                    i++;
                }
            }else if(alarmArray.get(j).getHour()>hour){
                return i;
            }
        }
        return i;
    }

}