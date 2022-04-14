package es.ucm.fdi.emtntr.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusStop {

    private final String id;
    private final String name;
    private final LatLng coords;

    private BusStop(String id, String name, LatLng coords) {
        this.id = id;
        this.name = name;
        this.coords = coords;
    }

    public static BusStop fromDetails(JSONObject json) throws JSONException {
        JSONArray coords = json.getJSONObject("geometry").getJSONArray("coordinates");

        String id = json.getString("stop");
        String name = json.getString("name");
        LatLng ll = new LatLng(coords.getDouble(1), coords.getDouble(0));
        BusStop stop = new BusStop(id, name, ll);
        return stop;
    }

    public static List<BusStop> fromNear(JSONArray json) throws JSONException {
        List<BusStop> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            list.add(fromDetails(json.getJSONObject(i)));
        }
        return list;
    }

    public static BusStop fromArrive(JSONObject json) throws JSONException {
        JSONArray coords = json.getJSONObject("geometry").getJSONArray("coordinates");

        String id = json.getString("stop");
        String name = json.getString("name");
        LatLng ll = new LatLng(coords.getDouble(1), coords.getDouble(0));
        BusStop stop = new BusStop(id, name, ll);
        return stop;
    }

    public static List<BusStop> fromBasicList(JSONArray json) throws JSONException {
        List<BusStop> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            list.add(fromBasic(json.getJSONObject(i)));
        }
        return list;
    }

    public static BusStop fromBasic(JSONObject json) throws JSONException {
        JSONArray coords = json.getJSONObject("geometry").getJSONArray("coordinates");

        String id = json.getString("node");
        String name = json.getString("name");
        LatLng ll = new LatLng(coords.getDouble(1), coords.getDouble(0));
        BusStop stop = new BusStop(id, name, ll);
        return stop;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LatLng getCoords() {
        return coords;
    }
}
