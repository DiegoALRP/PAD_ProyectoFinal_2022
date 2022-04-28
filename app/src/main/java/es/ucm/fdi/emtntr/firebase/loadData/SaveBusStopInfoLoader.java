package es.ucm.fdi.emtntr.firebase.loadData;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.ucm.fdi.emtntr.emt.EMTApi;
import es.ucm.fdi.emtntr.model.BusStop;

public class SaveBusStopInfoLoader extends AsyncTaskLoader<List<BusStop>> {

    public SaveBusStopInfoLoader(@NonNull @NotNull Context context) {
        super(context);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public List<BusStop> loadInBackground() {
        return getBusStopInfo();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public List<BusStop> getBusStopInfo() {
        return new EMTApi().getStopLocations().getData();
    }
}
