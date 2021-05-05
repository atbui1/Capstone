package com.example.democ.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.democ.R;
import com.example.democ.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            showNotificationExchange(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
            return;
        }
        showNotificationExchange(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
    }

    @Override
    public void onNewToken(@NonNull String s) {
        updateToken(s);
        super.onNewToken(s);
    }

    private void showNotificationExchange(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, getNotificationId(), intent,PendingIntent.FLAG_ONE_SHOT);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logov);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.notifications)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setColor(getResources().getColor(R.color.sick_green))
                .setSound(sound)
                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                .build();

//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
//        notificationManagerCompat.notify(getNotificationId(), notification);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(getNotificationId(), notification);
        }
    }

    private void showNotificationExchange(String body) {
        showNotificationExchange(body);
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }

    private void updateToken(String token) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                        System.out.println("MyFirebaseMessagingService");
                        System.out.println("token device: " + token);
                        System.out.println("MyFirebaseMessagingService");
                        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

                    }
                });
    }
}
