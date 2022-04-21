package es.ucm.fdi.emtntr.stopSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.ui.StopFragment;

public class BusStopResultListAdapter extends RecyclerView.Adapter<BusStopResultListAdapter.BusStopViewHolder> implements View.OnClickListener, Filterable {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<BusStop> busStopList;
    private ArrayList<BusStop> busStopListFull;
    private final int MAX_RESULTS = 40;
    private FragmentManager parentFragment;

    public BusStopResultListAdapter(Context context, List<BusStop> busStopList, LayoutInflater layoutInflater, FragmentManager parentFragment) {

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
    public BusStopResultListAdapter.BusStopViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.layout_for_card_view_bus_stops_search, parent, false);

        return new BusStopViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BusStopViewHolder holder, int position) {

        String busStopName = "";
        String busStopID = "";
        String busStopLines = "aa";

        busStopName = busStopList.get(position).getName();
        busStopID = busStopList.get(position).getId();
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

                FragmentManager fragmentManager = BusStopResultListAdapter.this.parentFragment;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);

                transaction.replace(R.id.nav_host_fragment, StopFragment.newInstance(busStopList.get(position)), null);

                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {

        return busStopList.size();
        //return 5;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Filter getFilter() {

        return StopBusListFiltered;
    }

    private Filter StopBusListFiltered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<BusStop> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(busStopListFull);
            }
            else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BusStop busStop: busStopListFull) {

                    String id = busStop.getId();
                    String name = busStop.getName();

                    if (id != null && (id.toLowerCase().contains(filterPattern) || id.toLowerCase().equals(filterPattern))) {
                        filteredList.add(busStop);
                    }
                    else if (name != null && (name.toLowerCase().contains(filterPattern) || name.toLowerCase().equals(filterPattern))) {
                        filteredList.add(busStop);
                    }
                }
            }

            ArrayList<BusStop> finalFilteredList = new ArrayList<>();
            if (filteredList.size() < MAX_RESULTS) {
                finalFilteredList.addAll(filteredList);
            }
            else {

                for (int i = 0; i < MAX_RESULTS; i++) {
                    finalFilteredList.add(filteredList.get(i));
                }
            }

            FilterResults results = new FilterResults();
            results.values = finalFilteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            busStopList.clear();
            busStopList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class BusStopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView busStopID;
        private TextView busStopLines;
        private TextView busStopName;
        private BusStopResultListAdapter adapter;

        public BusStopViewHolder(View itemView, BusStopResultListAdapter adapter) {
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
