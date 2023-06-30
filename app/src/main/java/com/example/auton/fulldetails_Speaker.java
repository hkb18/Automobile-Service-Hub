package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsBasstubesBinding;
import com.example.auton.databinding.ActivityFulldetailsSpeakerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_Speaker extends AppCompatActivity {
    private ActivityFulldetailsSpeakerBinding binding;
    //    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,modelStr,diameterStr,poweroutputStr,frequencyStr,imageStr,manufacturerStr,sensitivityStr,colorStr,priceStr,speakertypeStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsSpeakerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.speakerModel.setText(modelStr);

                    speakertypeStr=snapshot.child(key).child("SpeakerType").getValue(String.class);
                    binding.speakerType.setText(speakertypeStr);

                    poweroutputStr=snapshot.child(key).child("PowerOutput").getValue(String.class);
                    binding.speakerPowerOutput.setText(poweroutputStr);

                    diameterStr=snapshot.child(key).child("Diameter").getValue(String.class);
                    binding.speakerDiameter.setText(diameterStr);

                    frequencyStr=snapshot.child(key).child("Frequency").getValue(String.class);
                    binding.speakerFrequency.setText(frequencyStr);

                    sensitivityStr=snapshot.child(key).child("Sensitivity").getValue(String.class);
                    binding.speakerSensitivity.setText(sensitivityStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.speakerImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.speakerManufacturer.setText(manufacturerStr);

                    colorStr=snapshot.child(key).child("Color").getValue(String.class);
                    binding.speakerColor.setText(colorStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.speakerPrice.setText(priceStr);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnSpeakerBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnAddtocart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String key=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(manufacturerStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(key);
            modelClass.setPrice(priceStr);

            databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("Quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    //  qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_Speaker.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                        // databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Quantity").setValue(qty.toString());
                        Intent i=new Intent(getApplicationContext(),user_HomePage.class);
                        i.putExtra("Username", s1);
                        i.putExtra("iscart", "1");
                        startActivity(i);
                        finishAffinity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });
    }
}