package com.psl.fantasy.league.revamp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.activity.SplashActivity;
import com.psl.fantasy.league.revamp.activity.StartActivity;

import java.util.Map;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try{
            //Log data to Log Cat
            Log.e(TAG, "From: " + remoteMessage.getFrom());
            Log.e(TAG, "Notification Message Title: " + remoteMessage.getNotification().getTitle());
            Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            //create notification
            createNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification(). getBody());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createNotification(String title, String messageBody) {
        Intent intent = new Intent( this , StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(resultIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLights(Color.GREEN,100,100)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotificationBuilder.build());
    }



    private void sendNotification(String messageTitle, String messageBody) {

        Intent intent = new Intent( this , StartActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody).setContentIntent(pendingIntent).setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                .setPriority(Notification.PRIORITY_MAX); //Important for heads-up notification

        Notification buildNotification = mBuilder.build();

        int notifyId = (int) System.currentTimeMillis(); //For each push the older one will not be replaced for this unique id

// Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String name = messageTitle;
            String description = messageBody;
            int importance = NotificationManager.IMPORTANCE_HIGH; //Important   for heads-up notification

            NotificationChannel channel = new NotificationChannel("0",name,importance);
            channel.setDescription(description);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(channel);
                notificationManager.notify(notifyId, buildNotification);
            }
        }else{
            NotificationManager mNotifyMgr =   (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            if (mNotifyMgr != null) {
                mNotifyMgr.notify(notifyId, buildNotification);
            }
        }
    }
}