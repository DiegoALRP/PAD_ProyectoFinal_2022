package es.ucm.fdi.emtntr.model;

import java.util.ArrayList;
import java.util.HashMap;

public class BusStop {

    String busStopID;
    String busStopName;
    HashMap<Integer, String> busesID;
    String headerA;
    String headerB;
    String busStopDirection;
    String line;
    String minFrequency;
    String maxFrequency;

    public BusStop() {

    }


    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    public String getBusesID(int id) {
        return busesID.get(id);
    }

    public void setBusesID(ArrayList<String> busesID) {
        //this.busesID = busesID;
    }

    public String getHeaderA() {
        return headerA;
    }

    public void setHeaderA(String headerA) {
        this.headerA = headerA;
    }

    public String getHeaderB() {
        return headerB;
    }

    public void setHeaderB(String headerB) {
        this.headerB = headerB;
    }

    public String getBusStopDirection() {
        return busStopDirection;
    }

    public void setBusStopDirection(String busStopDirection) {
        this.busStopDirection = busStopDirection;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMinFrequency() {
        return minFrequency;
    }

    public void setMinFrequency(String minFrequency) {
        this.minFrequency = minFrequency;
    }

    public String getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(String maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

    public String getDirectionName() {
        if (busStopDirection.equals("A")) {
            return headerA;
        }
        else {
            return headerB;
        }
    }
}
