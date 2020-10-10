package com.miguel.android_workshop_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
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
        ImageButton taskOptions;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameView = itemView.findViewById(R.id.taskName);
            taskOptions = itemView.findViewById(R.id.taskOptionsImageBtn);

            taskOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     ShowPopUpTaskOptions(view);
                }
            });
        }
        private void ShowPopUpTaskOptions(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
            popupMenu.inflate(R.menu.task_options_image_button);

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.editTaskOption:
                            //lets user edit the task name
                            return true;
                        case R.id.deleteTaskOption:
                            //find a way to get the task position
                            // ToDoList.RemoveTask();

                            return true;
                        default:
                            return true;
                    }
                }
            });

            popupMenu.show();
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
