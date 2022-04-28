package es.ucm.fdi.emtntr.StopArrives;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


import es.ucm.fdi.emtntr.R;

public class NearBusNotifyer extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String line = intent.getStringExtra("linea");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "BusCerca")
                        .setSmallIcon(R.drawable.app_logo)
                        .setContentTitle("¡El bus está de la línea " + line +" está a punto de pasar!")
                        .setContentText("Autobús a menos de un minuto.")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManager notificationManager = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }
}
