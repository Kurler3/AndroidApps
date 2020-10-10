package com.miguel.android_workshop_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity implements AddTaskDialog.AddTaskListener {
    RecyclerView taskView;
    static TaskAdapter taskAdapter;
    RecyclerView.LayoutManager layoutManager;
    static ArrayList<Task> taskArrayList;
    ImageButton addTaskBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        InitializeViews();

        CreateArrayList();

        CreateRecyclerView();

    }
    private void CreateRecyclerView(){
        taskAdapter = new TaskAdapter(this,taskArrayList);
        layoutManager = new LinearLayoutManager(this);
        taskView.setAdapter(taskAdapter);
        taskView.setLayoutManager(layoutManager);
    }
    private void CreateArrayList(){
        taskArrayList = new ArrayList<>();
        taskArrayList.add(new Task("example task1"));
        taskArrayList.add(new Task("example task2"));
        taskArrayList.add(new Task("example task3"));
        taskArrayList.add(new Task("example task4"));
    }

    public void AddTask(View view) {
          //launches a sort of pop up that asks for input of name for the new task and adds it to the array and then updates the recycler view
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.show(getSupportFragmentManager(),"Add Task Dialog");

    }
    private void InitializeViews(){

        taskView = (RecyclerView) findViewById(R.id.taskList);
        addTaskBtn = (ImageButton) findViewById(R.id.addTaskBtn);
    }

    @Override
    public void ApplyTaskName(String newTaskName) {
        taskArrayList.add(0,new Task(newTaskName));

        taskAdapter.notifyItemInserted(0);
        Toast.makeText(ToDoList.this,"New Task Has Been Added!", Toast.LENGTH_SHORT).show();
    }
    public static void RemoveTask(int position){
        taskArrayList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}