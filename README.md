Bluetooth Crash Resolver
========================

This project contains an example Android app that makes use of a core class that provides relief for Android Bug 67272. 
This bug in the Bluedroid stack causes crashes in Android's BluetoothService when scanning for BLE devices encounters a 
large number of unique devices.  It is rare for most users but can be problematic for those with apps scanning for
Bluetooth LE devices in the background (e.g. iBeacon-enabled apps), especially when these users
are around Bluetooth LE devices that randomize their mac address.

The core class that provides the functionality is [com.radiusnetworks.bluetooth.BluetoothCrashResolver.](https://github.com/RadiusNetworks/bluetooth-crash-resolver/blob/master/app/src/main/java/com/radiusnetworks/bluetooth/BluetoothCrashResolver.java) Suggested improvements are strongly desired and feedback is encouraged.

The core class can both recover from crashes and prevent crashes from happening in the first place.  In order to 
be able to prevent crashes, the module must be integrated in an app that does the BLE scans, and must make a call
to the class with each scan record received so it can keep track of how many unique devices are likely in Bluedroid's
memory.

The reference app that uses the core class in this project CANNOT prevent crashes because the app does not do its own bluetooth scanning.  It can, however, reactively clear out the list of bluetooth addresses after a crash, making the next crash happen as far in the future as possible.

This app is also available in the [Google Play Store](https://play.google.com/store/apps/details?id=com.radiusnetworks.bluetoothcrashresolver).

##Integrating with your app

If you have an app that does Bluetooth LE scans, then here are the steps you need to take to add this to your app to prevent crashes:

1. Copy BluetoothCrashResolver.java into your codebase

2. In whatever class does Bluetooth LE scanning, construct this object, call start(), and keep using the same object instance for as long as you keep doing scanning:

    bluetoothCrashResolver = new BluetoothCrashResolver(this.getApplicationContext());
    bluetoothCrashResolver.start();

3. Whenever you get a scan callback, make a call like this, passing a reference to your your LeScanCallback object:

    bluetoothCrashResolver.notifyScannedDevice(device, myLeScanCallback);

4. When you stop scanning, call the stop method:

    bluetoothCrashResolver.stop();

By performing the steps above, the module will keep track of how many unique devices are seen, and when they approach the limit of 1990, it will stop doing LeScans, and command a flush of the list.  It will also save the list of recently seen devices to non-volatile storage, so if you stop scanning and start up again later (even after closing and reopening your app), it will resume counting from where it left off.


##License  
Apache 2.0 (Open Source)

##More details on the crash bug

[Android Bug Report #67272](https://code.google.com/p/android/issues/detail?id=67272)

[Android iBeacon Library Issue #16](https://github.com/RadiusNetworks/android-ibeacon-service/issues/16)

[A Solution for Android Bluetooth Crashes](http://developer.radiusnetworks.com/2014/04/02/a-solution-for-android-bluetooth-crashes.html)

