package com.miguel.android_workshop_final_project;

public class Alarm {
    String time;
    Boolean isOn;
    int hour;
    int minute;

    public Alarm(String time, Boolean isOn) {
        this.time = time;
        this.isOn = isOn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getOn() {
        return isOn;
    }

    public void setOn(Boolean on) {
        isOn = on;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
