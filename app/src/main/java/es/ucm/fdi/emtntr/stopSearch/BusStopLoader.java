package es.ucm.fdi.emtntr.stopSearch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.ucm.fdi.emtntr.model.BusStop;

public class BusStopLoader extends AsyncTaskLoader<List<BusStop>> {


    public BusStopLoader(@NonNull @NotNull Context context) {
        super(context);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public List<BusStop> loadInBackground() {
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
