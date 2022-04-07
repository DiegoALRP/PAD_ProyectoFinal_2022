package es.ucm.fdi.emtntr.notifications;

//TODO remember to declare the "Alarms & reminders" special app access

public class BusAlarm {

    public BusAlarm() {
    }

    /*public void startAlert(Context context) {

        EditText text = findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set:: " + i + " seconds", Toast.LENGTH_LONG).show();
    }*/
}
