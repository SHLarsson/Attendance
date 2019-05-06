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
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckinActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    FirebaseFirestore db;

    private boolean checkedin = false;
    private  Button checkinButton;
    private TextView infoText;
    private String fullName = "Simon Larsson";
    private WifiManager wifiManager;
    private String date;

    private FirebaseAuth auth;

    private List<ScanResult> results;

    private String username;

    private Map<String, Object> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        db = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


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
        checkinButton = (Button) findViewById(R.id.button);

        data = new HashMap<>();
        data.put("Time", timeFormat.format(currentTime) + "");// make time = timeFormat.format(currentTime) + "" variable
        //CollectionReference itemsRef = db.collection("attendance");
        //itemsRef.add(data);
        //scanWifi();

        if (user != null) {
            Log.d("!!!", user.getUid());

            DocumentReference userRef = db.collection("users").document(user.getUid());

           userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    username = documentSnapshot.getString("username");
                    db.collection(date).document(username).set(data);//time and fullName add
                }
            });
        }
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

    void showPopup(View view){
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.poupup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }else{
            return false;
        }
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
