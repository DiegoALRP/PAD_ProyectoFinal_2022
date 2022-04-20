package es.ucm.fdi.emtntr.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Arrival {

    private final String line;
    private final String dest;
    private final int bus;
    private final LatLng coords;
    private final int time;
    private StringBuilder timesString;
    private final int distance;

    private Arrival(String line, String dest, int bus, LatLng coords, int time, int distance) {
        this.line = line;
        this.dest = dest;
        this.bus = bus;
        this.coords = coords;
        this.time = time;
        this.timesString = new StringBuilder();
        this.distance = distance;
    }

    public static List<Arrival> fromArrive(JSONArray json) throws JSONException {
        List<Arrival> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            list.add(fromArrive(json.getJSONObject(i)));
        }
        return list;
    }

    public static Arrival fromArrive(JSONObject json) throws JSONException {
        JSONArray coords = json.getJSONObject("geometry").getJSONArray("coordinates");

        String line = json.getString("line");
        String dest = json.getString("destination");
        int bus = json.getInt("bus");
        LatLng ll = new LatLng(coords.getDouble(1), coords.getDouble(0));
        int time = json.getInt("estimateArrive");
        int distance = json.getInt("DistanceBus");

        return new Arrival(line, dest, bus, ll, time, distance);
    }

    public String getLine() {
        return line;
    }

    public String getDest() {
        return dest;
    }

    public int getBus() {
        return bus;
    }

    public LatLng getLatLng() {
        return coords;
    }

    public int getTime() {
        return time;
    }

    public String getTimesString() { return timesString.toString(); }

    public void putTimesString(int t) {
        if (timesString.length() != 0)timesString.append(", ");
        timesString.append(t/60).append(" min");
    }

    public int getDistance() {
        return distance;
    }
}
