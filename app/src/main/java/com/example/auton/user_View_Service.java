package com.example.auton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_View_Service extends AppCompatActivity {
    TextView textViewCarBrand, textViewCarModel, textViewServiceType, textViewServiceDate, textViewServiceTime, textViewUserLocation, textViewPaymentBy;
    Button Edit;
    String carbrandStr, carmodelStr, servicetypeStr, servicedateStr, servicetimeStr, userlocationStr, paymentbyStr;
    DatabaseReference databaseReference;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_service);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("Username");
        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        String s1 = sh.getString("Username", "");


        textViewCarBrand = findViewById(R.id.tv_CarBrand);
        textViewCarModel = findViewById(R.id.tv_CarModel);
        textViewServiceType = findViewById(R.id.tv_ServiceType);
        textViewServiceDate = findViewById(R.id.tv_ServiceDate);
        textViewServiceTime = findViewById(R.id.tv_ServiceTime);
        textViewUserLocation = findViewById(R.id.tv_UserLocation);
        textViewPaymentBy = findViewById(R.id.tv_Payment);

        databaseReference.child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(username)) {
                    carbrandStr = snapshot.child(username).child("Service").child("CarBrand").getValue(String.class);
                    carmodelStr = snapshot.child(username).child("Service").child("CarModel").getValue(String.class);
                    servicetypeStr = snapshot.child(username).child("Service").child("ServiceType").getValue(String.class);
                    servicedateStr = snapshot.child(username).child("Service").child("Date").getValue(String.class);
                    servicetimeStr = snapshot.child(username).child("Service").child("ServiceTime").getValue(String.class);
                    userlocationStr = snapshot.child(username).child("Service").child("Latitude").getValue(String.class);
                    paymentbyStr = snapshot.child(username).child("Service").child("PaymentMode").getValue(String.class);

                    textViewCarBrand.setText(carbrandStr);
                    textViewCarModel.setText(carmodelStr);
                    textViewServiceType.setText(servicetypeStr);
                    textViewServiceDate.setText(servicedateStr);
                    textViewServiceTime.setText(servicetimeStr);

                    textViewUserLocation.setText(userlocationStr);

                    textViewPaymentBy.setText(paymentbyStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void location(String location) {

    }
}