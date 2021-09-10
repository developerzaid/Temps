package com.hazyaz.temps;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DeviceAdminControl extends DeviceAdminReceiver {


    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "Enabled admin", Toast.LENGTH_LONG).show();


    }

    @Nullable
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return null;
    }

    @Override
    public void onDisabled(Context context, Intent intent) {

    }

    @Override
    public void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {

    }

}

