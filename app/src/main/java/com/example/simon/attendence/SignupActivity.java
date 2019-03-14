package com.example.simon.attendence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

    }
    void signupComplete(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
