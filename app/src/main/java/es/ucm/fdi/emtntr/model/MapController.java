package es.ucm.fdi.emtntr.model;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;

public class MapController  {
    private GoogleMap mMap;
    private Marker ubicacion;
    List<Marker> marcadores;

    public MapController(GoogleMap googleMap, GoogleMap.OnInfoWindowClickListener listener)
    {
        this.mMap = googleMap;
        mMap.setOnInfoWindowClickListener(listener);
        this.marcadores = new ArrayList<Marker>();
        ubicacion=null;
    }

    public void mostrarParadas(LatLng pos)
    {
        //Prueba//////////////////////////////////////////////////////////////
        List<BusStop> paradasPrueba = new ArrayList<BusStop>();
        for (int i = 0; i<5; i++)
        {
            BusStop b= new BusStop();
            b.setPosition(new LatLng(pos.latitude + Math.random()/1000 , pos.longitude+ Math.random()/1000));
            b.setBusStopName("parada " + i);
            b.setBusStopID("0000" + i);
            paradasPrueba.add(b);
        }
        /////////////////////////////////////////////////////////////////////

        if(marcadores.size()>0) for(Marker m: marcadores) m.remove();

        for(BusStop b: paradasPrueba)
        {
            marcadores.add(mMap.addMarker(new MarkerOptions()
                    .position(b.getPosition())
                    .title(b.getBusStopName())
                    .snippet("numero: " + b.getBusStopID())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus))));
        }
    }
    public void mostrarUbicacion(LatLng pos)
    {
        if(ubicacion!=null) ubicacion.remove();

        ubicacion = mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("position"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, 17);

        mMap.animateCamera(cameraUpdate);
    }

}
