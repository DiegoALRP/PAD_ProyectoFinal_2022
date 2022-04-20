package es.ucm.fdi.emtntr.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.StopArrives.AsyncAdaptableLoader;
import es.ucm.fdi.emtntr.map.MapController;
import es.ucm.fdi.emtntr.map.NearBusStopLoader;
import es.ucm.fdi.emtntr.model.BusStop;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnInfoWindowClickListener {

    private MapController mapController;
    private LocationManager locationManager;
    private NearBusStopLoaderCallBacks nearBusStopLoaderCallBacks;
    private LatLng pos;

    private final double max_dist = 0.5; // kilometros para volver a calcular las paradas
    private final String max_radio = "1500"; // metros a los que detecta las paradas

    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);

        pos = null;

        //Inicialización del mapa en el fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        nearBusStopLoaderCallBacks = new NearBusStopLoaderCallBacks(getContext());

        //locationManager irá actualizando las localizaciones mediante gps, que serán recibidas en onLocationChanged()
        locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);

        return root;
    }


    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mapController = new MapController(googleMap, this, getContext());

        //Pide la ultima localizacion disponible (puede ser erronea o no haber ninguna)
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location!=null)
        {
            onLocationChanged(location);
        }

        //Si el internet esta activo, pide una localizacion instantanea
        Criteria criteria = new Criteria();
        if (isInternetAvailable()) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            locationManager.requestSingleUpdate(criteria, this, null);
        }

    }

    //La localización cambia
    @Override
    public void onLocationChanged(@NonNull Location location) {

             LatLng new_pos = new LatLng(location.getLatitude(), location.getLongitude());
             if(pos == null || distance_Between_LatLong(pos, new_pos) > max_dist)
             {
                 pos = new_pos;
                 mapController.mostrarUbicacion(pos);
                 mapController.zoom(pos);
                 LoaderManager.getInstance(this).restartLoader(0, new Bundle(), nearBusStopLoaderCallBacks);
             }
             else
             {
                 mapController.mostrarUbicacion(pos);
             }

    }

    //distancia en km
    public static double distance_Between_LatLong(LatLng latLng1, LatLng latLng2) {
        double lat1 = Math.toRadians(latLng1.latitude);
        double lon1 = Math.toRadians(latLng1.longitude);
        double lat2 = Math.toRadians(latLng2.latitude);
        double lon2 = Math.toRadians(latLng2.longitude);

        double earthRadius = 6371.01; //Kilometers

        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
    }


    //Se pulsa la iformación de un marcador (marcadores creados en el MapController)
    @Override
    public void onInfoWindowClick(@NonNull @NotNull Marker marker) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        BusStop b = new BusStop(marker.getSnippet(), marker.getTitle(), marker.getPosition());
        transaction.replace(R.id.nav_host_fragment, StopFragment.newInstance(b), null);

        transaction.commit();

    }

    //Comprueba si hay acceso a internet
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    public class NearBusStopLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<BusStop>> {

        Context context;
        public NearBusStopLoaderCallBacks(Context context) {
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public AsyncAdaptableLoader<List<BusStop>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            String[] loaderArgs = new String[]{String.valueOf(pos.latitude), String.valueOf(pos.longitude), max_radio};
            AsyncAdaptableLoader<List<BusStop>> nearBusStopLoader =
                    new AsyncAdaptableLoader<List<BusStop>>(context, loaderArgs, AsyncAdaptableLoader.LoaderSelector.NEAR_STOPS);

            return nearBusStopLoader;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BusStop>> loader, List<BusStop> data) {

            mapController.mostrarParadas(data);
        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<BusStop>> loader) {

        }

    }

}
