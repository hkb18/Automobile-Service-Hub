package com.example.auton;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.auton.databinding.ActivityMaps2Binding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    String longitudeStr, latitudeStr, activityStr, workshopNameStr;
    Button currentlocation;
    CardView cardView;
    TextView textView;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private final int ACCESS_LOCATION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps2);
       /* binding= ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        cardView = findViewById(R.id.textview);
        textView = findViewById(R.id.textview1);
        currentlocation = findViewById(R.id.btn);

        geocoder = new Geocoder(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Bundle extras = getIntent().getExtras();
        longitudeStr = extras.getString("longitude");
        latitudeStr = extras.getString("latitude");
        activityStr = extras.getString("activity");
        if (activityStr.equals("workshopMap")) {
            currentlocation.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            // binding.textview.setVisibility(View.GONE);
            workshopNameStr = extras.getString("name");
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
       /* mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
            //zooToUserLocation();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                //we can show user a dialog why this permission is necessary
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);

            }
        }
        try {
            List<Address> addresses = geocoder.getFromLocationName("london", 1);
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//              //  LatLng london = new LatLng(address.getLatitude(), address.getLongitude());
//                LatLng london = new LatLng(Double.parseDouble(latitudeStr),Double.parseDouble(longitudeStr));
//                MarkerOptions markerOptions = new MarkerOptions().position(london).title(address.getLocality());
//                mMap.addMarker(markerOptions);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));
//
//            }
            Address address = addresses.get(0);
            LatLng london = new LatLng(Double.parseDouble(latitudeStr), Double.parseDouble(longitudeStr));
            Log.e("", "location: " + london);
            MarkerOptions markerOptions = new MarkerOptions().position(london).title("Your location");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));
            currentlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activityStr.equals("worker")) {
                        worker_Registration.mapinterface.location(latitudeStr, longitudeStr); //Interface
                        finish();
                    } else if (activityStr.equals("user2")) {
                        user_BookService2.mapinterface.location(latitudeStr, longitudeStr); //Interface
                        finish();
                    } else if (activityStr.equals("admin")) {
                        admin_Add_Workshop.mapinterface.location(latitudeStr, longitudeStr); //Interface
                        finish();
                    } else {
                        user_Book_Service.mapinterface.location(latitudeStr, longitudeStr); //Interface
                        finish();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        Log.d(TAG, "onMapLongClick: " + latLng);
        mMap.clear();
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streetAddress = address.getAddressLine(0);
                mMap.addMarker(new MarkerOptions().position(latLng).title(streetAddress).draggable(true));
                Log.e("", "latLang: " + latLng.latitude);
                Log.e("", "latLang: " + latLng.longitude);
                latitudeStr = String.valueOf(latLng.latitude);
                longitudeStr = String.valueOf(latLng.longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {
        Log.d(TAG, "onMarkerDrag: ");
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        Log.d(TAG, "onMarkerDragEnd: ");
        LatLng latLng = marker.getPosition();
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streetAddress = address.getAddressLine(0);
                marker.setTitle(streetAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {
        Log.d(TAG, "onMarkerDragStart: ");
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void zooToUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                mMap.addMarker((new MarkerOptions().position(latLng)));
            }
        });

    }
}