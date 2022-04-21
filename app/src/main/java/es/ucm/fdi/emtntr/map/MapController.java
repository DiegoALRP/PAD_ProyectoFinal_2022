package es.ucm.fdi.emtntr.map;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.Bus;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.stopSearch.BusStopLoader;

public class MapController  {
    private GoogleMap mMap;
    private Marker ubicacion;
    private List<Marker> marcadores;
    Context context;

    public MapController(GoogleMap googleMap, GoogleMap.OnInfoWindowClickListener listener, Context context)
    {
        this.mMap = googleMap;
        mMap.setOnInfoWindowClickListener(listener);
        this.marcadores = new ArrayList<Marker>();
        this.context = context;

        ubicacion=null;
    }

    public void mostrarParadas(List<BusStop> p)
    {

        if(marcadores.size()>0) for(Marker m: marcadores) m.remove();
        if(p!=null)
        {
        ArrayList<BusStop> paradas = new ArrayList<>(p) ;
            for(BusStop b: paradas)
            {
                marcadores.add(mMap.addMarker(new MarkerOptions()
                        .position(b.getCoords())
                        .title(b.getName())
                        .snippet(b.getId())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus))));
            }
        }
    }
    public void mostrarUbicacion(LatLng pos)
    {
        if(ubicacion!=null) ubicacion.remove();

        ubicacion = mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("position"));

    }

    public void zoom (LatLng pos)
    {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, 17);
        mMap.animateCamera(cameraUpdate);
    }



}
