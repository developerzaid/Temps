package com.hazyaz.temps.notification;


import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.NonNull;


public class NotificationService extends NotificationListenerService {

    private final String TAG = "NLService";

    // bind and unbind seems to make it work with Android 6...
    // but is never called with Android 4.4...
    @Override
    public IBinder onBind(Intent mIntent) {
        IBinder mIBinder = super.onBind(mIntent);
        Log.i(TAG, "onBind");
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent mIntent) {
        boolean mOnUnbind = super.onUnbind(mIntent);
        Log.i(TAG, "onUnbind");

        try {
        } catch (Exception e) {
            Log.e(TAG, "Error during unbind", e);
        }
        return mOnUnbind;
    }

    // onCreate is called with Android 4.4
    // because the service is explicitly started from the MainActivity.
    // Not on Android 6 where the system binds this service itself...

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "**********  onCreate");
    }

    @Override
    public void onNotificationPosted(@NonNull StatusBarNotification sbn) {

        Bundle extras = sbn.getNotification().extras;

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)) {
            Parcelable[] b = (Parcelable[]) extras.get(Notification.EXTRA_MESSAGES);

            if (b != null) {
                String content = "";
                for (Parcelable tmp : b) {

                    Bundle msgBundle = (Bundle) tmp;
                    content = content + msgBundle.getString("text") + "\n";
                    Log.d(TAG, " 455555555555555555 " + content);

                    /*Set<String> io = msgBundle.keySet(); // To get the keys available for this bundle*/

                }
            }
        }


        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName() + "\t\t\t" + extras);


        if (extras.containsKey("android.text")) {
            if (extras.getCharSequence("android.text") != null) {
                String text = extras.getCharSequence("android.text").toString();
                Log.d(TAG, "  " + text);

            }


        }

        if (extras.containsKey("android.title")) {
            Log.d(TAG, "  " + extras.getString("android.title"));
        }

    }


    @Override
    public void onNotificationRemoved(@NonNull StatusBarNotification sbn) {
        Log.i(TAG, "********** onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName() + "\t");
    }


}