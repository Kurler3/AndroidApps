package com.miguel.android_workshop_final_project;

public class Event {
    String date;
    String title;
    Boolean isChecked;

    public Event(String date, String title) {
        this.date = date;
        this.title = title;
        this.isChecked=false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
