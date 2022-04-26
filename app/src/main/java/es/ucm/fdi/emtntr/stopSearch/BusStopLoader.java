package es.ucm.fdi.emtntr.stopSearch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.emt.EMTApi;
import es.ucm.fdi.emtntr.emt.Response;
import es.ucm.fdi.emtntr.model.BusStop;

public class BusStopLoader extends AsyncTaskLoader<List<BusStop>> {

    private String busStopID;
    private Operation operation;

    public BusStopLoader(@NonNull @NotNull Context context, String busStopID, Operation operation) {

        super(context);
        this.busStopID = busStopID;
        this.operation = operation;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public List<BusStop> loadInBackground() {

        List<BusStop> busStopList;
        busStopList = getBusStopInfo();
        return busStopList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public List<BusStop> getBusStopInfo() {

        EMTApi emtApi = new EMTApi();
        List<BusStop> response = new ArrayList<>();
        try {
            Response<List<BusStop>> busStop = emtApi.getBusStopsList();
            response = busStop.getData();
            //response = emtApi.getBusStopsList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public enum Operation {
        BUS_STOP_LIST,
        BUS_STOP_INFO
    }
}
