package es.ucm.fdi.emtntr.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.ucm.fdi.emtntr.Nav_Activity;
import es.ucm.fdi.emtntr.R;

public class LogIn extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextInputEditText email_input;
    private TextInputEditText password_input;

    private final String TAG = "AuthEmailAndPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login_page);

        mAuth = FirebaseAuth.getInstance();

        if (userIsLoggedIn()){
            reload();
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

        String email = String.valueOf(email_input.getText());
        String password = String.valueOf(password_input.getText());

        logInUser(email, password);
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

    }

    private void goToMain() {
        Intent intent = new Intent(this, Nav_Activity.class);
        startActivity(intent);
    }
}