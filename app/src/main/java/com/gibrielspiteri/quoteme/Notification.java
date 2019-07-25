package com.gibrielspiteri.quoteme;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification extends Application {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel Channel1 = new NotificationChannel(CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            Channel1.setDescription("This is Channel 1");

            NotificationChannel Channel2 = new NotificationChannel(CHANNEL_1_ID, "Channel 2", NotificationManager.IMPORTANCE_LOW);
            Channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(Channel1);
            manager.createNotificationChannel(Channel2);
        }
    }
}
