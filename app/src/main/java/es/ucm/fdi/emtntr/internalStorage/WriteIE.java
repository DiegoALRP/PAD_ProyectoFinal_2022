package es.ucm.fdi.emtntr.internalStorage;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import es.ucm.fdi.emtntr.favorites.FavouriteBusInfo;
import es.ucm.fdi.emtntr.model.BusStop;

public class WriteIE {

    private final String filenameFav = "FavouritesBusStopList.json";

    public WriteIE() {
        //Do Nothing
    }

    public void write(Context context, String filename, String data) {

        try {

            File file = new File(context.getFilesDir(), filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) { //API 19
                outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.ISO_8859_1);
            }

            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFavouriteBusStop(Context context, ArrayList<BusStop> busStops) {

        StringBuilder stringBuilder = new StringBuilder();
        BusStop busStop = busStops.get(0);

        ArrayList<String> linesString = (ArrayList<String>) busStop.getLines();
        for (String lineNumber: linesString) {

            stringBuilder.append(lineNumber);
            stringBuilder.append(", ");
        }

        String lines = stringBuilder.substring(0, stringBuilder.length() - 2);

        FavouriteBusInfo favouriteBusInfo = new FavouriteBusInfo("none", busStop.getName(), busStop.getId(), busStop.getCoords(), lines);

        Gson gson = new Gson();
        String data = gson.toJson(favouriteBusInfo);

        write(context, filenameFav, data);
    }
}
