package com.consul.edu.educationconsultant.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.consul.edu.educationconsultant.activities.NavigationDrawerActivity;
import com.consul.edu.educationconsultant.activities.NewActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.consul.edu.educationconsultant.R;

/**
 * Created by Svetlana on 6/10/2018.
 */

public class MyFCMClass extends FirebaseMessagingService {

    private final String TAG = "EC-FCM";
    private final String CHANNEL_ID = "my_channel_01";


    public MyFCMClass() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Title: " + remoteMessage.getNotification().getTitle());
            Log.e(TAG, "Body: " + remoteMessage.getNotification().getBody());
        }


        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        sendNotification(title, message);

    }

    @Override
    public void onDeletedMessages() {

    }


    private void sendNotification(String title,String messageBody) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NavigationDrawerActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // preserve the user's expected navigation experience after they open your app via the notification
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);  // automatically removes the notification when the user taps it

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // TODO : shared pref idNotif
        notificationManager.notify(NotificationID.getID(), mBuilder.build());



    }







}