package com.example.baselib.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;


import com.example.baselib.R;

import java.util.Arrays;

import androidx.annotation.RequiresApi;

public class NotificationChannels {

    public final static String CRITICAL = "critical";
    public final static String IMPORTANCE = "importance";
    public final static String DEFAULT = "default";
    public final static String LOW = "low";
    public final static String MEDIA = "media";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createAllNotificationChannels(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null)//为空就返回
            return;

        NotificationChannel mediaChannel = new NotificationChannel(
                MEDIA,context.getString(R.string.channel_media),
                NotificationManager.IMPORTANCE_DEFAULT);
        mediaChannel.setSound(null, null);//无声
        mediaChannel.setVibrationPattern(null);//无震动
        notificationManager.createNotificationChannels(Arrays.asList(
                new NotificationChannel(
                        CRITICAL,
                        context.getString(R.string.channel_critical),
                        NotificationManager.IMPORTANCE_HIGH),
                new NotificationChannel(
                        IMPORTANCE,
                        context.getString(R.string.channel_importance),
                        NotificationManager.IMPORTANCE_DEFAULT),
                new NotificationChannel(
                        DEFAULT,
                        context.getString(R.string.channel_default),
                        NotificationManager.IMPORTANCE_LOW),
                new NotificationChannel(
                        LOW,
                        context.getString(R.string.channel_low),
                        NotificationManager.IMPORTANCE_MIN),
                mediaChannel
        ));

    }

}
