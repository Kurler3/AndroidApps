package com.miguel.android_workshop_final_project;

import android.content.Context;
import android.content.ContextWrapper;

public class AlarmNotificationHelper extends ContextWrapper {

    public AlarmNotificationHelper(Context base) {
        super(base);
    }
}
