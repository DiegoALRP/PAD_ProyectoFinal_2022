package es.ucm.fdi.emtntr.stopSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.Nav_Activity;
import es.ucm.fdi.emtntr.R;

public class BusStopResultListAdapter extends RecyclerView.Adapter<BusStopResultListAdapter.BusStopViewHolder> implements View.OnClickListener{

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<BusStopInfo> busStopList;

    public BusStopResultListAdapter(Context context, List<BusStopInfo> busStopList, LayoutInflater layoutInflater) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);//layoutInflater;
        setBusStopData(busStopList);
    }

    public void setBusStopData(List<BusStopInfo> busStopList) {

        this.busStopList = (ArrayList<BusStopInfo>) busStopList;
    }

    @NonNull
    @NotNull
    @Override
    public BusStopResultListAdapter.BusStopViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.card_view_2, parent, false);

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
