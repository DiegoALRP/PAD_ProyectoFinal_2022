package es.ucm.fdi.emtntr.emt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import es.ucm.fdi.emtntr.model.Arrival;
import es.ucm.fdi.emtntr.model.BusStop;
import es.ucm.fdi.emtntr.model.StopArrivals;

public class EMTApi {

    private static final String baseURL = "https://openapi.emtmadrid.es/v2/";
    private String token = null;

    public EMTApi(String user, String pass) {
        Response<JSONArray> res = login(user, pass);
        try {
            this.token = (res.getCode().equals("00") || res.getCode().equals("01")) ? res.getData().getJSONObject(0).getString("accessToken") : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public EMTApi() {
        token = "cd56c60e-5c55-11ea-83c4-02dc460b89d8";
    }

    private String buildPath(String... args) {
        StringJoiner sj = new StringJoiner("/", "", "/");
        for (String elm : args) {
            sj.add(elm);
        }
        return sj.toString();
    }

    private Response<JSONArray> apiCall(String endpoint) {
        return apiCall(endpoint, null);
    }

    private Response<JSONArray> apiCall(String endpoint, String body) {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessToken", this.token);
        return apiCall(endpoint, body, map);
    }

    private Response<JSONArray> apiCall(String endpoint, String body, Map<String, String> headers) {
        HttpURLConnection conn = null;

        try {
            // Prepare the request
            conn = (HttpURLConnection) new URL(baseURL + endpoint).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            // Set headers
            for (Map.Entry<String, String> e : headers.entrySet()) {
                conn.setRequestProperty(e.getKey(), e.getValue());
            }

            if (body != null) {
                // Set body
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
                    bw.write(body);
                    bw.flush();
                }
            }

            // Make the request and return the response
            conn.connect();
            return streamToJSON(conn.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            return new Response<>("1000", e.getMessage());
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    static private Response<JSONArray> streamToJSON(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());
            return new Response<>(json.getString("code"), json.getString("description"), json.getJSONArray("data"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return new Response<>("1000", e.getMessage());
        }
    }

    private Response<JSONArray> login(String user, String pass) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", user);
        map.put("password", pass);
        Response<JSONArray> response = apiCall("mobilitylabs/user/login/", null, map);
        System.out.println(response.getData());
        return response;
    }

    public Response<JSONArray> apiTest() {
        return apiCall("mobilitylabs/user/whoami/");
    }

    public Response<BusStop> getStopDetails(String id) {
        Response<JSONArray> res = apiCall(buildPath("transport/busemtmad/stops", id, "detail"));

        if (res.getCode().equals("00")) {
            try {
                return new Response<>(res.getCode(), res.getMessage(), BusStop.fromDetails(res.getData().getJSONObject(0).getJSONArray("stops").getJSONObject(0)));
            } catch (JSONException e) {
                e.printStackTrace();
                return new Response<>("1000", e.getMessage());
            }
        } else {
            return new Response<>(res.getCode(), res.getMessage());
        }
    }

    public Response<StopArrivals> getArriveTimes(BusStop stop) {
        Response<JSONArray> res = apiCall(buildPath("transport/busemtmad/stops", stop.getId(), "arrives"), "{\"Text_EstimationsRequired_YN\":\"Y\"}");

        if (res.getCode().equals("00")) {
            try {
                return new Response<>(res.getCode(), res.getMessage(), StopArrivals.fromArrivals(stop, res.getData().getJSONObject(0).getJSONArray("Arrive")));
            } catch (JSONException e) {
                e.printStackTrace();
                return new Response<>("1000", e.getMessage());
            }
        } else {
            return new Response<>(res.getCode(), res.getMessage());
        }
    }

    public Response<List<Arrival>> getArrives(BusStop stop) {
        Response<JSONArray> res = apiCall(buildPath("transport/busemtmad/stops", stop.getId(), "arrives"), "{\"Text_EstimationsRequired_YN\":\"Y\"}");
        if (res.getCode().equals("00")) {
            try {
                return new Response<>(res.getCode(), res.getMessage(), Arrival.fromArrive(res.getData().getJSONObject(0).getJSONArray("Arrive")));
            } catch (JSONException e) {
                e.printStackTrace();
                return new Response<>("1000", e.getMessage());
            }
        } else {
            return new Response<>(res.getCode(), res.getMessage());
        }
    }

    public Response<List<BusStop>> getNearbyStops(String lat, String lng, String distance) {
        Response<JSONArray> res = apiCall(buildPath("transport/busemtmad/stops/arroundxy", lng, lat, distance));

        if (res.getCode().equals("00")) {
            try {
                return new Response<>(res.getCode(), res.getMessage(), BusStop.fromNearList(res.getData()));
            } catch (JSONException e) {
                e.printStackTrace();
                return new Response<>("1000", e.getMessage());
            }
        } else {
            return new Response<>(res.getCode(), res.getMessage());
        }
    }

    public Response<List<BusStop>> getStopLocations() {
        Response<JSONArray> res = apiCall(buildPath("transport/busemtmad/stops/list"), "");

        if (res.getCode().equals("00")) {
            try {
                return new Response<>(res.getCode(), res.getMessage(), BusStop.fromBasicList(res.getData()));
            } catch (JSONException e) {
                e.printStackTrace();
                return new Response<>("1000", e.getMessage());
            }
        } else {
            return new Response<>(res.getCode(), res.getMessage());
        }
    }
}
