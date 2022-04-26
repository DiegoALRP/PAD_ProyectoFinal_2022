package es.ucm.fdi.emtntr.favorites;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class FavouriteBusInfo {

    private String user_stopBusName;
    private String stopBusName;
    private String stopBusId;
    private LatLng coordinates;
    private ArrayList<String> busLines;

    private FavouriteBusInfo() {

    }

    public String getUser_stopBusName() {
        return user_stopBusName;
    }

    public void setUser_stopBusName(String user_stopBusName) {
        this.user_stopBusName = user_stopBusName;
    }

    public String getStopBusName() {
        return stopBusName;
    }

    public void setStopBusName(String stopBusName) {
        this.stopBusName = stopBusName;
    }

    public String getStopBusId() {
        return stopBusId;
    }

    public void setStopBusId(String stopBusId) {
        this.stopBusId = stopBusId;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<String> getBusLines() {
        return busLines;
    }

    public void setBusLines(ArrayList<String> busLines) {
        this.busLines = busLines;
    }
}
