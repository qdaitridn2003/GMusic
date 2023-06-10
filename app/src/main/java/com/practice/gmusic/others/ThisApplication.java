package com.practice.gmusic.others;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.StrictMode;

import com.practice.gmusic.BuildConfig;

public class ThisApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        channelNotification();
    }

    private void channelNotification(){
        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(Const.channelId,Const.appName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null,null);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
