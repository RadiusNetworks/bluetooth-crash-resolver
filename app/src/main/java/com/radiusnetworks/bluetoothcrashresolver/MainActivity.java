package com.radiusnetworks.bluetoothcrashresolver;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import com.radiusnetworks.bluetooth.BluetoothCrashResolver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBluetoothCrashResolver().setUpdateNotifier(updateNotifer);
        updateView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getBluetoothCrashResolver().setUpdateNotifier(null);
    }

    public BluetoothCrashResolver getBluetoothCrashResolver() {
        return ((BluetoothCrashResolverApplication)getApplication()).getBluetoothCrashResolver();
    }

    public BluetoothCrashResolver.UpdateNotifier updateNotifer = new BluetoothCrashResolver.UpdateNotifier() {
        public void dataUpdated() {
            runOnUiThread(new Runnable() {
                public void run() {
                    updateView();
                }
            });
        }
    };

    public void updateView() {
        TextView stateTextView = (TextView) findViewById(R.id.bluetooth_state);
        if (BluetoothAdapter.getDefaultAdapter() != null && BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            stateTextView.setText("ON");
        }
        else {
            stateTextView.setText("OFF");
        }
        TextView crashCountTextView = (TextView) findViewById(R.id.crash_count);
        crashCountTextView.setText(""+getBluetoothCrashResolver().getDetectedCrashCount());
        TextView recoveryCountTextView = (TextView) findViewById(R.id.recovery_count);
        recoveryCountTextView.setText(""+getBluetoothCrashResolver().getRecoveryAttemptCount());
        TextView lastCrashTimeTextView = (TextView) findViewById(R.id.last_crash_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        if (getBluetoothCrashResolver().getLastBluetoothCrashDetectionTime() == 0) {
            lastCrashTimeTextView.setText("Unknown");
        }
        else {
            lastCrashTimeTextView.setText(format.format(new Date(getBluetoothCrashResolver().getLastBluetoothCrashDetectionTime())));
        }
        TextView attributionTextView = (TextView) findViewById(R.id.attribution);
        attributionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://developer.radiusnetworks.com/ibeacon/android/android-bug-67272"));
                startActivity(browserIntent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem settings = menu.add("Force Flush");
        settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    if (BluetoothAdapter.getDefaultAdapter().isDiscovering()) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Flush in Progress");
                        builder.setMessage("A flush is already in progress.  Please wait 10 seconds for it to complete.");
                        builder.setPositiveButton(android.R.string.ok, null);
                        builder.show();
                    }

                    else {
                        new ForceFlushTask().execute(null, null, null);
                    }
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Bluetooth not enabled");
                    builder.setMessage("Please terminate all apps that use bluetooth, then turn bluetooth on and try again.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                            System.exit(0);
                        }
                    });
                    builder.show();
                }
                return true;
            }
        });
        return true;
    }


    private class ForceFlushTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            getBluetoothCrashResolver().forceFlush();
            return null;
        }
    }
}
