package com.example.simon.attendence;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText textInputEmail;
    private EditText textInputPassword;
    private Button submitButton;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        textInputEmail = (EditText) findViewById(R.id.input_email);
        textInputPassword = (EditText) findViewById(R.id.input_password);
        submitButton = (Button) findViewById(R.id.loginButton);

        FirebaseUser user = auth.getCurrentUser();
        if ( user != null) {
            Log.d("!!!","loggdIn: " + user.getEmail());
            //do something

        } else {
            Log.d("!!!", "no user");
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        //updateUI(currentUser);
    }

    void loginComplete(){
        Intent intent = new Intent(this, CheckinActivity.class);
        startActivity(intent);
    }
    void signInAttempt(View view){
        email = textInputEmail.getText().toString();
        password = textInputPassword.getText().toString();
        signIn(email,password);
    }

    private void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("!!!", "Sign in success");
                    //goToAddItemActivity();
                    loginComplete();
                } else {
                    Log.d("!!!", "sign in failed");
                    Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
}
