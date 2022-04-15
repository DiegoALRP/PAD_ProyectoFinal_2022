package es.ucm.fdi.emtntr.stopSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;

public class BusStopResultListAdapter extends RecyclerView.Adapter<BusStopResultListAdapter.BusStopViewHolder> implements View.OnClickListener, Filterable {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<BusStopInfo> busStopList;
    private ArrayList<BusStopInfo> busStopListFull;
    private final int MAX_RESULTS = 40;

    public BusStopResultListAdapter(Context context, List<BusStopInfo> busStopList, LayoutInflater layoutInflater) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);//layoutInflater;
        setBusStopData(busStopList);
    }

    public void setBusStopData(List<BusStopInfo> busStopList) {

        this.busStopList = (ArrayList<BusStopInfo>) busStopList;
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

        busStopName = busStopList.get(position).getBusStopName();
        busStopID = busStopList.get(position).getBusStopID();
        ArrayList<String> linesList = busStopList.get(position).getLinesList();
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

            ArrayList<BusStopInfo> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(busStopListFull);
            }
            else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BusStopInfo busStop: busStopListFull) {

                    String id = busStop.busStopID;
                    String name = busStop.busStopName;

                    if (id != null && (id.toLowerCase().contains(filterPattern) || id.toLowerCase().equals(filterPattern))) {
                        filteredList.add(busStop);
                    }
                    else if (name != null && (name.toLowerCase().contains(filterPattern) || name.toLowerCase().equals(filterPattern))) {
                        filteredList.add(busStop);
                    }
                }
            }

            ArrayList<BusStopInfo> finalFilteredList = new ArrayList<>();
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
            //busStopName = itemView.findViewById(R.id.busStopName_textView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
