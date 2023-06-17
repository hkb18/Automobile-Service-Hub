package com.example.auton;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsAmplifierBinding;
import com.example.auton.databinding.ActivityFulldetailsBasstubesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_Basstubes extends AppCompatActivity {

    private ActivityFulldetailsBasstubesBinding binding;
//    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    String key,modelStr,dimensionStr,poweroutputStr,frequencyStr,imageStr,manufacturerStr,sensitivityStr,colorStr,priceStr,weightStr,designStr,salientfeatureStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsBasstubesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //androidScreen_interface=this;//interface

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.basstubesModel.setText(modelStr);

                    poweroutputStr=snapshot.child(key).child("PowerOutput").getValue(String.class);
                    binding.basstubesPowerOutput.setText(poweroutputStr);

                    salientfeatureStr=snapshot.child(key).child("SalientFeature").getValue(String.class);
                    binding.basstubesSalientFeature.setText(salientfeatureStr);

                    dimensionStr=snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.basstubesDimension.setText(dimensionStr);

                    frequencyStr=snapshot.child(key).child("Frequency").getValue(String.class);
                    binding.basstubesFrequency.setText(frequencyStr);

                    sensitivityStr=snapshot.child(key).child("Sensitivity").getValue(String.class);
                    binding.basstubesSensitivity.setText(sensitivityStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.basstubesImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.basstubesManufacturer.setText(manufacturerStr);

                    colorStr=snapshot.child(key).child("Color").getValue(String.class);
                    binding.basstubesColor.setText(colorStr);

                    designStr=snapshot.child(key).child("Design").getValue(String.class);
                    binding.basstubesDesign.setText(designStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.basstubesPrice.setText(priceStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.basstubesWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}