Bluetooth Crash Resolver
========================

This project contains an example Android app that makes use of a core class that provides relief for Android Bug 67272. 
This bug in the Bluedroid stack causes crashes in Android's BluetoothService when scanning for BLE devices encounters a 
large number of unique devices.  It is rare for most users but can be problematic for those with apps scanning for
Bluetooth LE devices in the background (e.g. iBeacon-enabled apps), especially when these users
are around Bluetooth LE devices that randomize their mac address.

The core class can both recover from crashes and prevent crashes from happening in the first place.  In order to 
be able to prevent crashes, the module must be integrated in an app that does the BLE scans, and must make a call
to the class with each scan record received so it can keep track of how many unique devices are likely in Bluedroid's
memory.

This app is also available in the [Google Play Store(https://play.google.com/store/apps/details?id=com.radiusnetworks.bluetoothcrashresolver)].


##License  
Apache 2.0 (Open Source)

##More details on the crash bug

[Android Bug Report #67272](https://code.google.com/p/android/issues/detail?id=67272)

[Android iBeacon Library Issue #16](https://github.com/RadiusNetworks/android-ibeacon-service/issues/16)

