package es.ucm.fdi.emtntr.map;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.emt.EMTApi;
import es.ucm.fdi.emtntr.emt.Response;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.stopSearch.BusStopInfo;

public class NearBusStopLoader extends AsyncTaskLoader<List<BusStop>> {

    private String lat;
    private String lng;
    private String max_radio = "1500"; // metros a los que detecta las paradas

    public NearBusStopLoader(@NonNull @NotNull Context context, LatLng pos) {

        super(context);
        this.lat = String.valueOf(pos.latitude);
        this.lng = String.valueOf(pos.longitude);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public List<BusStop> loadInBackground() {

        List<BusStop> busStopList;
        busStopList = getNearBusStops();
        return busStopList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public List<BusStop> getNearBusStops() {

        EMTApi emtApi = new EMTApi();

        Response<List<BusStop>> response = emtApi.getNearbyStops(this.lat, this.lng, max_radio);
        List<BusStop> nearStops = response.getData();

        return nearStops;
    }

    public void setMax_radio(String m)
    {
        this.max_radio = m;
    }
}
