package es.ucm.fdi.emtntr.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.core.app.NotificationCompat;

import es.ucm.fdi.emtntr.R;

public class Notification extends BroadcastReceiver  {

    private final static String CHANNEL_ID = "BUS_EMTNT_NOTIFICATION";
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;

    private Context applicationContext;
    private String bus_stop;
    private String line_number;
    private final static String default_notification_channel_id = "default" ;

    

    private Parcelable createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(applicationContext, default_notification_channel_id ) ;
        builder.setSmallIcon(R.drawable.app_logo);
        builder.setContentTitle("Bus is coming!!!");
        builder.setContentText(bus_stop+" "+line_number);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);




        return builder.build();

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("HEre");
        applicationContext = context;

        line_number =  intent.getStringExtra("LINE_NUMBER");
        bus_stop = intent.getStringExtra("BUSSTOP_INFO");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( default_notification_channel_id , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        assert notificationManager != null;
        notificationManager.notify(id , (android.app.Notification) createNotification()) ;


    }

    
}
