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

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextInputEditText name_input;
    private TextInputEditText email_input;
    private TextInputEditText password_input;

    private final String TAG = "AuthEmailAndPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_signup_page);

        mAuth = FirebaseAuth.getInstance();

        name_input = findViewById(R.id.signUp_name_input_text_field);
        email_input = findViewById(R.id.signUp_email_input_text_field);
        password_input = findViewById(R.id.signUp_password_input_text_field);

        Button login_button = findViewById(R.id.singUp_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpButtonClicked();
            }
        });
    }

    private void signUpButtonClicked() {

        String name = String.valueOf(name_input.getText());
        String email = String.valueOf(email_input.getText());
        String password = String.valueOf(password_input.getText());

        createNewUserEmailAndPassword(email, password);
    }

    public void createNewUserEmailAndPassword(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignUp.this, "User created!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Fail to create user", Toast.LENGTH_LONG).show();
                        }

                        SignUp.this.finish();
                    }
                });
    }

    private void goToMain() {
        Intent intent = new Intent(this, Nav_Activity.class);
        startActivity(intent);
    }
}