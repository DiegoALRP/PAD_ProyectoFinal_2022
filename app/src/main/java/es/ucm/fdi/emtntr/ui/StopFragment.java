package es.ucm.fdi.emtntr.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.StopArrives.ArrivalListAdapter;
import es.ucm.fdi.emtntr.StopArrives.AsyncAdaptableLoader;
import es.ucm.fdi.emtntr.model.Arrival;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.stopSearch.BusStopResultListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopFragment extends Fragment {

    private static final String ARG_BUS_STOP = "param1";
    private static final int UPDATE_TIME = 30000; //miliseconds
    private BusStop busStop;

    private TextView txtv1;
    private TextView txtv2;
    private ToggleButton toggleButton;
    private RecyclerView recyclerView_arrivals;
    private ArrivalListAdapter arrivalListAdapter;
    private ArrivalsLoaderCallBacks arrivalsLoaderCallBacks;

    private int id = 0;


    public StopFragment() {
        // Required empty public constructor
    }

    public static StopFragment newInstance(BusStop busStop) {
        StopFragment fragment = new StopFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BUS_STOP, busStop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.busStop = (BusStop) getArguments().get(ARG_BUS_STOP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_stop, container, false);
        txtv1 = root.findViewById(R.id.txtv1);
        txtv2 = root.findViewById(R.id.txtv2);
        toggleButton = (ToggleButton) root.findViewById(R.id.toggleButton);

        txtv1.setText(busStop.getName());
        txtv2.setText("Número: " + busStop.getId());

        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_empty));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star));
                else toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_empty));
            }
        });

        recyclerView_arrivals = root.findViewById(R.id.arrivals_recyclerView);

        arrivalsLoaderCallBacks = new StopFragment.ArrivalsLoaderCallBacks(getContext());

        arrivalListAdapter = new ArrivalListAdapter(root.getContext(), new ArrayList<Arrival>(), inflater);
        recyclerView_arrivals.setAdapter(arrivalListAdapter);
        recyclerView_arrivals.setLayoutManager(new LinearLayoutManager(root.getContext()));

        LoaderManager.getInstance(this).restartLoader(0, new Bundle(), arrivalsLoaderCallBacks);

        //Codigo de actualizacion cada 15 segundos
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actualizar();
                    }
                }) ;
            }
        };

        new Timer().scheduleAtFixedRate(timerTask, UPDATE_TIME, UPDATE_TIME);
        return root;
    }

    public void actualizar()
    {
        LoaderManager.getInstance(this).restartLoader(id, new Bundle(), arrivalsLoaderCallBacks);
        id++;
    }

    public void mostrarLineas(List<Arrival> data)
    {
        //Agrupo los Arrival por línea

        Map<String, Arrival> hashMap = new HashMap<>();
        String lineAux="";
        if(data != null){
            for(Arrival a: data)
            {
                //Solo incluye el arrival si es mayor que 0
                if(a.getTime()>0) {
                    //Si ya hay llegada para la línea, le añade la segunda
                    if (hashMap.containsKey(a.getLine())) hashMap.get(a.getLine()).setTime2(a.getTime());
                    else hashMap.put(a.getLine(), a);
                }
            }
            data = new ArrayList<>(hashMap.values());
        }
        if (hashMap.isEmpty()) data = null;

        arrivalListAdapter.setData(data);
        arrivalListAdapter.notifyDataSetChanged();
    }

    public class ArrivalsLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<Arrival>> {

        Context context;
        public ArrivalsLoaderCallBacks(Context context) {
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public AsyncAdaptableLoader<List<Arrival>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            String[] loaderArgs = new String[]{String.valueOf(busStop.getId())};
            AsyncAdaptableLoader<List<Arrival>> arrivalsLoader =
                    new AsyncAdaptableLoader<List<Arrival>>(context, loaderArgs, AsyncAdaptableLoader.LoaderSelector.ARRIVE_TIMES);

            return arrivalsLoader;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<Arrival>> loader, List<Arrival> data) {

            mostrarLineas(data);
        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<Arrival>> loader) {

        }

    }
}