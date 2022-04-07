package es.ucm.fdi.emtntr.favorites;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Locale;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.notifications.BusAlarm;
import es.ucm.fdi.emtntr.notifications.Notification;

public class ActivityFavorite extends AppCompatActivity {

    private BusStop busStop;
    private int busID;

    private int hour, minute;
    private Button timeButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_favorite_one);
    }

    private void buttonPressed() {

        startAlert();
    }


    public void startAlert() {

        int hour = 0;
        int minute = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                hour, minute, 0);
    }

    private void setAlarm(long timeInMillis) {

        Gson gson = new Gson();
        String busStopInfo = gson.toJson(busStop);

        Intent intentNotification = new Intent(this, Notification.class);
        intentNotification.putExtra("BUSSTOP_INFO", busStopInfo);
        intentNotification.putExtra("BUSSTOP_ID", busID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intentNotification, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, 7*AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    /*
    val calendar: Calendar = Calendar.getInstance()
         if (Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), timePicker.hour, timePicker.minute, 0)
         }
         else {
            calendar.set(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            timePicker.currentHour,
            timePicker.currentMinute, 0)
         }
         setAlarm(calendar.timeInMillis)
      }
   }
   private fun setAlarm(timeInMillis: Long) {
      val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
      val intent = Intent(this, MyAlarm::class.java)
      val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
      alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
      Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
   }
   private class MyAlarm : BroadcastReceiver() {
      override fun onReceive(context: Context, intent: Intent) {
         Log.d("Alarm Bell", "Alarm just fired")
      }
   }
}
     */

}
