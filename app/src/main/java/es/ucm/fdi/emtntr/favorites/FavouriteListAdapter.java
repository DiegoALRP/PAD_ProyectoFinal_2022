package es.ucm.fdi.emtntr.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.stopSearch.BusStopResultListAdapter;
import es.ucm.fdi.emtntr.ui.FavouriteFragment;
import es.ucm.fdi.emtntr.ui.StopFragment;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.BusStopViewHolder> implements View.OnClickListener {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<FavouriteBusInfo> busStopList;
    private ArrayList<FavouriteBusInfo> busStopListFull;
    private FragmentManager parentFragment;

    public FavouriteListAdapter(Context context, List<FavouriteBusInfo> busStopList, LayoutInflater layoutInflater, FragmentManager parentFragment) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);//layoutInflater;
        this.parentFragment = parentFragment;
        setBusStopData((ArrayList<FavouriteBusInfo>) busStopList);
    }

    public void setBusStopData(ArrayList<FavouriteBusInfo> busStopList) {

        this.busStopList = busStopList;
        this.busStopListFull = new ArrayList<>(busStopList);
    }

    @NonNull
    @NotNull
    @Override
    public BusStopViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.layout_for_card_view_bus_stops_favourites, parent, false);

        return new FavouriteListAdapter.BusStopViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BusStopViewHolder holder, int position) {

        String busStopName_user = "";
        String busStopName = "";
        String busStopID = "";
        String busStopNumber = "N. ";
        String busStopLines = "aa";

        busStopName_user = busStopList.get(position).getUser_stopBusName();
        if (busStopName_user.equals("none")) {
            busStopName_user = busStopList.get(position).getStopBusName();
        }

        busStopName = busStopList.get(position).getStopBusName();
        busStopID = busStopNumber + busStopList.get(position).getStopBusId();
        busStopLines = busStopList.get(position).getBusLines();

        holder.busStopName_user.setText(busStopName_user);
        holder.busStopName.setText(busStopName);
        holder.busStopID.setText(busStopID);
        holder.busStopLines.setText(busStopLines);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = FavouriteListAdapter.this.parentFragment;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);

                String name = busStopList.get(position).getStopBusName();
                String id = busStopList.get(position).getStopBusId();
                String[] linesAux = busStopList.get(position).getBusLines().split(", ");
                ArrayList<String> lines = new ArrayList<String>(Arrays.asList(linesAux));
                LatLng coordinates = busStopList.get(position).getCoordinates();

                BusStop busStop = new BusStop(id, name, coordinates, lines);

                transaction.replace(R.id.nav_host_fragment, StopFragment.newInstance(busStop), null);

                transaction.commit();
            }
        });
        holder.configurationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = FavouriteListAdapter.this.parentFragment;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);

                String name = busStopList.get(position).getStopBusName();
                String id = busStopList.get(position).getStopBusId();
                String[] linesAux = busStopList.get(position).getBusLines().split(", ");
                ArrayList<String> lines = new ArrayList<String>(Arrays.asList(linesAux));
                LatLng coordinates = busStopList.get(position).getCoordinates();

                BusStop busStop = new BusStop(id, name, coordinates, lines);

                transaction.replace(R.id.nav_host_fragment, FavouriteFragment.newInstance(busStop), null);

                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return busStopList.size();
    }

    @Override
    public void onClick(View v) {

    }

    class BusStopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView busStopID;
        private TextView busStopLines;
        private TextView busStopName;
        private TextView busStopName_user;
        private FavouriteListAdapter adapter;
        private ImageView configurationImage;

        public BusStopViewHolder(View itemView, FavouriteListAdapter adapter) {
            super(itemView);

            busStopName = itemView.findViewById(R.id.favouriteBusStop_busStopName_textView);
            busStopID = itemView.findViewById(R.id.favouriteBusStop_busStopID_textView);
            busStopLines = itemView.findViewById(R.id.favouriteBusStop_busStopLines_textView);
            busStopName_user = itemView.findViewById(R.id.favouriteBusStop_userBusStopName_textView);
            configurationImage = itemView.findViewById(R.id.favourites_configuration_imageView);

            configurationImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goToConfFragment();
                }
            });

            this.adapter = adapter;
        }

        private void goToConfFragment() {

            FragmentManager fragmentManager = FavouriteListAdapter.this.parentFragment;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);


            //transaction.replace(R.id.nav_host_fragment, FavouriteFragment.newInstance(), null);

            transaction.commit();
        }

        public void onClickModified(View v, FavouriteBusInfo busStop) {

        }

        @Override
        public void onClick(View v) {

        }
    }
}
