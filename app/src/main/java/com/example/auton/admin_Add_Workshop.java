package com.example.auton;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.auton.databinding.ActivityAdminAddWorkshopBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class admin_Add_Workshop extends BASEACTIVITY implements mapinterface {
    static mapinterface mapinterface;
    String workshopnameStr, emailidStr, contactnoStr, usernameStr, passwordStr, confirmpasswordStr;
    DatabaseReference databaseReference;
    private ActivityAdminAddWorkshopBinding binding;
    private String latitudeStr, longitudeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddWorkshopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapinterface = this;
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.LOCATION.setOnClickListener(v -> {
            if (longitudeBest != 0.0 || latitudeBest != 0.0) {
                Intent i = new Intent(getApplicationContext(), MapsActivity2.class);
                i.putExtra("longitude", String.valueOf(longitudeBest));
                i.putExtra("latitude", String.valueOf(latitudeBest));
                i.putExtra("activity", "admin");
                startActivity(i);

            } else {
                Toast.makeText(admin_Add_Workshop.this, "not able to get permission", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnAddWorkshop.setOnClickListener(view -> {
            workshopnameStr = binding.companyName.getText().toString();
            emailidStr = binding.emailId.getText().toString();
            contactnoStr = binding.contactNumber.getText().toString();
            usernameStr = binding.username.getText().toString();
            passwordStr = binding.password.getText().toString();
            confirmpasswordStr = binding.confirmPassword.getText().toString();

            if (TextUtils.isEmpty(workshopnameStr) || binding.tvLocation.getText().toString().isEmpty() || emailidStr.isEmpty() || contactnoStr.isEmpty() || usernameStr.isEmpty() || passwordStr.isEmpty() || confirmpasswordStr.isEmpty()) {
                Toast.makeText(admin_Add_Workshop.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else if (passwordStr.equals(confirmpasswordStr)) {
                databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(usernameStr)) {
                            Toast.makeText(admin_Add_Workshop.this, "Already existing User", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(admin_Add_Workshop.this, "Workshop Successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(admin_Add_Workshop.this, admin_HomePage.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_Add_Workshop.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(admin_Add_Workshop.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void location(String latitude, String longitude) {
        latitudeStr = latitude;
        longitudeStr = longitude;

        getAddress(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(admin_Add_Workshop.this, Locale.getDefault());
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

            binding.tvLocation.setText(add);


            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}