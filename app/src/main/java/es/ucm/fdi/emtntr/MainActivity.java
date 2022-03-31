package es.ucm.fdi.emtntr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {

    private Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.Log_btn);

    }

    public void login(View view){
        Intent intent = new Intent(this, Nav_Activity.class);
        startActivity(intent);
    }
}
