package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsAndroidScreenBinding;
import com.example.auton.databinding.ActivityUserViewScreensSpeakersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_AndroidScreen extends AppCompatActivity implements AndroidScreen_Interface{
    String androidscreenModelStr;
    private ActivityFulldetailsAndroidScreenBinding binding;
    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    String key,dimensionStr,displaytypeStr,imageStr,manufacturerStr,modelStr,ostypeStr,priceStr,ramStr,romStr,screensizeStr,weightStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsAndroidScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        androidScreen_interface=this;//interface

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("AndroidScreens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.androidscreenModel.setText(modelStr);

                    dimensionStr=snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.androidscreenDimension.setText(dimensionStr);

                    displaytypeStr=snapshot.child(key).child("DisplayType").getValue(String.class);
                    binding.androidscreenDisplayType.setText(displaytypeStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.androidscreenImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.androidscreenManufacturer.setText(manufacturerStr);

                    ostypeStr=snapshot.child(key).child("OSType").getValue(String.class);
                    binding.androidscreenOSType.setText(ostypeStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.androidscreenPrice.setText(priceStr);

                    /*quantityStr=snapshot.child(key).child("Quantity").getValue(String.class);
                    binding.androidscreen.setText(modelStr);*/
                    ramStr=snapshot.child(key).child("RAM").getValue(String.class);
                    binding.androidscreenRAM.setText(ramStr);

                    romStr=snapshot.child(key).child("ROM").getValue(String.class);
                    binding.androidscreenROM.setText(romStr);

                    screensizeStr=snapshot.child(key).child("ScreenSize").getValue(String.class);
                    binding.androidscreenScreenSize.setText(screensizeStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.androidscreenWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void details(String Model) {
        androidscreenModelStr=Model;
    }

    @Override
    public void onClickItem(String Model) {

    }
}