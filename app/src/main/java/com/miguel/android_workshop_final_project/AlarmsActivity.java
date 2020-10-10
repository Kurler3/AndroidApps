package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.ArrayList;

public class AlarmsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    RecyclerView alarmListRecyclerView;
    AlarmAdapter alarmAdapter;
    RecyclerView.LayoutManager layoutManager;
    ImageButton addAlarmBtn;
    ArrayList<Alarm> alarmArray;
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
    }
    private void CreatingRecyclerView(){
        alarmAdapter = new AlarmAdapter(this,alarmArray);
        layoutManager = new LinearLayoutManager(this);

        alarmListRecyclerView.setAdapter(alarmAdapter);
        alarmListRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) { //After picking the time, this method gets called back
        String newAlarmTime = i + ":" + i1;
        alarmArray.add(new Alarm(newAlarmTime,true));
        alarmAdapter.notifyItemInserted(alarmArray.size()-1);

    }
}