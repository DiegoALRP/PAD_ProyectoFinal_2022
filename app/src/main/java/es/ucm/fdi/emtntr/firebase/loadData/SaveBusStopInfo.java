package es.ucm.fdi.emtntr.firebase.loadData;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.model.BusStop;

public class SaveBusStopInfo {

    private BusStopLoaderCallBacks busStopLoaderCallBacks;
    //private Context context;

    public SaveBusStopInfo(Context context) {

        busStopLoaderCallBacks = new BusStopLoaderCallBacks(context);
    }

    public void writeInInternalStorage(List<BusStop> busStopInfos) {

        ArrayList<BusStop> busStopInfosArrayList = (ArrayList<BusStop>) busStopInfos;
    }

    public class BusStopLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<BusStop>> {

        private Context context;

        public BusStopLoaderCallBacks(Context context) {
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public SaveBusStopInfoLoader onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            SaveBusStopInfoLoader busStopLoader = new SaveBusStopInfoLoader(context);

            return busStopLoader;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BusStop>> loader, List<BusStop> data) {

            writeInInternalStorage(data);
        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<BusStop>> loader) {

        }
    }
}
