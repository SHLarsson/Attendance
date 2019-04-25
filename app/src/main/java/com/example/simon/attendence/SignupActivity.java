package com.example.simon.attendence;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private EditText textInputEmail;
    private EditText textInputName;
    private EditText textInputPassword;
    private Button submitButton;

    String email;
    String name;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        Spinner schoolSpinner = (Spinner) findViewById(R.id.schoolSpinner);
        Spinner classSpinner = (Spinner) findViewById(R.id.classSpinner);

        ArrayAdapter<CharSequence> schoolAdapter = ArrayAdapter.createFromResource(this,
                R.array.schools, android.R.layout.simple_spinner_item);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolSpinner.setAdapter(schoolAdapter);

        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(this,
                R.array.nackademin_classes, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);

        textInputEmail = (EditText) findViewById(R.id.input_email);
        textInputName = (EditText) findViewById(R.id.input_fullName);
        textInputPassword = (EditText) findViewById(R.id.input_password);
        submitButton = (Button) findViewById(R.id.loginButton);


    }
    void signupComplete(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    void signUpAttempt(View view){
        email = textInputEmail.getText().toString();
        name = textInputName.getText().toString();
        password = textInputPassword.getText().toString();
        createAccount(email,name,password);
    }

    private void createAccount (String email,String name, String password) {
        final String fullName = name;
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful()) {
                    Log.d("!!!", "user created");
                    User user = new User(fullName,"MA18");
                    
                    signupComplete();

                } else {
                    Log.d("!!!", "create user failed" , task.getException());

                }
            }
        });
    }
}