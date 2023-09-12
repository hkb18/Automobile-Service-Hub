package com.example.auton;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityUserBookService2Binding;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class user_BookService2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, mapinterface {
    private ActivityUserBookService2Binding binding;
    private static final int PERMISSIONS_REQUEST_LOCATION = 123;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    LocationManager locationManager;
    double longitudeBest = 0.0, latitudeBest = 0.0;
    private ActivityResultLauncher<IntentSenderRequest> resolutionForResult;
    int totalPrice;
    String modelstr, datestr, timestr, locationstr, currentlocationstr, servicenameStr, serviceStr, imgStr, getPriceServiceTypeStr, carbodytypePriceStr = "", carmodelPriceStr = "";
    String brandStr, username, latitudeStr, longitudeStr, s1 = "";
    String[] time = {"Morning", "Afternoon", "Evening"};
    private int mYear, mMonth, mDay;
    DatabaseReference databaseReference;
    static mapinterface mapinterface;

    CountDownTimer mCountDownTimer;
    int i = 0;

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

    private boolean checkLocation() {
        if (!isLocationEnabled()) showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10 * 1000).setWaitForAccurateLocation(false).setMinUpdateIntervalMillis(3000).setMaxUpdateDelayMillis(100).build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(user_BookService2.this).checkLocationSettings(builder.build()).addOnSuccessListener(user_BookService2.this, (LocationSettingsResponse response) -> {

            toggleBestUpdates();

        }).addOnFailureListener(user_BookService2.this, ex -> {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBookService2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.progressBar.setProgress(i);
        mCountDownTimer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                i++;
                binding.progressBar.setProgress(i * 100 / (5000 / 1000));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                binding.progressBar.setProgress(100);
                binding.loadingPanel.setVisibility(View.GONE);
            }
        };
        mCountDownTimer.start();


        Bundle extras = getIntent().getExtras();
        username = extras.getString("Username");
        servicenameStr = extras.getString("Service");//getBrand(),getModel()
        imgStr = extras.getString("img");
        serviceStr = extras.getString("servicetype"); //Batteries,Tyres and Wheelcare
        getPriceServiceTypeStr = extras.getString("Price");

        binding.tvServiceType.setText(servicenameStr);
        binding.tvService.setText(serviceStr);
        Glide.with(getApplicationContext()).load(imgStr).into(binding.imageView);


        SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        s1 = sh.getString("Username", "");

        // MAP
        mapinterface = this;
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

        setCarBrandAdapter();

        setCarTypeAdapter();

        binding.serviceTime.setOnItemSelectedListener(this);
        ArrayAdapter ac = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, time);
        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.serviceTime.setAdapter(ac);

        //  LOCATION
        binding.buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (longitudeBest != 0.0 || latitudeBest != 0.0) {
                    Intent i = new Intent(getApplicationContext(), MapsActivity2.class);
                    i.putExtra("longitude", String.valueOf(longitudeBest));
                    i.putExtra("latitude", String.valueOf(latitudeBest));
                    i.putExtra("activity", "user2");
                    startActivity(i);
                } else {
                    Toast.makeText(user_BookService2.this, "not able to get permission", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //  DATE
        binding.buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view == binding.buttonDate) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    long minDate = c.getTimeInMillis();


                    DatePickerDialog datePickerDialog = new DatePickerDialog(user_BookService2.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthofYear, int dayofMonth) {
                            binding.edittextDate.setText(dayofMonth + "-" + (monthofYear + 1) + "-" + year);
                            datestr = binding.edittextDate.getText().toString();
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMinDate(minDate);
                    datePickerDialog.show();
                }
            }
        });
        // BOOKING
        binding.btnBookService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //  SPINNER
                brandStr = binding.carBrand.getSelectedItem().toString();
                modelstr = binding.carModel.getSelectedItem().toString();
                timestr = binding.serviceTime.getSelectedItem().toString();
                locationstr = currentlocationstr;

                if (datestr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter date", Toast.LENGTH_SHORT).show();
                }
                databaseReference.child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Handler handler;
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                totalPrice = Integer.parseInt(carbodytypePriceStr) + Integer.parseInt(carmodelPriceStr) + Integer.parseInt(getPriceServiceTypeStr);
                                Intent i = new Intent(getApplicationContext(), RazorPay.class);
                                i.putExtra("activity", "bookService2");
                                i.putExtra("Username", s1);
                                i.putExtra("CarBrand", brandStr);
                                i.putExtra("CarModel", modelstr);
                                i.putExtra("ServiceType", servicenameStr);
                                i.putExtra("ServiceName", serviceStr);
                                i.putExtra("Date", datestr);
                                i.putExtra("ServiceTime", timestr);
                                i.putExtra("Latitude", latitudeStr);
                                i.putExtra("Longitude", longitudeStr);
                                i.putExtra("Img", imgStr);
                                i.putExtra("totalPrice", String.valueOf(totalPrice));
                                startActivity(i);
                            }
                        }, 2000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(user_BookService2.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setCarTypeAdapter() {
        ArrayList<String> carbodyType = new ArrayList<>();
        ArrayList<carBodyType> cbtPrice = new ArrayList<>();
        databaseReference.child("CAR_BODYTYPE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carbodyType.clear();
                cbtPrice.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    carbodyType.add(dataSnapshot.getKey());
                    carBodyType carBodyType = dataSnapshot.getValue(carBodyType.class);
                    cbtPrice.add(carBodyType);
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(user_BookService2.this, R.layout.simple_spinner_item, carbodyType);
                binding.carModel.setAdapter(brandAdapter);
                binding.carModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        /*String[] modelss=models[position];
                        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelss);
                        carmodel.setAdapter(modelAdapter);*/
                        carbodytypePriceStr = cbtPrice.get(position).getPrice();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCarBrandAdapter() {
        ArrayList<String> carBrand = new ArrayList<>();
        ArrayList<carModel> model = new ArrayList<>();
        databaseReference.child("CAR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carBrand.clear();
                model.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    carBrand.add(dataSnapshot.getKey());
                    carModel model1 = dataSnapshot.getValue(carModel.class);
                    model.add(model1);
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(user_BookService2.this, R.layout.simple_spinner_item, carBrand);
                binding.carBrand.setAdapter(brandAdapter);
                binding.carBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        /*String[] modelss=models[position];
                        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelss);
                        carmodel.setAdapter(modelAdapter);*/
                        carmodelPriceStr = model.get(position).getPrice();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        LocationServices.getSettingsClient(user_BookService2.this).checkLocationSettings(builder.build()).addOnSuccessListener(user_BookService2.this, (LocationSettingsResponse response) -> {
            getLocation();
        }).addOnFailureListener(user_BookService2.this, ex -> {
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
   /*     if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,0,this);
        /*if (location != null) onLocationChanged(location);
        else location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        } else {
            Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
        }*/

    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(),brand[position],Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void location(String latitude, String longitude) {

        latitudeStr = latitude;
        longitudeStr = longitude;
        Log.e("", "latitude: " + latitude + "  Longitude:" + longitude);
        getAddress(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    ///to get place details from lat n log
    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(user_BookService2.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            //add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            // add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            // add = add + "\n" + obj.getSubThoroughfare();

            Log.v("IGA", "Address" + add);

            binding.edittextLocation.setText(add);


            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}