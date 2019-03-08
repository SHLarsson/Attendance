package com.example.simon.attendence;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class CheckinActivity extends AppCompatActivity {

    private boolean checkedin = false;
    private  Button checkinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        Date currentTime = Calendar.getInstance().getTime();

        String pattern = "HH:mm";
        SimpleDateFormat timeFormat = new SimpleDateFormat(pattern);
        // api 21
        TextView displaySort = findViewById(R.id.time);
        displaySort.setText("You need to check in");
        displaySort.setText(timeFormat.format(currentTime) + "");

        checkinButton = (Button)findViewById(R.id.button);
    }
    void checkin(View view){

        if(checkedin){
            alert();

        }else{
            checkedin = !checkedin;
        }

        Button checkinButton = (Button)findViewById(R.id.button);
        if(checkedin){
            checkinButton.setText("Check out");
            checkinButton.setBackgroundColor(Color.parseColor("#F20069"));
        }else{
            checkinButton.setText("Check in");
            checkinButton.setBackgroundColor(Color.parseColor("#03DAC6"));

        }
    }
    void alert(){
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to check out?")
                .setMessage("checking out will end your attendance!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedin = false;
                        checkinButton.setText("Check in");
                        checkinButton.setBackgroundColor(Color.parseColor("#03DAC6"));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss
                    }
                })
                .show();
    }
}
