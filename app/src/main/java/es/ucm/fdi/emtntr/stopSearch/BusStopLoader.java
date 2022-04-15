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

public class BusStopLoader extends AsyncTaskLoader<List<BusStopInfo>> {

    private String busStopID;

    public BusStopLoader(@NonNull @NotNull Context context, String busStopID) {

        super(context);
        this.busStopID = busStopID;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public List<BusStopInfo> loadInBackground() {

        List<BusStopInfo> busStopList;
        busStopList = getBusStopInfo();
        return busStopList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public List<BusStopInfo> getBusStopInfo() {

        EMTApi emtApi = new EMTApi();
        List<BusStopInfo> response = new ArrayList<BusStopInfo>();
        try {
            response = emtApi.getBusStopsList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}
