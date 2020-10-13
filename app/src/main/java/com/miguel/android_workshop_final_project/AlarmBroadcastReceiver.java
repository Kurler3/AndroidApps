package com.miguel.android_workshop_final_project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static int alarmCount;
    private NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        /*AlarmsActivity.notificationChannels.add(new NotificationChannel(String.valueOf(alarmCount),"alarm" + alarmCount
        ,NotificationManager.IMPORTANCE_DEFAULT));

        AlarmsActivity.notificationChannels.get(alarmCount).enableLights(true);
        AlarmsActivity.notificationChannels.get(alarmCount).enableVibration(true);
        AlarmsActivity.notificationChannels.get(alarmCount).setLightColor(R.color.colorPrimary);
        AlarmsActivity.notificationChannels.get(alarmCount).setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);*/

        NotificationChannel notification = new NotificationChannel(String.valueOf(alarmCount),"alarm" + alarmCount
                ,NotificationManager.IMPORTANCE_DEFAULT);

        notification.enableLights(true);
        notification.enableVibration(true);
        notification.setLightColor(R.color.colorPrimary);
        notification.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        CreateNotificationManager(context);
        notificationManager.createNotificationChannel(notification);
        //notificationManager.createNotificationChannel(AlarmsActivity.notificationChannels.get(alarmCount));
        NotificationCompat.Builder notificationBuilder = getChannelNotification(context,String.valueOf(alarmCount),"Alarm");
        notificationManager.notify(alarmCount,notificationBuilder.build());

        alarmCount++;
    }
    private void CreateNotificationManager(Context context){
         if(notificationManager==null){
             notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
         }
    }
    private NotificationCompat.Builder getChannelNotification(Context context,String channelID ,String title){
        return new NotificationCompat.Builder(context, channelID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.alarm);
    }
}
