package com.example.simon.attendence;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckinActivity extends AppCompatActivity {

    FirebaseFirestore db;

    private boolean checkedin = false;
    private  Button checkinButton;
    private TextView infoText;
    private String fullName = "Simon Larsson";
    private WifiManager wifiManager;
    private String date;

    private List<ScanResult> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        db = FirebaseFirestore.getInstance();

        Date currentTime = Calendar.getInstance().getTime();

        Date currentDate = Calendar.getInstance().getTime();

        String pattern = "HH:mm";
        String datePattern = "dd-MMM";
        SimpleDateFormat timeFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);


        TextView displaySort = findViewById(R.id.time);
        displaySort.setText("You need to check in");
        displaySort.setText(timeFormat.format(currentTime) + "");

        date = dateFormat.format(currentDate) + "";

        infoText = findViewById(R.id.infoText);
        checkinButton = (Button)findViewById(R.id.button);

        Map<String, Object> data = new HashMap<>();
        data.put("Time",timeFormat.format(currentTime) + "");// make time = timeFormat.format(currentTime) + "" variable
        db.collection(date).document(fullName).set(data);//time and fullName add

        //CollectionReference itemsRef = db.collection("attaendance");
        //itemsRef.add(data);
        //scanWifi();

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
            checkinButton.setBackgroundColor(Color.parseColor("#FF4181"));
            infoText.setText("You checked in at");
        }else{
            checkinButton.setText("Check in");
            checkinButton.setBackgroundColor(Color.parseColor("#03DAC6"));
            infoText.setText("You're not checked in");
        }
    }
    void alert(){
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to check out?")
                .setMessage("checking out will end your attendance!")
                .setPositiveButton("Check out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedin = false;
                        checkinButton.setText("Check in");
                        checkinButton.setBackgroundColor(Color.parseColor("#03DAC6"));
                        infoText.setText("You're not checked in");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss
                    }
                })
                .show();
    }

    void checkWifi(){
        if(!wifiManager.isWifiEnabled()){
            //Toast.makeText(this,"Wifi is disabled");

        }
    }

    void scanWifi(){
        registerReceiver(wifiReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Log.d("SSIDNAME",results.toString());
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
             unregisterReceiver(this);

             for(ScanResult scanResult : results){
             }
        }
    };

    @Override
    public void onBackPressed(){/*Do nothing*/}

}
