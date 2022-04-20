package es.ucm.fdi.emtntr.StopArrives;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jetbrains.annotations.NotNull;

import es.ucm.fdi.emtntr.emt.EMTApi;
import es.ucm.fdi.emtntr.emt.Response;
import es.ucm.fdi.emtntr.model.BusStop;

public class AsyncAdaptableLoader<T> extends AsyncTaskLoader<T> {


    LoaderSelector opcion;
    String[] args;

    public AsyncAdaptableLoader(@NonNull @NotNull Context context, String[] args, LoaderSelector opcion) {

        super(context);
        this.opcion = opcion;
        this.args = args;

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public T loadInBackground() {

        T list;
        list = getFromAPI();
        return list;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public T getFromAPI() {

        EMTApi emtApi = new EMTApi();
        Response<T> response = null;

        switch(opcion)
        {
            case NEAR_STOPS: response = (Response<T>) emtApi.getNearbyStops(args[0], args[1], args[2]); break;

            case ARRIVE_TIMES: response = (Response<T>) emtApi.getArrives(new BusStop(args[0], null, null)); break;

        }

        T data = response.getData();
        return data;
    }


    public enum LoaderSelector{
        NEAR_STOPS,                 //lat, long, max_radio
        ARRIVE_TIMES                //busStopId
    }
}
