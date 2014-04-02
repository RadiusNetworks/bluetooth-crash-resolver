package com.radiusnetworks.bluetoothcrashresolver;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.radiusnetworks.bluetooth.BluetoothCrashResolver;

/**
 * Created by dyoung on 3/31/14.
 */
public class BluetoothCrashResolverApplication extends Application {

    public static final String TAG = "BluetoothCrashResolverApplication";
    private BluetoothCrashResolver bluetoothCrashResolver = null;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "Dummy service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "Dummy service disconnected");
        }
    };

    /**
     * This method will get called automatically when the app is launched, or when the
     * StartupBroadcastReceiver gets word of a phone boot up.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Application onCreate");
        // Start looking for bluetooth crashes
        bluetoothCrashResolver = new BluetoothCrashResolver(this);
        bluetoothCrashResolver.start();
        bindService(new Intent(this, DummyService.class), connection, Context.BIND_AUTO_CREATE);
    }

    public BluetoothCrashResolver getBluetoothCrashResolver() {
        return bluetoothCrashResolver;
    }


}
