### `com.sadellie.numberhub.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`

Read (boring): https://developer.android.com/about/versions/14/behavior-changes-14#runtime-receivers-exported

### `android.permission.INTERNET`

Used in **Unit Converter** to update currency rates. Requests are made only when you select a currency unit.

### `android.permission.ACCESS_NETWORK_STATE`

Used in Unit Converter as a callback. Retries to update currency rates if there was an error (no network, for example) and the Internet connection is back.

### `android.permission.WAKE_LOCK`

Not used explicitly. Added automatically by Widget feature.

### `android.permission.RECEIVE_BOOT_COMPLETED`

Not used explicitly. Added automatically by Widget feature.

### `android.permission.FOREGROUND_SERVICE`

Not used explicitly. Added automatically by Widget feature.

### Non-free network service

Non-free means that you can't host the given service on your machine.

The app uses [Free Currency Rates API by fawazahmed0](https://github.com/fawazahmed0/exchange-api). Requests are send to `cdn.jsdelivr.net`. 

