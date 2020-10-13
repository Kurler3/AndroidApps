package com.miguel.android_workshop_final_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Calendar;

public class EventsList extends AppCompatActivity {
    public static final String EVENT_ARRAY = "eventArray";
    public static final String EVENT_CHECKED_ARRAY = "eventCheckedArray";
    public  static final int REQUEST_NEW_EVENT_DATA=2;

    RecyclerView eventListRecyclerView;
    EventAdapter eventAdapter;
    public static ArrayList<Event> eventArrayList;
    public static ArrayList<Boolean> eventCheckedList;
    RecyclerView.LayoutManager layoutManager;
    ImageButton addEventBtn, deleteEventBtn;
    public static int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        InitializeViews();
        CreateEventArrayList();
        LoadData();
        CreateRecyclerView();

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivityForResult(new Intent(EventsList.this,AddEventActivity.class),REQUEST_NEW_EVENT_DATA);
            }
        });
        deleteEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteCheckedEvents();
            }
        });
    }
    private void InitializeViews(){
        addEventBtn = findViewById(R.id.addEventBtn);
        deleteEventBtn = findViewById(R.id.deleteEventBtn);
        eventListRecyclerView = findViewById(R.id.eventListRecyclerView);
    }
    private void CreateEventArrayList(){
        eventArrayList = new ArrayList<>();
        eventCheckedList = new ArrayList<>();
    }
    private void CreateRecyclerView(){
        eventAdapter = new EventAdapter(this,eventArrayList);
        layoutManager = new LinearLayoutManager(this);

        eventListRecyclerView.setAdapter(eventAdapter);
        eventListRecyclerView.setLayoutManager(layoutManager);
    }
    private void DeleteCheckedEvents(){
        for(int i=0;i<eventCheckedList.size();i++){
            if (eventCheckedList.get(i)){
                eventArrayList.remove(i);
                eventCheckedList.remove(i);
                eventAdapter.notifyItemRemoved(i);
                i--;
                SaveData();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_NEW_EVENT_DATA && resultCode==RESULT_OK){
              String date = data.getStringExtra("date");
              String title = data.getStringExtra("title");

              CreateNotification();
              AddEventWithData(date,title);
        }
    }
    private void AddEventWithData(String date, String title){
           eventArrayList.add(new Event(date,title));
           eventCheckedList.add(false);
           eventAdapter.notifyItemInserted(eventArrayList.size()-1);
           SaveData();
    }
    private void SaveData(){
        TinyDB tinyDB = new TinyDB(getApplicationContext());

        ArrayList<Object> eventObject = new ArrayList<Object>();
        for(Event event : eventArrayList){
            eventObject.add((Object) event);
        }

        tinyDB.putListObject(EVENT_ARRAY,eventObject);
        tinyDB.putListBoolean(EVENT_CHECKED_ARRAY,eventCheckedList);

    }
    private void LoadData(){
        TinyDB tinyDB = new TinyDB(getApplicationContext());

        ArrayList<Object> eventObjects = new ArrayList<Object>();

        eventObjects = tinyDB.getListObject(EVENT_ARRAY,Event.class);

        for(Object event : eventObjects){
            eventArrayList.add((Event) event);
        }

        eventCheckedList = tinyDB.getListBoolean(EVENT_CHECKED_ARRAY);
    }
    private void CreateNotification(){
        int yearNotify = year;
        int monthNotify = month;
        int dayNotify = day;

        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);
        int curMonth = cal.get(Calendar.MONTH);
        int curDay = cal.get(Calendar.DAY_OF_MONTH);



    }
}