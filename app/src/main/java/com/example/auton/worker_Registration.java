package com.example.auton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class worker_Registration extends BASEACTIVITY implements mapinterface {
    static mapinterface mapinterface;
    String workshopnameStr, emailidStr, contactnoStr, usernameStr, passwordStr, confirmpasswordStr;
    TextInputEditText textInputEditText1, textInputEditText2, textInputEditText3, textInputEditText4, textInputEditText5, textInputEditText6;
    Button signUp, signIn, location;
    TextView tvlocation;
    DatabaseReference databaseReference;
    private String latitudeStr, longitudeStr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_registration);

        mapinterface = this;
        signUp = findViewById(R.id.button_WorkshopSignUp);
        signIn = findViewById(R.id.button_WorkshopLogin);
        location = findViewById(R.id.LOCATION);
        textInputEditText1 = findViewById(R.id.companyName);
        tvlocation = findViewById(R.id.tvLocation);
        textInputEditText2 = findViewById(R.id.emailId);
        textInputEditText3 = findViewById(R.id.contactNumber);
        textInputEditText4 = findViewById(R.id.username);
        textInputEditText5 = findViewById(R.id.password);
        textInputEditText6 = findViewById(R.id.confirmPassword);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        location.setOnClickListener(v -> {
            if (longitudeBest != 0.0 || latitudeBest != 0.0) {
                Intent i = new Intent(getApplicationContext(), MapsActivity2.class);
                i.putExtra("longitude", String.valueOf(longitudeBest));
                i.putExtra("latitude", String.valueOf(latitudeBest));
                i.putExtra("activity", "worker");
                startActivity(i);

            } else {
                Toast.makeText(worker_Registration.this, "not able to get permission", Toast.LENGTH_SHORT).show();
            }
        });
        signUp.setOnClickListener(v -> {
            workshopnameStr = textInputEditText1.getText().toString();
            emailidStr = textInputEditText2.getText().toString();
            contactnoStr = textInputEditText3.getText().toString();
            usernameStr = textInputEditText4.getText().toString();
            passwordStr = textInputEditText5.getText().toString();
            confirmpasswordStr = textInputEditText6.getText().toString();

            if (TextUtils.isEmpty(workshopnameStr) || tvlocation.getText().toString().isEmpty() || emailidStr.isEmpty() || contactnoStr.isEmpty() || usernameStr.isEmpty() || passwordStr.isEmpty() || confirmpasswordStr.isEmpty()) {
                Toast.makeText(worker_Registration.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else if (passwordStr.equals(confirmpasswordStr)) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailidStr).matches()) {

                    if (contactnoStr.length() < 10) {
                        Toast.makeText(worker_Registration.this, "Enter a valid Mobile number", Toast.LENGTH_SHORT).show();
                    } else {
                        if (passwordStr.length() < 8 || confirmpasswordStr.length() < 8) {
                            Toast.makeText(worker_Registration.this, "Minimum length of password is 8", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.hasChild(usernameStr)) {
                                        Toast.makeText(worker_Registration.this, "Already existing User", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        databaseReference.child("Workshop_Profile").child(usernameStr).child("Username").setValue(usernameStr);
                                        databaseReference.child("Workshop_Profile").child(usernameStr).child("Name").setValue(workshopnameStr);
                                        databaseReference.child("Workshop_Profile").child(usernameStr).child("ContactNo").setValue(contactnoStr);
                                        databaseReference.child("Workshop_Profile").child(usernameStr).child("Email_Id").setValue(emailidStr);
                                        databaseReference.child("Workshop_Profile").child(usernameStr).child("Password").setValue(passwordStr);
                                        databaseReference.child("Workshop_Profile").child(usernameStr).child("latitude").setValue(latitudeStr);
                                        databaseReference.child("Workshop_Profile").child(usernameStr).child("longitude").setValue(longitudeStr);
                                        Toast.makeText(worker_Registration.this, "Workshop Successfully Registered", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(worker_Registration.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                } else {
                    Toast.makeText(this, "Enter a valid Email Id", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(worker_Registration.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void location(String latitude, String longitude) {

        latitudeStr = latitude;
        longitudeStr = longitude;
        Log.e("", "latitude: " + latitude + "  Longitude:" + longitude);
        getAddress(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(worker_Registration.this, Locale.getDefault());
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

            tvlocation.setText(add);


            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}