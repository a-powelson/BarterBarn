package com.example.barterbarn;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHandler {

    private final int LOCATION_PERMISSION = 0;
    private final int NOTIFICATION_PERMISSION_CODE = 123;
    private final Context context;
    private final AppCompatActivity activity;

    public PermissionHandler(Context context) {
        this.context = context;
        this.activity = (AppCompatActivity) context;
    }

    public void requestLocationPermissionIfNotGranted() {
        if (locationPermissionGranted()) {
            displayLocationPermissionGrantedMessage();
        } else {
            requestLocationPermission();
        }
    }

    private boolean locationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(context, "Permission is needed", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);
        }
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, NOTIFICATION_PERMISSION_CODE );
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Permission request was interrupted
        if (permissions.length == 0 || grantResults.length == 0) {
            return;
        }

        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayLocationPermissionGrantedMessage();
            } else {
                createLocationRequestAlert();
            }
        }
        if (requestCode == 123 ) {

            // If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }

    private void createLocationRequestAlert() {
        new AlertDialog.Builder(context)
                .setTitle("Permission needed")
                .setMessage("Location permission is necessary for Barter Barn!")
                .setPositiveButton("OK", (dialog, which) ->
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void displayLocationPermissionGrantedMessage() {
        //Toast.makeText(context, "Permission granted!", Toast.LENGTH_SHORT).show();

        TextView locationStatus = activity.findViewById(R.id.locationStatusLabel);
        locationStatus.setText(R.string.locationPermissionGranted);
    }
}
