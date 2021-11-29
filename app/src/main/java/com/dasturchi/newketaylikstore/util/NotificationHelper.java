package com.dasturchi.newketaylikstore.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import com.dasturchi.newketaylikstore.R;
import com.dasturchi.newketaylikstore.ui.main.HomeActivity;

public class NotificationHelper {
    public static void displayNotification(Context context,String title, String text){

        final String CHANNEL_ID = "ketaylik_chanel_id";

        Intent resultIntent = new Intent(context, HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.delivery_boy2)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,mBuilder.build());

    }
}
