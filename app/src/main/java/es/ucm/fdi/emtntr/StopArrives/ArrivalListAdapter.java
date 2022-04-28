package es.ucm.fdi.emtntr.StopArrives;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.Arrival;

public class ArrivalListAdapter extends RecyclerView.Adapter<ArrivalListAdapter.ArrivalsViewHolder> implements View.OnClickListener {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<Arrival> arrivals;

    private NotificationChannel notificationChannel;
    private NotificationManager notificationManager;
    private AlarmManager alarmManager;

    private HashMap<String, PendingIntent> alarmMap;


    private final int MAX_RESULTS = 20;
    private final int NEXT_BUS_TIME = 30; //segundos a los que te avisa del bus
    private static final String CHANNEL_ID = "BusCerca";

    private int notification_id;

    public ArrivalListAdapter(Context context, List<Arrival> arrivals, LayoutInflater layoutInflater) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);//layoutInflater;
        this.notification_id = 0;

        this.alarmMap = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "Notificación parada",
                    NotificationManager.IMPORTANCE_HIGH);
        }

        notificationManager = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        setData(arrivals);
    }

    @NonNull
    @NotNull
    @Override
    public ArrivalListAdapter.ArrivalsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.layout_for_card_view_arrivals, parent, false);

        return new ArrivalsViewHolder(itemView, this);
    }

    public void  setData(List<Arrival> arrivals)
    {
        if (arrivals != null) this.arrivals = new ArrayList<>(arrivals);
        else {
            Toast.makeText(context, "En este momento no hay datos para la parada", Toast.LENGTH_SHORT).show();
            this.arrivals = new ArrayList<>();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ArrivalsViewHolder holder, int position) {

        String line_name = "";
        String arrival_times = "";

        line_name = arrivals.get(position).getLine();
        int time1, time2;
        time1 = arrivals.get(position).getTime();
        time2 = arrivals.get(position).getTime2();
        arrival_times = String.valueOf(time1/60) + "min";
        if(time2 >=0)  arrival_times = arrival_times + ", " + String.valueOf(time2/60) + "min";

        holder.line.setText(line_name);
        holder.times.setText(arrival_times);

        String finalLine_name = line_name;
        int finalTime1 = time1;
        int finalTime2 = time2;
        holder.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.switch1.isChecked()) setAlarm(finalLine_name, finalTime1, finalTime2);
                else deleteAlarm(finalLine_name);
            }
        });
        if(alarmMap.containsKey(position)) holder.switch1.setChecked(true);


    }

    @Override
    public int getItemCount() {

        return arrivals.size();
    }

    @Override
    public void onClick(View v) {

    }

    public void setAlarm(String line, int time1, int time2)
    {

        int alarm_time;
        if(time1 > NEXT_BUS_TIME) alarm_time = time1;
        else alarm_time = time2;
        alarm_time = alarm_time-30;


        Intent notificationIntent = new Intent(context, NearBusNotifyer.class);
        notificationIntent.putExtra("line", line);

        PendingIntent notificationPendingIntent =
                PendingIntent.getBroadcast(context, notification_id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmMap.put(line, notificationPendingIntent);
        notification_id++;

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                System.currentTimeMillis() + alarm_time * 1000,
                notificationPendingIntent);



        String alarmString;
        if (alarm_time > 60) alarmString = String.valueOf(alarm_time/60) + " min";
        else alarmString = String.valueOf(alarm_time) + " seg";

        Toast.makeText(context, "Alarma añadida en " + line + ", " + alarmString, Toast.LENGTH_SHORT).show();
    }


    public void deleteAlarm(String line)
    {
        if(alarmMap.containsKey(line)) {
            alarmManager.cancel(alarmMap.get(line));
            alarmMap.remove(line);
        }
        Toast.makeText(context, "Alarma eliminada en " + line, Toast.LENGTH_SHORT).show();
    }


    class ArrivalsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView line;
        private TextView times;
        private SwitchCompat switch1;
        private ArrivalListAdapter adapter;

        public ArrivalsViewHolder(View itemView, ArrivalListAdapter adapter) {
            super(itemView);

            line = itemView.findViewById(R.id.txtv_line);
            times = itemView.findViewById(R.id.txtv_times);
            switch1 = (SwitchCompat) itemView.findViewById(R.id.switch1);

            this.adapter = adapter;
        }


        @Override
        public void onClick(View v) {

        }
    }
}
