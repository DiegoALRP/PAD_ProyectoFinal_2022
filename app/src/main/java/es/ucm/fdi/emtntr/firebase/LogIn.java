package es.ucm.fdi.emtntr.firebase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.Nav_Activity;
import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.firebase.loadData.SaveBusStopInfoLoader;
import es.ucm.fdi.emtntr.internalStorage.WriteIE;
import es.ucm.fdi.emtntr.model.BusStop;

public class LogIn extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextInputEditText email_input;
    private TextInputEditText password_input;

    private String user_email;
    private String user_password;

    private BusStopLoaderCallBacks busStopLoaderCallBacks;

    private final String TAG = "AuthEmailAndPassword";
    private final String filename = "BusStopInfo.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login_page);

        mAuth = FirebaseAuth.getInstance();

        busStopLoaderCallBacks = new BusStopLoaderCallBacks(this);
        Bundle queryBundle = new Bundle();
        //getLoaderManager().restartLoader(1, queryBundle, busStopLoaderCallBacks);
        LoaderManager.getInstance(this).restartLoader(0, queryBundle, busStopLoaderCallBacks);

        if (userIsLoggedIn()){
            //reload();
        }

        email_input = findViewById(R.id.email_input_text_field);
        password_input = findViewById(R.id.password_input_text_field);

        Button login_button = findViewById(R.id.logIn_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();
            }
        });

        Button createAccount_button = findViewById(R.id.createAccountButton);
        createAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountButtonClicked();
            }
        });
    }

    public boolean userIsLoggedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    private void loginButtonClicked() {

        user_email = String.valueOf(email_input.getText());
        user_password = String.valueOf(password_input.getText());

        verifyInput();

        //logInUser(email, password);
    }

    private void verifyInput() {

        boolean is_ok = true;
        String error = "";

        if (user_email.equals("")) {
            is_ok = false;
            error = "email is empty";
        }
        if (user_password.equals("")) {
            is_ok = false;
            error = "password field is empty";
        }

        if (!is_ok) {
            showAlertErrorUser(error);
        }
        else {
            logInUser(user_email, user_password);
        }

    }

    private void createAccountButtonClicked() {

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void logInUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LogIn.this, "User Logged in", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Reloads the page or starts a new activity
    private void reload() {

        Intent intent = new Intent(this, Nav_Activity.class);
        startActivity(intent);
    }

    private void goToMain() {

        Intent intent = new Intent(this, Nav_Activity.class);
        startActivity(intent);
    }

    public void writeInInternalStorage(List<BusStop> busStopInfos) {

        List<BusStop> busStopInfos1 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            busStopInfos1.add(busStopInfos.get(i));
        }
        Gson gson = new Gson();
        String data = gson.toJson(busStopInfos1);

        WriteIE writeIE = new WriteIE();
        writeIE.write(getApplicationContext(), filename, data);
    }

    private void showAlertErrorUser(String st_error) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(st_error)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    class BusStopLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<BusStop>> {

        private Context context;

        public BusStopLoaderCallBacks(Context context) {
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public SaveBusStopInfoLoader onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            SaveBusStopInfoLoader busStopLoader = new SaveBusStopInfoLoader(context);

            return busStopLoader;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BusStop>> loader, List<BusStop> data) {

            writeInInternalStorage(data);
        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<BusStop>> loader) {

        }
    }
}