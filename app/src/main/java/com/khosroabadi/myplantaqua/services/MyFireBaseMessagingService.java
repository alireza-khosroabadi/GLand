package com.khosroabadi.myplantaqua.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.khosroabadi.myplantaqua.R;
import com.khosroabadi.myplantaqua.activity.SplashActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Alireza on 1/21/2017.
 */

public class MyFireBaseMessagingService extends FirebaseMessagingService {
    private static final int MY_NOTIFICATION_ID=1;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("NOTIFIIIICATION", "From: " + remoteMessage.getFrom());
        Log.d("NOTIFIIIICATION", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        try {
            showNotification(URLDecoder.decode(remoteMessage.getNotification().getTitle(),"UTF-8"), URLDecoder.decode(remoteMessage.getNotification().getBody(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void showNotification(String title , String message){
        //Creating a notification

        Intent splashIntent = new Intent(this , SplashActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        splashIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Uri notificationSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setSound(notificationSound)
                //.setStyle(new NotificationCompat.BigPictureStyle())
        //Vibration
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })

        //LED
                .setLights(Color.GREEN, 3000, 3000);


        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(MY_NOTIFICATION_ID , mBuilder.build());
    }

}
