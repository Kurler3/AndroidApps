package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity {
    RecyclerView taskView;
    TaskAdapter taskAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Task> taskArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        InitializeViews();
        taskArrayList = new ArrayList<>();

        taskArrayList.add(new Task("example task1"));
        taskArrayList.add(new Task("example task2"));
        taskArrayList.add(new Task("example task3"));
        taskArrayList.add(new Task("example task4"));
        taskArrayList.add(new Task("example task5"));
        taskArrayList.add(new Task("example task6"));
        taskArrayList.add(new Task("example task7"));
        taskArrayList.add(new Task("example task8"));
        taskArrayList.add(new Task("example task9"));
        taskArrayList.add(new Task("example task10"));

        taskAdapter = new TaskAdapter(this,taskArrayList);
        layoutManager = new LinearLayoutManager(this);

        taskView.setAdapter(taskAdapter);
        taskView.setLayoutManager(layoutManager);

    }

    public void AddTask(View view) {

    }
    private void InitializeViews(){
        taskView = (RecyclerView) findViewById(R.id.taskList);
    }
}