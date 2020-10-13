package com.miguel.android_workshop_final_project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    public static int channelID;
    public static int notificationID;
    private NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        String channelIDToString = String.valueOf(channelID);
        NotificationChannel notificationChannel = new NotificationChannel(channelIDToString,"Channel" + channelIDToString,NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(R.color.colorPrimary);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        notificationManager = getNotificationManager(context);
        notificationManager.createNotificationChannel(notificationChannel);
        NotificationHelper notificationHelper = new NotificationHelper(context,channelIDToString,"Alarm");

        notificationManager.notify(notificationID,notificationHelper.build());
        channelID++;
        notificationID++;
    }
    private NotificationManager getNotificationManager(Context context){
        if(notificationManager==null){
            NotificationManager notiManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            return notiManager;
        }
        return notificationManager;
    }
}
