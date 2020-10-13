package com.miguel.android_workshop_final_project;

import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends NotificationCompat.Builder {
    public NotificationHelper(@NonNull Context context, @NonNull String channelId, String title) {
        super(context, channelId);

        this.setSmallIcon(R.drawable.alarm);
        this.setContentTitle(title);

    }
}
