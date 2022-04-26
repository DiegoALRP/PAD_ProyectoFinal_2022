package es.ucm.fdi.emtntr.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.stopSearch.BusStopResultListAdapter;
import es.ucm.fdi.emtntr.ui.StopFragment;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.BusStopViewHolder> implements View.OnClickListener {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<BusStop> busStopList;
    private ArrayList<BusStop> busStopListFull;
    private FragmentManager parentFragment;

    public FavouriteListAdapter(Context context, List<BusStop> busStopList, LayoutInflater layoutInflater, FragmentManager parentFragment) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);//layoutInflater;
        this.parentFragment = parentFragment;
        setBusStopData(busStopList);
    }

    public void setBusStopData(List<BusStop> busStopList) {

        this.busStopList = (ArrayList<BusStop>) busStopList;
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

        String busStopName = "";
        String busStopID = "";
        String busStopNumber = "N. ";
        String busStopLines = "aa";

        busStopName = busStopList.get(position).getName();
        busStopID = busStopNumber + busStopList.get(position).getId();
        ArrayList<String> linesList = (ArrayList<String>) busStopList.get(position).getLines();
        int linesSize = linesList.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < linesSize; i++) {
            stringBuilder.append(linesList.get(i));
            stringBuilder.append(", ");
        }

        int sb_size = stringBuilder.length();
        if (sb_size > 1) {
            stringBuilder.delete(sb_size - 2, sb_size - 1);
        }
        busStopLines = stringBuilder.toString();

        holder.busStopName.setText(busStopName);
        holder.busStopID.setText(busStopID);
        holder.busStopLines.setText(busStopLines);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = FavouriteListAdapter.this.parentFragment;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);

                transaction.replace(R.id.nav_host_fragment, StopFragment.newInstance(busStopList.get(position)), null);

                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    class BusStopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView busStopID;
        private TextView busStopLines;
        private TextView busStopName;
        private FavouriteListAdapter adapter;

        public BusStopViewHolder(View itemView, FavouriteListAdapter adapter) {
            super(itemView);

            busStopName = itemView.findViewById(R.id.searchBusStop_busStopName_textView);
            busStopID = itemView.findViewById(R.id.searchBusStop_busStopID_textView);
            busStopLines = itemView.findViewById(R.id.searchBusStop_busStopLines_textView);

            this.adapter = adapter;
        }

        public void onClickModified(View v, BusStop busStop) {

        }

        @Override
        public void onClick(View v) {

        }
    }
}
