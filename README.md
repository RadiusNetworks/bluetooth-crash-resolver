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


##License  
Apache 2.0 (Open Source)

##More details on the crash bug

[Android Bug Report #67272](https://code.google.com/p/android/issues/detail?id=67272)

[Android iBeacon Library Issue #16](https://github.com/RadiusNetworks/android-ibeacon-service/issues/16)

