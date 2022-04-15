package es.ucm.fdi.emtntr.stopSearch;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class BusStopInfo {

    String busStopID;
    String busStopName;
    ArrayList<String> linesList;
    LatLng position;

    public BusStopInfo(String busStopID, String busStopName, ArrayList<String> linesList) {

        this.busStopID = busStopID;
        this.busStopName = busStopName;
        this.linesList = linesList;
    }

    public BusStopInfo(String busStopID, String busStopName, ArrayList<String> linesList, LatLng position) {

        this.busStopID = busStopID;
        this.busStopName = busStopName;
        this.linesList = linesList;
        this.position = position;
    }

    public String getBusStopID() {
        return busStopID;
    }

    public void setBusStopID(String busStopID) {
        this.busStopID = busStopID;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    public ArrayList<String> getLinesList() {
        return linesList;
    }

    public void setLinesList(ArrayList<String> linesList) {
        this.linesList = linesList;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    //
}
