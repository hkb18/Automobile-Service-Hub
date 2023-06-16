package com.example.auton;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsAmplifierBinding;
import com.example.auton.databinding.ActivityFulldetailsAndroidScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_Amplifier extends AppCompatActivity implements AndroidScreen_Interface{
    String amplifierModelStr;
    private ActivityFulldetailsAmplifierBinding binding;
    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    String key,modelStr,dimensionStr,maxvoltageStr,mountinghardwareStr,channelStr,imageStr,manufacturerStr,priceStr,weightStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsAmplifierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //androidScreen_interface=this;//interface

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.amplifierModel.setText(modelStr);

                    maxvoltageStr=snapshot.child(key).child("MaxVoltage").getValue(String.class);
                    binding.amplifierMaxVoltage.setText(maxvoltageStr);

                    dimensionStr=snapshot.child(key).child("Dimenension").getValue(String.class);
                    binding.amplifierDimension.setText(dimensionStr);

                    mountinghardwareStr=snapshot.child(key).child("MountingHardware").getValue(String.class);
                    binding.amplifierMountingHardware.setText(mountinghardwareStr);

                    channelStr=snapshot.child(key).child("Channel").getValue(String.class);
                    binding.amplifierChannels.setText(channelStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.amplifierImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.amplifierManufacturer.setText(manufacturerStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.amplifierPrice.setText(priceStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.amplifierWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void details(String Model) {
        amplifierModelStr=Model;
    }

    @Override
    public void onClickItem(String Model) {

    }
}