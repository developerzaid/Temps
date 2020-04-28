package com.hazyaz.temps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hazyaz.temps.CallLogs.GetCallLogs;
import com.hazyaz.temps.GPS.GPSTracker;
import com.hazyaz.temps.Media.GetImages;
import com.hazyaz.temps.Message.MessageReceiver;

public class MainActivity extends AppCompatActivity {
    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public final int EXTERNAL_REQUEST = 138;
    private FirebaseAuth mAuth;
    public static Context context;
    GPSTracker gps;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        requestForPermission();

        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
//        GetImages obj = new GetImages();


                                                        //For Longitude and Latitude
//        gps = new GPSTracker(MainActivity.this);
//
//        // check if GPS enabled
//        if(gps.canGetLocation()){
//
//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("users").child("username").child("Location").child("latitude").setValue(latitude);
//        mDatabase.child("users").child("username").child("Location").child("longitude").setValue(longitude);
//
//
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
//                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//            Log.d("aaaa","Your Location is - \nLat: "
//                    + latitude + "\nLong: " + longitude);
//        }else{
//
//            gps.showSettingsAlert();
//        }

                                                                    // Call Records
//        GetCallLogs getCallLogs=new GetCallLogs();
//        String callrec = getCallLogs.getCallDetails(getApplicationContext());
//        Log.d("abc", callrec);
//
//
//                                                                        //Messages
//        MessageReceiver messageReceiver=new MessageReceiver();
//        Log.d("xxx",messageReceiver.getAllSms(getApplicationContext()));
//

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent t = new Intent(MainActivity.this, Login.class);
            startActivity(t);
        }
    }


    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));

    }
}
