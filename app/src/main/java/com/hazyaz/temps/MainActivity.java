package com.hazyaz.temps;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private static final int APP_PERMISSION_REQUEST = 1;
    private static final String TAG = "Recorder";
    public static Context context;
    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_BACKGROUND_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.BIND_DEVICE_ADMIN
    };
    ComponentName mDeviceAdminSample;
    DevicePolicyManager mDPM;
    public final int EXTERNAL_REQUEST = 138;
    private FirebaseAuth mAuth;
    private Button whatsAppBtn;
    private Button notificationBtn;
    private Button callLogs;
    private Button contacts;
    private Button messages;
    private Button location;
    private Button takeImage;
    private Button takeVideo;
    private Button screenshot;
    private Button enableAdmin;
    private Button chnagePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

        context = getApplicationContext();
        requestForPermission();
//gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg this is for video recording

        mDeviceAdminSample = new ComponentName(this, DeviceAdminControl.class);
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);



//        to get whatsapp images
//        whatsAppBtn = findViewById(R.id.whatsappBtn);
//        whatsAppBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GetImages obj = new GetImages();
//                Toast.makeText(getApplicationContext(), "DEVELOPED", Toast.LENGTH_LONG).show();
//            }
//        });

//        to get noticatin s
//        notificationBtn = findViewById(R.id.notificationBtn);
//        notificationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                showPermissionDialog();
//                Toast.makeText(getApplicationContext(), "DEVELOPED", Toast.LENGTH_LONG).show();
//            }
//        });

//to get call logs
//        callLogs = findViewById(R.id.callLogs);
//        callLogs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GetCallLogs getCallLogs = new GetCallLogs();
////                String callrec = getCallLogs.getCallDetails(getApplicationContext());
////                Log.d("call logs", callrec);
//                Toast.makeText(getApplicationContext(), "DEVELOPED", Toast.LENGTH_LONG).show();
//            }
//        });

// to get messages
//        messages = findViewById(R.id.messages);
//        messages.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onClick(View v) {
////                MessageReceiver messageReceiver = new MessageReceiver();
////                String messages = messageReceiver.getAllSms(getApplicationContext());
////                Log.d("messages ", messages);
//                Toast.makeText(getApplicationContext(), "DEVELOPED", Toast.LENGTH_LONG).show();
//            }
//        });


//        to get Contacts
//        contacts = findViewById(R.id.contacts);
//        contacts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Contacts contacts = new Contacts();
////                contacts.getContactList(getContentResolver());
//                Toast.makeText(getApplicationContext(), "DEVELOPED", Toast.LENGTH_LONG).show();
//            }
//        });

//        get location
//        location = findViewById(R.id.location);
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
////                Log.d("loasdsd", "" + gpsTracker.getLocation());
//                Toast.makeText(getApplicationContext(), "DEVELOPED", Toast.LENGTH_LONG).show();
//            }
//        });

        takeImage = findViewById(R.id.takeImage);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startService(new Intent(MainActivity.this, CameraImage.class));
                Toast.makeText(getApplicationContext(), "DEVELOPED", Toast.LENGTH_LONG).show();
            }
        });

        takeVideo = findViewById(R.id.takevideo);
        takeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CameraVideo.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startService(intent);
//                finish();
                Toast.makeText(getApplicationContext(), "Under development", Toast.LENGTH_LONG).show();
            }
        });

//        screenshot = findViewById(R.id.screenshotBtn);
//        screenshot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Under development", Toast.LENGTH_LONG).show();
//
//            }
//        });

        enableAdmin = findViewById(R.id.enableAdmin);
        enableAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDPM != null && mDPM.isAdminActive(mDeviceAdminSample)) {
                    Toast.makeText(getApplicationContext(), "Admin Active ", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                            mDeviceAdminSample);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "You need to activate Device Administrator to perform phonelost tasks!");
                    startActivityForResult(intent, 45);
                }
//                mDPM.lockNow();    lock the device
//                mDPM.wipeData(0);  wipe the device
//
//                int maxFailedPw = 1;
//                mDPM.setMaximumFailedPasswordsForWipe(mDeviceAdminSample, maxFailedPw);  reset phone after 2 ettempts

            }

        });


        chnagePassword = findViewById(R.id.changePass);
        chnagePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDPM.lockNow();
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
            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
            if (!(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_DEVICE_ADMIN))) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
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
