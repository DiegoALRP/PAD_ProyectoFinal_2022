package es.ucm.fdi.emtntr.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class StopArrivals {

    private final BusStop stop;
    private final List<Arrival> arrivals;

    private StopArrivals(BusStop stop, List<Arrival> arrivals) {
        this.stop = stop;
        this.arrivals = arrivals;
    }

    public static StopArrivals fromArrivals(BusStop stop, JSONArray json) throws JSONException {

        return new StopArrivals(stop, Arrival.fromArrive(json));
    }

    public BusStop getStop() {
        return stop;
    }

    public List<Arrival> getArrivals() {
        return arrivals;
    }
}
