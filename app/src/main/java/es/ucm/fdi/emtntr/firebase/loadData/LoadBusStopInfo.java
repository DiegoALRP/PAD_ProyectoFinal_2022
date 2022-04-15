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

import es.ucm.fdi.emtntr.stopSearch.BusStopInfo;

public class LoadBusStopInfo {

    private BusStopLoaderCallBacks busStopLoaderCallBacks;
    //private Context context;

    public LoadBusStopInfo(Context context) {

        busStopLoaderCallBacks = new BusStopLoaderCallBacks(context);
    }

    public void writeInInternalStorage(List<BusStopInfo> busStopInfos) {

        ArrayList<BusStopInfo> busStopInfosArrayList = (ArrayList<BusStopInfo>) busStopInfos;
    }

    public class BusStopLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<BusStopInfo>> {

        private Context context;

        public BusStopLoaderCallBacks(Context context) {
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public LoadBusStopInfoLoader onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            LoadBusStopInfoLoader busStopLoader = new LoadBusStopInfoLoader(context);

            return busStopLoader;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BusStopInfo>> loader, List<BusStopInfo> data) {

            writeInInternalStorage(data);
        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<BusStopInfo>> loader) {

        }
    }
}
