package com.superlamer.wearnotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Notification Title")
                .setContentText("Hi Rado, how is it going?")
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(0, notificationBuilder.build());

        // Enables Always-on
        setAmbientEnabled();
    }
}
