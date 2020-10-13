package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import org.joda.time.LocalTime;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    public static final String ALARM_LIST = "alarmList";
    public static final String ALARM_CHECKED_LIST = "alarmCheckedList";

    int requestCode;
    RecyclerView alarmListRecyclerView;
    AlarmAdapter alarmAdapter;
    RecyclerView.LayoutManager layoutManager;
    ImageButton addAlarmBtn;
    public static ArrayList<Alarm> alarmArray;
    public static ArrayList<Boolean> alarmCheckedArray;
    public static ArrayList<NotificationChannel> notificationChannels;
    AlarmTimePicker alarmTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        InstanteateViews();
        CreateAlarmArrayList();
        LoadData();
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

        CreateNewAlarmNotification(i,i1);
        SaveData();

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
    private void SaveData(){
        TinyDB tinyDB = new TinyDB(getApplicationContext());

        ArrayList<Object> alarmObject = new ArrayList<Object>();
        for(Alarm alarm : alarmArray){
            alarmObject.add((Object) alarm);
        }

        tinyDB.putListObject(ALARM_LIST,alarmObject);
        tinyDB.putListBoolean(ALARM_CHECKED_LIST,alarmCheckedArray);

    }
    private void LoadData(){
        TinyDB tinyDB = new TinyDB(getApplicationContext());

        ArrayList<Object> alarmObjects = new ArrayList<Object>();

        alarmObjects = tinyDB.getListObject(ALARM_LIST,Alarm.class);

        for(Object alarm : alarmObjects){
            alarmArray.add((Alarm) alarm);
        }

        alarmCheckedArray = tinyDB.getListBoolean(ALARM_CHECKED_LIST);
    }
    private void CreateNewAlarmNotification(int hour, int minutes){
        Calendar calendar = Calendar.getInstance();
         //calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                24*60*60*1000, pendingIntent);
        requestCode++;
    }
    private void CancelAlarm(){

    }
}