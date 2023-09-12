package com.example.auton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.auton.databinding.ActivityMaps2Binding;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity_Workshop extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    double longitudeGPS, latitudeGPS;
    String longitudeStr, latitudeStr, activityStr, workshopNameStr, userLat, userLong, userName;
    Button currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    CardView cardView;
    TextView textView;
    String provider;
    MarkerOptions origin, destination;
    LocationManager locationManager;
    private Geocoder geocoder;
    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private final int ACCESS_LOCATION_REQUEST_CODE = 100;
    private ActivityResultLauncher<IntentSenderRequest> resolutionForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding= ActivityMaps2Binding.inflate(getLayoutInflater());
        /*binding= ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/
        setContentView(R.layout.activity_maps_workshop);

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
            //currentlocation.setVisibility(View.GONE);
            currentlocation.setText("OK");
            cardView.setVisibility(View.GONE);
            // binding.textview.setVisibility(View.GONE);
            workshopNameStr = extras.getString("name");
            userLong = extras.getString("userLongitude");
            userLat = extras.getString("userLatitude");
            userName = extras.getString("userName");
        }

        currentlocation.setOnClickListener(view -> {
            if (activityStr.equals("workshopMap")) {
                startActivity(new Intent(this, workshop_HomePage.class));
                finishAffinity();
            }
        });

        Toast.makeText(this, "LONG" + longitudeStr, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "LAT" + latitudeStr, Toast.LENGTH_SHORT).show();

        /* enable location and permissions */
        enableLocationSettings();
        resolutionForResult = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                getLocation();
            } else {
                /* permissions not Granted */
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableLocationSettings() {

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10 * 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(MapsActivity_Workshop.this).checkLocationSettings(builder.build()).addOnSuccessListener(MapsActivity_Workshop.this, (LocationSettingsResponse response) -> {
            getLocation();
        }).addOnFailureListener(MapsActivity_Workshop.this, ex -> {
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);

        // Get the location from the given provider
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);

        if (location != null) onLocationChanged(location);
        else location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        } else {
            Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
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


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
            //zooToUserLocation();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //we can show user a dialog why this permission is necessary
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);

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
          /*  Address address = addresses.get(0);
            LatLng london = new LatLng(Double.parseDouble(latitudeStr),Double.parseDouble(longitudeStr));
            Log.e("", "location: "+london);
            MarkerOptions markerOptions = new MarkerOptions().position(london).title("Your location");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));
            currentlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activityStr.equals("worker")){
                        worker_Registration.mapinterface.location(latitudeStr,longitudeStr); //Interface
                        finish();
                    }else {
                        user_Book_Service.mapinterface.location(latitudeStr,longitudeStr); //Interface
                        finish();
                    }
                }
            });*/


        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        //setting transportation mode
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyAKueYWFVF6M472H_4nPwZEyxhkfNOmj8o";
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        longitudeGPS = location.getLongitude();
        latitudeGPS = location.getLatitude();

        mMap.clear();
        origin = new MarkerOptions().position(new LatLng(Double.parseDouble(latitudeStr), Double.parseDouble(longitudeStr))).title(workshopNameStr).snippet("origin");
        destination = new MarkerOptions().position(new LatLng(Double.parseDouble(userLat), Double.parseDouble(userLong))).title(userName).snippet("destination");

        String url = getDirectionsUrl(origin.getPosition(), destination.getPosition());

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

        mMap.addMarker(origin);
        mMap.addMarker(destination);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin.getPosition(), 10));


    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the JSON format
     */
    @SuppressLint("StaticFieldLeak")
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

            for (int i = 0; i < result.size(); i++) {

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

            // Drawing polyline in the Google Map
            if (points.size() != 0) mMap.addPolyline(lineOptions);
        }
    }


}