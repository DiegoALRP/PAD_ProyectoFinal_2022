package es.ucm.fdi.emtntr.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.StopArrives.ArrivalListAdapter;
import es.ucm.fdi.emtntr.StopArrives.AsyncAdaptableLoader;
import es.ucm.fdi.emtntr.internalStorage.WriteIE;
import es.ucm.fdi.emtntr.model.Arrival;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.stopSearch.BusStopLoader;
import es.ucm.fdi.emtntr.stopSearch.BusStopResultListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopFragment extends Fragment {

    private static final String ARG_BUS_STOP = "param1";
    private BusStop busStop;

    private TextView txtv1;
    private TextView txtv2;
    private ToggleButton toggleButton;
    private RecyclerView recyclerView_arrivals;
    private ArrivalListAdapter arrivalListAdapter;
    private ArrivalsLoaderCallBacks arrivalsLoaderCallBacks;
    private boolean ok_toAdd;

    public StopFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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

        ok_toAdd = false;
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.star_empty));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {

                    if (ok_toAdd) addBusStopToFavourites();
                    else ok_toAdd = true;
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.star_full));
                }
                else {

                    deleteBusStopFromFavourites();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.star_empty));
                }
            }
        });
        isBusStopInFavorite();

        recyclerView_arrivals = root.findViewById(R.id.arrivals_recyclerView);

        arrivalsLoaderCallBacks = new StopFragment.ArrivalsLoaderCallBacks(getContext());

        arrivalListAdapter = new ArrivalListAdapter(root.getContext(), new ArrayList<Arrival>(), inflater);
        recyclerView_arrivals.setAdapter(arrivalListAdapter);
        recyclerView_arrivals.setLayoutManager(new LinearLayoutManager(root.getContext()));

        LoaderManager.getInstance(this).restartLoader(0, new Bundle(), arrivalsLoaderCallBacks);


        return root;
    }

    public void mostrarLineas(List<Arrival> data)
    {
        //Agrupo los Arrival por línea

        Map<String, Arrival> hashMap = new HashMap<>();
        String lineAux="";
        for(Arrival a: data)
        {
            if(hashMap.containsKey(a.getLine())) hashMap.get(a.getLine()).putTimesString(a.getTime());
            else {
                a.putTimesString(a.getTime());
                hashMap.put(a.getLine(), a);
            }
        }
        data = new ArrayList<>(hashMap.values());

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

    //Favourites Section
    private void addBusStopToFavourites() {

        if (ok_toAdd) {
            Resources resources = getContext().getResources();
            String st = resources.getString(R.string.favourite_alert_favouriteAdded);
            Toast.makeText(getContext(), st, Toast.LENGTH_LONG).show();
        }

        LinesLoaderCallBacks linesLoaderCallBacks = new LinesLoaderCallBacks(getContext());

        LoaderManager.getInstance(this).restartLoader(1, new Bundle(), linesLoaderCallBacks);
    }

    private void addLinesInfo(ArrayList<BusStop> data) {

        WriteIE writeIE = new WriteIE();
        writeIE.addAndWriteBusStopToFavourites(getActivity().getApplicationContext(), data);
    }

    private void deleteBusStopFromFavourites() {

        Resources resources = getContext().getResources();
        String st = resources.getString(R.string.favourite_alert_favouriteDeleted);
        Toast.makeText(getContext(), st, Toast.LENGTH_LONG).show();

        WriteIE writeIE = new WriteIE();
        writeIE.deleteBusStop(getActivity().getApplicationContext(), busStop.getId());
    }

    private void isBusStopInFavorite() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://pad-proyectofinal-1-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getUid() != null) {
            databaseReference.child(mAuth.getUid()).child("Favourites").child(busStop.getId())
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {

                    ok_toAdd = true;
                    if (task.isSuccessful()) {

                        String st = String.valueOf(task.getResult().getValue());

                        if (st.equals("null")) {

                            StopFragment.this.toggleButton.setChecked(false);
                        }
                        else {
                            StopFragment.this.toggleButton.setChecked(true);
                        }
                    }
                }
            });
        }
    }

    public class LinesLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<BusStop>> {

        Context context;
        public LinesLoaderCallBacks(Context context) {
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public BusStopLoader onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            BusStopLoader busStopLoader = new BusStopLoader(context, busStop.getId(), BusStopLoader.Operation.BUS_STOP_INFO);

            return busStopLoader;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BusStop>> loader, List<BusStop> data) {

            addLinesInfo((ArrayList<BusStop>) data);
        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<BusStop>> loader) {

        }

    }
}