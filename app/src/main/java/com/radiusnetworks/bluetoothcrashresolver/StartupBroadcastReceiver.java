package com.radiusnetworks.bluetoothcrashresolver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

@TargetApi(4)
public class StartupBroadcastReceiver extends BroadcastReceiver
{
    private static final String TAG = "StartupBroadcastReceiver";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive called in startup broadcast receiver for BluetoothCrashResolver");
        // Don't need to do anything here.  BluetoothCrashResolverApplication.onCreate() will get
        // called automatically as part of the Android lifecycle, and this does the real work.
    }
}