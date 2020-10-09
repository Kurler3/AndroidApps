package com.miguel.android_workshop_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;

public class TaskAdapter  extends Adapter<TaskAdapter.TaskViewHolder> {
    Context ctx;
    ArrayList<Task> taskArrayList;

    public TaskAdapter(Context ctx, ArrayList<Task> taskArrayList){
        this.ctx=ctx;
        this.taskArrayList=taskArrayList;
    }
    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskNameView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameView = itemView.findViewById(R.id.taskName);
        }
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View v = layoutInflater.inflate(R.layout.example_task_item, parent,false);
        return new TaskViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = taskArrayList.get(position);
        holder.taskNameView.setText(currentTask.getTaskName());
    }
    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
}
