package es.ucm.fdi.emtntr.stopSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import es.ucm.fdi.emtntr.R;

public class BusStopResultListAdapter extends RecyclerView.Adapter<BusStopResultListAdapter.BusStopViewHolder> {

    private Context context;
    //private
    public BusStopResultListAdapter(Context context) {

        this.context = context;
        //mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @NotNull
    @Override
    public BusStopViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

       // View itemView =

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BusStopViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BusStopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userStopName;
        private TextView busStopNumber;
        private TextView busLines;
        private TextView busStopName;

        public BusStopViewHolder(View itemView) {
            super(itemView);

            userStopName = itemView.findViewById(R.id.userStopName_textView);
            busStopNumber = itemView.findViewById(R.id.stopNumber_textView);
            busLines = itemView.findViewById(R.id.busLines_textView);
            busStopName = itemView.findViewById(R.id.busStopName_textView);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
