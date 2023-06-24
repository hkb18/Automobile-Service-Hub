package com.example.auton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class user_Book_Service extends AppCompatActivity implements AdapterView.OnItemSelectedListener,mapinterface {
    Button date,location,booking;
    int i=0;

    String sysTime;

    private static final int PERMISSIONS_REQUEST_LOCATION = 123;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    LocationManager locationManager;
    double longitudeBest=0.0, latitudeBest=0.0;
    private ActivityResultLauncher<IntentSenderRequest> resolutionForResult;
    String modelstr,typestr,datestr,timestr,modestr,locationstr,currentlocationstr,servicenameStr;
    EditText edittextdate,edittextlocation;
    Spinner carbrand,carmodel,servicetype,servicetime,paymentmode;
    TextView serviceType;
    /*String[] brand={"Maruti Suzuki","Hyundai","Tata","Mahindra","Kia","Toyota","Honda","Renault","Volkswagen","Benz","BMW"};
    String[][] models={{"Alto","Wagon R","800","Baleno","Swift","Ciaz","Ignis","Dzire"},
            {"Santro","i 10","i 20","Venuw","Creta","Eon","Alcazar"},
            {"Nexon","Punch","Harrier","Safari","Altroz"},
            {"Thar","Scorpio","Bolero","XUV 700","XUV 300"},
            {"Seltos","Sonet","Carnival","Carens"},
            {"Crysta","Fortuner","Innova,","Glanza","Vellfire","Ertiga"},
            {"City","Amaze","Jazz","Civic"},
            {"Duster","Kwid","Triber","Kiger"},
            {"Skoda","Polo","Virtus","Taigun","Tiguan"},
            {"AMG GT 63 S E","Benz G-Class","Benz C-Class"},
            {"BMW XM"," BMW 2 Series","BMW M340i"," BMW X1"}};*/
    String brandStr,username,latitudeStr,longitudeStr;
    String[] type={"Normal Service","Flat Tyre","Flat Battery","Car Wash","Recovery","Engine Trouble"};
    String[] time={"Morning","Afternoon","Evening"};
    String[] payment={"UPI Payment","COD","Net Banking"};
    private int mYear,mMonth,mDay;
    DatabaseReference databaseReference;

    static mapinterface mapinterface;

    //  MAP
    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            Toast.makeText(user_Book_Service.this, "Best " + longitudeBest, Toast.LENGTH_SHORT).show();
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

        LocationServices.getSettingsClient(user_Book_Service.this).checkLocationSettings(builder.build()).addOnSuccessListener(user_Book_Service.this, (LocationSettingsResponse response) -> {

            toggleBestUpdates();

        }).addOnFailureListener(user_Book_Service.this, ex -> {
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
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE    );
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerBest);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_service);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        Bundle extras=getIntent().getExtras();
        username= extras.getString("Username");
        servicenameStr=extras.getString("Service");
        Toast.makeText(getApplicationContext(), "Username:"+username, Toast.LENGTH_SHORT).show();

        // MAP
        mapinterface=this;
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

        edittextlocation=findViewById(R.id.edittextLocation);
        edittextdate=findViewById(R.id.edittextDate);
        carmodel=findViewById(R.id.carModel);
        carbrand=findViewById(R.id.carBrand);

        setCarBrandAdapter();

        setCarTypeAdapter();

        serviceType=findViewById(R.id.tvServiceType);
        serviceType.setText(servicenameStr);

        /*servicetype.setOnItemSelectedListener(this);
        ArrayAdapter st=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,type);
        st.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicetype.setAdapter(st);*/

        servicetime=findViewById(R.id.serviceTime);
        servicetime.setOnItemSelectedListener(this);
        ArrayAdapter ac=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,time);
        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicetime.setAdapter(ac);

        date=findViewById(R.id.buttonDate);
        location=findViewById(R.id.buttonLocation);
        servicetime=findViewById(R.id.serviceTime);

        paymentmode=findViewById(R.id.paymentMode);
        paymentmode.setOnItemSelectedListener(this);
        ArrayAdapter pm=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,payment);
        pm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentmode.setAdapter(pm);





        //  LOCATION
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(longitudeBest!=0.0 || latitudeBest!=0.0) {
                        Intent i = new Intent(getApplicationContext(), MapsActivity2.class);
                        i.putExtra("longitude", String.valueOf(longitudeBest));
                        i.putExtra("latitude", String.valueOf(latitudeBest));
                        i.putExtra("activity","user");
                        startActivity(i);


                    }else {
                        Toast.makeText(user_Book_Service.this, "not able to get permission", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        //  DATE
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view==date)
                {
                    final Calendar c=Calendar.getInstance();
                    mYear=c.get(Calendar.YEAR);
                    mMonth=c.get(Calendar.MONTH);
                    mDay=c.get(Calendar.DAY_OF_MONTH);
                    long minDate = c.getTimeInMillis();


                    DatePickerDialog datePickerDialog=new DatePickerDialog(user_Book_Service.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthofYear, int dayofMonth) {
                            edittextdate.setText(dayofMonth +"-"+(monthofYear+1)+"-"+year);
                            datestr=edittextdate.getText().toString();
                        }
                    },mYear,mMonth,mDay);
                    datePickerDialog.getDatePicker().setMinDate(minDate);
                    datePickerDialog.show();
                }
            }
        });

        booking=findViewById(R.id.btn_BookService);
        booking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    //  SPINNER
                    brandStr = carbrand.getSelectedItem().toString();
                    modelstr = carmodel.getSelectedItem().toString();
                    typestr = servicetype.getSelectedItem().toString();
                    timestr = servicetime.getSelectedItem().toString();
                    locationstr = currentlocationstr;
                    modestr = paymentmode.getSelectedItem().toString();

                if (datestr.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter date", Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG", "Username: "+username+""+brandStr+""+modelstr+""+typestr+""+datestr+""+timestr+""+latitudeStr+""+longitudeStr+""+modestr);
                    databaseReference.child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Handler handler;
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                   //Intent i = new Intent(getApplicationContext(), user_View_Booked_Service.class);
                                    Intent i = new Intent(getApplicationContext(), RazorPay.class);
                                    i.putExtra("Username", username);
                                    startActivity(i);
                                }
                            }, 3000);


                                sysTime=String.valueOf(System.currentTimeMillis());
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("Username").setValue(username);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("CarBrand").setValue(brandStr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("CarModel").setValue(modelstr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("ServiceType").setValue(typestr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("Date").setValue(datestr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("ServiceTime").setValue(timestr);
//                                databaseReference.child("Users").child(username).child("Service").child(String.valueOf(System.currentTimeMillis())).child("Location").setValue(locationstr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("Latitude").setValue(latitudeStr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("Longitude").setValue(longitudeStr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("PaymentMode").setValue(modestr);
                                databaseReference.child("Users").child(username).child("Service").child(sysTime).child("SYSTIME").setValue(sysTime);


                                //passing to service table
                                databaseReference.child("Service").child(username).child(sysTime).child("Username").setValue(username);
                                databaseReference.child("Service").child(username).child(sysTime).child("CarBrand").setValue(brandStr);
                                databaseReference.child("Service").child(username).child(sysTime).child("CarModel").setValue(modelstr);
                                databaseReference.child("Service").child(username).child(sysTime).child("ServiceType").setValue(typestr);
                                databaseReference.child("Service").child(username).child(sysTime).child("Date").setValue(datestr);
                                databaseReference.child("Service").child(username).child(sysTime).child("ServiceTime").setValue(timestr);
//                                databaseReference.child("Service").child(username).child(String.valueOf(System.currentTimeMillis())).child("Location").setValue(longitudeStr);
                                databaseReference.child("Service").child(username).child(sysTime).child("Latitude").setValue(latitudeStr);
                                databaseReference.child("Service").child(username).child(sysTime).child("Longitude").setValue(longitudeStr);
                                databaseReference.child("Service").child(username).child(sysTime).child("PaymentMode").setValue(modestr);
                                databaseReference.child("Service").child(username).child(sysTime).child("SYSTIME").setValue(sysTime);
                                Toast.makeText(getApplicationContext(), "Service Sussecfully Booked", Toast.LENGTH_SHORT).show();
                           // }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(user_Book_Service.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });


        /* enable location and permissions */
//        enableLocationSettings();
//        resolutionForResult = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK) {
//                getLocation();
//            } else {
//                /* permissions not Granted */
//                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
//            }
//        });
//
   }

    private void setCarTypeAdapter() {
        ArrayList<String> carbodyType=new ArrayList<>();
        databaseReference.child("CAR_BODYTYPE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carbodyType.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    carbodyType.add(dataSnapshot.getKey());
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(user_Book_Service.this, R.layout.simple_spinner_item, carbodyType);
                carmodel.setAdapter(brandAdapter);
                carmodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        /*String[] modelss=models[position];
                        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelss);
                        carmodel.setAdapter(modelAdapter);*/
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
        ArrayList<String> carBrand=new ArrayList<>();
        databaseReference.child("CAR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carBrand.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    carBrand.add(dataSnapshot.getKey());
                }
                ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(user_Book_Service.this, R.layout.simple_spinner_item, carBrand);
                carbrand.setAdapter(brandAdapter);
                carbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        /*String[] modelss=models[position];
                        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelss);
                        carmodel.setAdapter(modelAdapter);*/
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

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10*1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(user_Book_Service.this).checkLocationSettings(builder.build()).addOnSuccessListener(user_Book_Service.this, (LocationSettingsResponse response) -> {
            getLocation();
        }).addOnFailureListener(user_Book_Service.this, ex -> {
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
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        //Toast.makeText(getApplicationContext(),brand[position],Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void location(String latitude,String longitude) {

        latitudeStr=latitude;
        longitudeStr=longitude;
        Log.e("", "latitude: "+latitude+"  Longitude:"+longitude );
        getAddress( Double.parseDouble(latitude) ,Double.parseDouble(longitude) );
    }

    ///to get place details from lat n log
    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(user_Book_Service.this, Locale.getDefault());
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

            edittextlocation.setText(add);


            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}