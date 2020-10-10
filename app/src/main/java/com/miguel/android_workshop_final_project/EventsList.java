package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class EventsList extends AppCompatActivity {
    RecyclerView eventListRecyclerView;
    EventAdapter eventAdapter;
    public static ArrayList<Event> eventArrayList;
    RecyclerView.LayoutManager layoutManager;
    ImageButton addEventBtn, deleteEventBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        InitializeViews();
        CreateEventArrayList();
        CreateRecyclerView();

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        eventArrayList.add(new Event("20/11/2020","fucking october",false));
        eventArrayList.add(new Event("20/12/2020","fucking december",false));
    }
    private void CreateRecyclerView(){
        eventAdapter = new EventAdapter(this,eventArrayList);
        layoutManager = new LinearLayoutManager(this);

        eventListRecyclerView.setAdapter(eventAdapter);
        eventListRecyclerView.setLayoutManager(layoutManager);
    }
    private void DeleteCheckedEvents(){
        for(int i=0;i<eventArrayList.size();i++){
            if (eventArrayList.get(i).getChecked()){
                eventArrayList.remove(i);
                eventAdapter.notifyItemRemoved(i);
                i--;
            }
        }
    }
}