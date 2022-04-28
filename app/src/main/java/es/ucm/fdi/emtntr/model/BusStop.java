package es.ucm.fdi.emtntr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusStop implements Parcelable {

    private final String id;
    private final String name;
    private final LatLng coords;
    private List<String> lines;

    public BusStop(String id, String name, LatLng coords) {
        this.id = id;
        this.name = name;
        this.coords = coords;
    }

    public BusStop(String id, String name, LatLng coords, ArrayList<String> lines) {
        this.id = id;
        this.name = name;
        this.coords = coords;
        this.lines = lines;
    }

    protected BusStop(Parcel in) {
        id = in.readString();
        name = in.readString();
        coords = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<BusStop> CREATOR = new Creator<BusStop>() {
        @Override
        public BusStop createFromParcel(Parcel in) {
            return new BusStop(in);
        }

        @Override
        public BusStop[] newArray(int size) {
            return new BusStop[size];
        }
    };

    public static BusStop fromDetails(JSONObject json) throws JSONException {
        JSONArray coords = json.getJSONObject("geometry").getJSONArray("coordinates");

        String id = json.getString("stop");
        String name = json.getString("name");
        LatLng ll = new LatLng(coords.getDouble(1), coords.getDouble(0));
        BusStop stop = new BusStop(id, name, ll);

        JSONArray lineData = json.getJSONArray("dataLine");
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < lineData.length(); i++) {
            lines.add(lineData.getJSONObject(i).getString("line"));
        }

        stop.lines = lines;
        return stop;
    }

    public static BusStop fromNear(JSONObject json) throws JSONException {
        JSONArray coords = json.getJSONObject("geometry").getJSONArray("coordinates");
        String id = json.getString("stopId");
        String name = json.getString("stopName");
        LatLng ll = new LatLng(coords.getDouble(1), coords.getDouble(0));
        BusStop stop = new BusStop(id, name, ll);
        return stop;
    }

    public static List<BusStop> fromNearList(JSONArray json) throws JSONException {
        List<BusStop> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            list.add(fromNear(json.getJSONObject(i)));
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

        JSONArray lineData = json.getJSONArray("lines");
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < lineData.length(); i++) {
            lines.add(lineData.getString(i).split("/")[0]);
        }

        stop.lines = lines;

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

    public List<String> getLines() {
        return lines;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(coords, i);
        parcel.writeDouble(coords.latitude);
        parcel.writeDouble(coords.latitude);
    }
}
