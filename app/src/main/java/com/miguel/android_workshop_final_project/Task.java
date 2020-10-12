package com.miguel.android_workshop_final_project;

public class Task {
    String taskName;
    boolean isChecked;

    public Task(String taskName) {
        this.taskName = taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskName() {
        return taskName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
