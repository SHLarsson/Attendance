package com.example.simon.attendence;

import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class CheckinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        Date currentTime = Calendar.getInstance().getTime();

        String pattern = "HH-ss";
       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        // api 21
        TextView displaySort = findViewById(R.id.time);
        displaySort.setText(currentTime+"");
    }
}
