package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsBasstubesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fulldetails_Basstubes extends AppCompatActivity {

    //    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1, key, modelStr, quantityStr, dimensionStr, poweroutputStr, frequencyStr, imageStr, manufacturerStr, sensitivityStr, colorStr, priceStr, weightStr, designStr, salientfeatureStr;
    private ActivityFulldetailsBasstubesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFulldetailsBasstubesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        s1 = sh.getString("Username", "");

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)) {
                    modelStr = snapshot.child(key).child("model").getValue(String.class);
                    binding.basstubesModel.setText(modelStr);

                    poweroutputStr = snapshot.child(key).child("powerOutput").getValue(String.class);
                    binding.basstubesPowerOutput.setText(poweroutputStr);

                    salientfeatureStr = snapshot.child(key).child("salientFeature").getValue(String.class);
                    binding.basstubesSalientFeature.setText(salientfeatureStr);

                    dimensionStr = snapshot.child(key).child("dimension").getValue(String.class);
                    binding.basstubesDimension.setText(dimensionStr);

                    frequencyStr = snapshot.child(key).child("frequency").getValue(String.class);
                    binding.basstubesFrequency.setText(frequencyStr);

                    sensitivityStr = snapshot.child(key).child("sensitivity").getValue(String.class);
                    binding.basstubesSensitivity.setText(sensitivityStr);

                    imageStr = snapshot.child(key).child("image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.basstubesImg);

                    manufacturerStr = snapshot.child(key).child("manufacturer").getValue(String.class);
                    binding.basstubesManufacturer.setText(manufacturerStr);

                    colorStr = snapshot.child(key).child("color").getValue(String.class);
                    binding.basstubesColor.setText(colorStr);

                    designStr = snapshot.child(key).child("design").getValue(String.class);
                    binding.basstubesDesign.setText(designStr);

                    priceStr = snapshot.child(key).child("price").getValue(String.class);
                    binding.basstubesPrice.setText(priceStr);

                    weightStr = snapshot.child(key).child("weight").getValue(String.class);
                    binding.basstubesWeight.setText(weightStr);
                    quantityStr = snapshot.child(key).child("quantity").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(fulldetails_Basstubes.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        binding.btnBasstubesBuyNow.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), RazorPay.class);
            i.putExtra("totalPrice", priceStr);
            i.putExtra("key", key);
            i.putExtra("activity", "buynow");
            i.putExtra("mainName", "SCREENS_SPEAKERS");
            i.putExtra("subName", "BassTubes");
            i.putExtra("image", imageStr);
            i.putExtra("manufacturer", manufacturerStr);
            i.putExtra("model", modelStr);
            i.putExtra("quantity", quantityStr);
            startActivity(i);
        });

        binding.btnAddtocart.setOnClickListener(view -> {
            cart_ModelClass modelClass = new cart_ModelClass();
            String keyz = databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setManufacturer(manufacturerStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(keyz);
            modelClass.setPrice(priceStr);
            modelClass.setProductKey(key);
            modelClass.setMainName("SCREENS_SPEAKERS");
            modelClass.setSubName("BassTubes");

            databaseReference.child("CART").child(s1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    ArrayList<cart_ModelClass> list = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getValue(cart_ModelClass.class));
                    }
                    for (cart_ModelClass x : list) {
                        if (x.getProductKey().equals(key)) {
                            Integer tempQty = Integer.parseInt(x.getQuantity());
                            tempQty++;
                            modelClass.setQuantity(tempQty.toString());
                        } else {
                            modelClass.setQuantity("1");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(fulldetails_Basstubes.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr = snapshot.child("quantity").getValue().toString();
                    Integer qty = Integer.parseInt(qtyStr);
                    modelClass.setTotalQty(qtyStr);
                    if (qty <= 0) {
                        Toast.makeText(fulldetails_Basstubes.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                        Intent i = new Intent(getApplicationContext(), user_HomePage.class);
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