package es.ucm.fdi.emtntr.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.notifications.Notification;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BUS_STOP = "bustop";




    private BusStop busStop;

    private int hour, minute;
    private Button timeButton , button_notification;
    private TextInputEditText number_of_line;
    private List<RadioButton> days;
    private Calendar calendar;
    private  String number_of_line_string;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(BusStop busStop) {

        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUS_STOP, busStop);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            busStop = (BusStop) getArguments().get(BUS_STOP);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root =  inflater.inflate(R.layout.fragment_favourite_configuration, container, false);
        timeButton = root.findViewById(R.id.clock);
        button_notification = root.findViewById(R.id.Add_notification);
        number_of_line = root.findViewById(R.id.number_of_line);
        days = new ArrayList<>();
        days.add(root.findViewById(R.id.radioButton_M));
        days.add(root.findViewById(R.id.radioButton_T));
        days.add(root.findViewById(R.id.radioButton_W));
        days.add(root.findViewById(R.id.radioButton_Th));
        days.add(root.findViewById(R.id.radioButton_F));
        days.add(root.findViewById(R.id.radioButton_S));
        days.add(root.findViewById(R.id.radioButton_Sa));

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(v);
            }
        });

        button_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotification(view);
            }
        });
        return root;
    }


    public void startAlert() {

        int hour = 0;
        int minute = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                hour, minute, 0);
    }


    private void setAlarm(long timeInMillis) {
        Intent intentNotification = new Intent(getActivity(), Notification.class);
        intentNotification.putExtra("BUSSTOP_INFO", busStop.getName());
        intentNotification.putExtra("LINE_NUMBER", number_of_line_string );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getActivity().getApplicationContext() ,0, intentNotification, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, 7*AlarmManager.INTERVAL_DAY, pendingIntent);
        System.out.println("Time:"+((System.currentTimeMillis()-timeInMillis)/1000));

    }

    public void Calendar(View view, TimePicker timePicker){
        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), timePicker.getHour(), timePicker.getMinute(), 0);

    }
    //Timer
    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                Calendar(view,timePicker);
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void addNotification(View view){

       number_of_line_string =  number_of_line.getText().toString();
       if(check_line(number_of_line_string)){

           for(RadioButton day : days){
               if(day.isChecked()){

                   int index = days.indexOf(day);

                   calendar.set(Calendar.DAY_OF_WEEK,(index+2)%7);
                   if(calendar.getTimeInMillis() < System.currentTimeMillis()){
                       calendar.set(Calendar.WEEK_OF_MONTH,calendar.get(Calendar.WEEK_OF_MONTH)+1);
                       System.out.println(calendar.getTime());
                       setAlarm(calendar.getTimeInMillis());
                       calendar.set(Calendar.WEEK_OF_MONTH,calendar.get(Calendar.WEEK_OF_MONTH)-1);
                   }
                   else {
                       System.out.println(calendar.getTime());
                       setAlarm(calendar.getTimeInMillis());
                   }


               }
           }

       }
    }

    public boolean check_line(String number_of_line_function){
       List<String> lines =  busStop.getLines();
       for(String line : lines){
           if(line.equals(number_of_line_function)){
              return true;
           }
       }
       return false;
    }


}