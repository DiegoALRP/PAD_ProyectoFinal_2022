package es.ucm.fdi.emtntr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;

import es.ucm.fdi.emtntr.firebase.LogIn;
import es.ucm.fdi.emtntr.model.BusStop;

public class MainActivity extends AppCompatActivity {

    private Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.Log_btn);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        /*Gson gson = new Gson();
        BusStop busStop = new BusStop();
        busStop.setBusStopDirection("Dir");
        busStop.setBusStopName("pedr");

        String json = gson.toJson(busStop);

        BusStop busStop1 = gson.fromJson(json, BusStop.class);

        int p;*/
    }

    public void login(View view){

        Intent intentLogin = new Intent(this, LogIn.class);
        startActivity(intentLogin);
        //Intent intent = new Intent(this, Nav_Activity.class);
        //startActivity(intent);
    }
}
