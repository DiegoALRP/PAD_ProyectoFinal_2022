package es.ucm.fdi.emtntr.internalStorage;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import es.ucm.fdi.emtntr.favorites.FavouriteBusInfo;
import es.ucm.fdi.emtntr.model.BusStop;

public class WriteIE {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String filenameFav = "FavouritesBusStopList.json";
    private FirebaseAuth mAuth;

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

    public void addAndWriteBusStopToFavourites(Context context, ArrayList<BusStop> busStops) {

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

        readAndModifyFile(context, filenameFav, favouriteBusInfo);
    }

    public void readAndModifyFile(Context context, String filename, FavouriteBusInfo favouriteBusInfo) {

        firebaseDatabase = FirebaseDatabase.getInstance("https://pad-proyectofinal-1-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getUid() != null) {

            databaseReference.child(mAuth.getUid()).child("Favourites").child(favouriteBusInfo.getStopBusId()).setValue(favouriteBusInfo);

            databaseReference.child(mAuth.getUid()).child("Favourites")
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {

                        HashMap<String, Object> map = (HashMap<String, Object>) task.getResult().getValue();

                        if (map != null) {

                            Gson gson = new Gson();
                            String data = gson.toJson(map);
                            write(context, filename, data);
                        }
                    }
                }
            });
        }
    }

    public void deleteBusStop(Context context, String busStopId) {

        firebaseDatabase = FirebaseDatabase.getInstance("https://pad-proyectofinal-1-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getUid() != null) {

            databaseReference.child(mAuth.getUid()).child("Favourites").child(busStopId).removeValue();

            databaseReference.child(mAuth.getUid()).child("Favourites")
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {

                        HashMap<String, Object> map = (HashMap<String, Object>) task.getResult().getValue();

                        if (map != null) {

                            Gson gson = new Gson();
                            String data = gson.toJson(map);
                            write(context, filenameFav, data);
                        }
                    }
                }
            });
        }
    }
}
