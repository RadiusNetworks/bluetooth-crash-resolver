package com.radiusnetworks.bluetoothcrashresolver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * This service does almost nothing.  Its main purpose is to exist so the application is given a higher
 * priority when apps get purged.  The real background function of this application is more simply
 * implemented off of a custom Application class for easier communication with the foreground
 * activity.
 *
 * When the service is shut down by Android, it makes a call to BackgroundCrashResolver.stop() so
 * it cleans itself up and saves its state.
 *
 * Created by dyoung on 3/31/14.
 */
public class DummyService extends Service {

    private final IBinder binder = new LocalBinder();
    private static final String TAG = "DummyService";

    public class LocalBinder extends Binder {
        DummyService getService() {
            return DummyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Dummy service bound");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        boolean result = super.onUnbind(intent);
        Log.d(TAG, "Dummy service unbound");
        return result;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Dummy service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service (and presumably Application) is being destroyed.  We will stop the crash resolver.");
        ((BluetoothCrashResolverApplication)getApplication()).getBluetoothCrashResolver().stop();
    }

}
