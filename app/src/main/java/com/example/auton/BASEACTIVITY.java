package com.example.auton;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;

abstract class BASEACTIVITY extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    LocationManager locationManager;
    double longitudeBest = 0.0, latitudeBest = 0.0;
    //  MAP
    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private ActivityResultLauncher<IntentSenderRequest> resolutionForResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkLocation()) {
            toggleBestUpdates();
        }
        resolutionForResult = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (checkLocation()) {
                    toggleBestUpdates();
                }
            } else {
                /* permissions not Granted */
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10 * 1000).setWaitForAccurateLocation(false).setMinUpdateIntervalMillis(3000).setMaxUpdateDelayMillis(100).build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(this).checkLocationSettings(builder.build()).addOnSuccessListener(this, (LocationSettingsResponse response) -> {

            toggleBestUpdates();

        }).addOnFailureListener(this, ex -> {
            if (ex instanceof ResolvableApiException) {
                try {
                    IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(((ResolvableApiException) ex).getResolution()).build();
                    resolutionForResult.launch(intentSenderRequest);
                } catch (Exception exception) {
                    Toast.makeText(this, "" + exception, Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "enableLocationSettings: " + exception);
                }
            }
        });
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkLocation()) {

                    toggleBestUpdates();

                }
            } else {
                // Permission is denied, handle this case or show an explanation to the user
                // ...
            }
        }
    }

    public void toggleBestUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerBest);
        }
    }
}
