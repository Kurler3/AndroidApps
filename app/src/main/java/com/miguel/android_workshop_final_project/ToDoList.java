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
    static ArrayList<Boolean> taskCheckedList;
    ImageButton addTaskBtn, deleteTasksBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        InitializeViews();

        CreateArrayLists();

        CreateRecyclerView();

    }
    private void CreateRecyclerView(){
        taskAdapter = new TaskAdapter(this,taskArrayList,taskCheckedList);
        layoutManager = new LinearLayoutManager(this);
        taskView.setAdapter(taskAdapter);
        taskView.setLayoutManager(layoutManager);
    }
    private void CreateArrayLists(){
        taskArrayList = new ArrayList<>();
        taskCheckedList = new ArrayList<>();
    }

    public void AddTask(View view) {
          //launches a sort of pop up that asks for input of name for the new task and adds it to the array and then updates the recycler view
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.show(getSupportFragmentManager(),"Add Task Dialog");

    }
    public void DeleteTasks(View view){
        for(int i=0;i<taskCheckedList.size();i++){
            if (taskCheckedList.get(i)==true){
                taskCheckedList.remove(i);
                taskArrayList.remove(i);
                taskAdapter.notifyItemRemoved(i);
                i--;
            }
        }
    }
    private void InitializeViews(){

        taskView = (RecyclerView) findViewById(R.id.taskList);
        addTaskBtn = (ImageButton) findViewById(R.id.addTaskBtn);
        deleteTasksBtn = (ImageButton) findViewById(R.id.deleteTasksBtn);
    }

    @Override
    public void ApplyTaskName(String newTaskName) {
        taskArrayList.add(0,new Task(newTaskName));
        taskCheckedList.add(0,false);

        taskAdapter.notifyItemInserted(0);
        Toast.makeText(ToDoList.this,"New Task Has Been Added!", Toast.LENGTH_SHORT).show();
    }
    public static void RemoveTask(int position){
        taskArrayList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}