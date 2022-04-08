package es.ucm.fdi.emtntr.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;

import es.ucm.fdi.emtntr.Nav_Activity;
import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.MapController;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnInfoWindowClickListener {

    //private GoogleMap mMap;
    private MapController mapController;
    LocationManager locationManager;


    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Fragments no pueden pedir System Service, por eso el getActivity
        locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

        return root;
    }

    //TODO: Decidir si poner onMapReady y onLocationChanged aqui en el fragment o en el mapController (onInfoWindowClick aqui)

    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mapController = new MapController(googleMap, this);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location!=null)
        {
            locationManager.removeUpdates(this);
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            mapController.mostrarUbicacion(pos);
            mapController.mostrarParadas(pos);
            locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            mapController.mostrarUbicacion(pos);
    }

    @Override
    public void onInfoWindowClick(@NonNull @NotNull Marker marker) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        transaction.replace(R.id.nav_host_fragment, StopFragment.class, null);

        transaction.commit();

    }
}
