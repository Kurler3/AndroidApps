package com.miguel.android_workshop_final_project;

public class Alarm {
    String time;
    Boolean isOn;

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
}
