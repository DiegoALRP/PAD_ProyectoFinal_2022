package es.ucm.fdi.emtntr.StopArrives;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.Arrival;
import es.ucm.fdi.emtntr.stopSearch.BusStopInfo;

public class ArrivalListAdapter extends RecyclerView.Adapter<ArrivalListAdapter.ArrivalsViewHolder> implements View.OnClickListener {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<Arrival> arrivals;
    private final int MAX_RESULTS = 20;

    public ArrivalListAdapter(Context context, List<Arrival> arrivals, LayoutInflater layoutInflater) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);//layoutInflater;
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
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ArrivalsViewHolder holder, int position) {

        String line_name = "";
        String arrival_times = "";

        line_name = arrivals.get(position).getLine();
        arrival_times = arrivals.get(position).getTimesString();

        holder.line.setText(line_name);
        holder.times.setText(arrival_times);

        String finalLine_name = line_name;
        String finalArrival_times = arrival_times;
        holder.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.switch1.isChecked()) setAlarm(finalLine_name, finalArrival_times);
                else deleteAlarm(finalLine_name);
            }
        });


    }

    @Override
    public int getItemCount() {

        return arrivals.size();
    }

    @Override
    public void onClick(View v) {

    }

    //TODO: poner las alarmas
    public void setAlarm(String line, String time)
    {
        Toast.makeText(context, "Alarma añadida en " + line + ", " + time + " PRUEBA", Toast.LENGTH_SHORT).show();
    }

    public void deleteAlarm(String line)
    {
        Toast.makeText(context, "Alarma eliminada en " + line + " PRUEBA", Toast.LENGTH_SHORT).show();
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
