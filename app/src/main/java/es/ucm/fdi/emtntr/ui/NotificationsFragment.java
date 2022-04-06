package es.ucm.fdi.emtntr.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;

import static androidx.core.content.ContextCompat.getSystemService;

public class NotificationsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;


    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Fragments no pueden pedir System Service, por eso el getActivity
        locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        return root;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        float acc = location.getAccuracy();
        while(acc<30) acc = location.getAccuracy();
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
        mostrarParadas(pos);
        locationManager.removeUpdates(this);
    }

    public void mostrarParadas(LatLng pos)
    {
        List<LatLng> paradas = new ArrayList<LatLng>();
        //Prueba
        for (int i = 0; i<5; i++) paradas.add(new LatLng(pos.latitude + Math.random()/1000 , pos.longitude+ Math.random()/1000));
        //
        for(LatLng l: paradas)
        {
            mMap.addMarker(new MarkerOptions().position(l).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus)));
        }
    }
}
