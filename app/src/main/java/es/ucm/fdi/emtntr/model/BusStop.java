package es.ucm.fdi.emtntr.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.stopSearch.BusStopInfo;

public class BusStop {

    private final String id;
    private final String name;
    private final LatLng coords;
    private ArrayList<String> linesList;

    public BusStop(String id, String name, LatLng coords) {
        this.id = id;
        this.name = name;
        this.coords = coords;
    }

    public BusStop(String id, String name, LatLng coords, ArrayList<String> linesList) {
        this.id = id;
        this.name = name;
        this.coords = coords;
        this.linesList = linesList;
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

    public static List<BusStop> fromBasicToList(JSONArray jsonArray) {

        List<BusStop> busStopInfoList = new ArrayList<>();

        Gson gson = new Gson();
        ArrayList<LinkedTreeMap<String, Object>> busStopList = gson.fromJson(String.valueOf(jsonArray), ArrayList.class);
        for (LinkedTreeMap<String, Object> busStop: busStopList) {

            String busStopID = String.valueOf(busStop.get("node"));
            String busStopName = String.valueOf(busStop.get("name"));
            ArrayList<String> lines = (ArrayList<String>) busStop.get("lines");
            for (int i = 0; i < lines.size(); i++) {

                String line = lines.get(i);
                String[] valid = line.split("/");
                line = valid[0];
                lines.set(i, line);
            }

            LinkedTreeMap<String, ArrayList<Double>> geo = (LinkedTreeMap<String, ArrayList<Double>>) busStop.get("geometry");
            ArrayList<Double> coordinates = geo.get("coordinates");
            LatLng latLng = new LatLng(coordinates.get(0), coordinates.get(1));

            BusStop busStopInfo = new BusStop(busStopID, busStopName, latLng, lines);

            busStopInfoList.add(busStopInfo);
        }

        return busStopInfoList;
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

    public List<String> getLines() {
        return linesList;
    }
}
