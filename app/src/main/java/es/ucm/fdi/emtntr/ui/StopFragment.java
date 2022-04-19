package es.ucm.fdi.emtntr.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;

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


        txtv1.setText(busStop.getName());
        txtv2.setText("Número: " + busStop.getId());
        return root;
    }
}