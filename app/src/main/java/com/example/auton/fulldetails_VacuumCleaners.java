package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsSpeakerBinding;
import com.example.auton.databinding.ActivityFulldetailsVacuumCleanersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_VacuumCleaners extends AppCompatActivity {
    private ActivityFulldetailsVacuumCleanersBinding binding;
    //    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    String key,modelStr,imageStr,manufacturerStr,colorStr,priceStr,operatingvoltageStr,dimensionStr,weightStr,itemincludedStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsVacuumCleanersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("VacuumCleaners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.vacuumcleanerModel.setText(modelStr);

                    operatingvoltageStr=snapshot.child(key).child("OperatingVoltage").getValue(String.class);
                    binding.vacuumcleanerOperatingVoltage.setText(operatingvoltageStr);

                    dimensionStr=snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.vacuumcleanerDimensions.setText(dimensionStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.vacuumcleanerWeight.setText(weightStr);

                    itemincludedStr=snapshot.child(key).child("ItemsIncluded").getValue(String.class);
                    binding.vacuumcleanerItemsIncluded.setText(itemincludedStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.vacuumcleanerImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.vacuumcleanerManufacturer.setText(manufacturerStr);

                    colorStr=snapshot.child(key).child("Color").getValue(String.class);
                    binding.vacuumcleanerColor.setText(colorStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.vacuumcleanerPrice.setText(priceStr);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}