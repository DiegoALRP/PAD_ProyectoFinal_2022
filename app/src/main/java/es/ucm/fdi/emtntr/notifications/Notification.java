package es.ucm.fdi.emtntr.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import es.ucm.fdi.emtntr.model.Bus;
import es.ucm.fdi.emtntr.model.BusStop;

public class Notification extends BroadcastReceiver {

    private final static String CHANNEL_ID = "BUS_EMTNT_NOTIFICATION";

    private Context applicationContext;
    private BusStop bus;
    private int busID;

    public Notification(BusStop bus, int busID, Context applicationContext) {

        this.bus = bus;
        this.busID = busID;
        this.applicationContext = applicationContext;
    }

    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(applicationContext, CHANNEL_ID);
        //TODO
        //builder.setSmallIcon(icon)
        builder.setContentTitle("Bus is coming!!!");
        builder.setContentText(bus.getBusesID(busID)+" "+bus.getDirectionName());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        busID =  intent.getIntExtra("BUS_ID", 0);
        String busStopInfo = intent.getStringExtra("BusStopInfo");

        Gson gson = new Gson();
        this.bus = gson.fromJson(busStopInfo, BusStop.class);

        createNotification();
    }
}
