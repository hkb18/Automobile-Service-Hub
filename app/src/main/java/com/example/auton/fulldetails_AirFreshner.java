package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsAirFreshnerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class fulldetails_AirFreshner extends AppCompatActivity {
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1, key, modelStr, dimensionStr, imageStr, manufacturerStr, priceStr, weightStr, colorStr, itemsformStr, durationStr, fragrenceStr, quantityStr;
    private ActivityFulldetailsAirFreshnerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFulldetailsAirFreshnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        s1 = sh.getString("Username", "");

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("AirFreshner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)) {
                    modelStr = snapshot.child(key).child("model").getValue(String.class);
                    binding.airfreshnerModel.setText(modelStr);

                    colorStr = snapshot.child(key).child("color").getValue(String.class);
                    binding.airfreshnerColor.setText(colorStr);

                    dimensionStr = snapshot.child(key).child("dimension").getValue(String.class);
                    binding.airfreshnerDimensions.setText(dimensionStr);

                    itemsformStr = snapshot.child(key).child("itemForm").getValue(String.class);
                    binding.airfreshnerItemForm.setText(itemsformStr);

                    durationStr = snapshot.child(key).child("duration").getValue(String.class);
                    binding.airfreshnerDuration.setText(durationStr);

                    imageStr = snapshot.child(key).child("image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.airfreshnerImg);

                    manufacturerStr = snapshot.child(key).child("manufacturer").getValue(String.class);
                    binding.airfreshnerManufacturer.setText(manufacturerStr);

                    priceStr = snapshot.child(key).child("price").getValue(String.class);
                    binding.airfreshnerPrice.setText(priceStr);

                    fragrenceStr = snapshot.child(key).child("fragrence").getValue(String.class);
                    binding.airfreshnerFragrence.setText(fragrenceStr);

                    weightStr = snapshot.child(key).child("weight").getValue(String.class);
                    binding.airfreshnerWeight.setText(weightStr);

                    quantityStr = snapshot.child(key).child("quantity").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(fulldetails_AirFreshner.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        binding.btnAirfreshnerBuyNow.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), RazorPay.class);
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            i.putExtra("totalPrice", priceStr);
            i.putExtra("key", key);
            i.putExtra("activity", "buynow");
            i.putExtra("mainName", "FLOORMATS_CUSHIONS");
            i.putExtra("subName", "AirFreshner");
            i.putExtra("image", imageStr);
            i.putExtra("manufacturer", manufacturerStr);
            i.putExtra("model", modelStr);
            i.putExtra("quantity", quantityStr);
            startActivity(i);
        });

        binding.btnAirfreshnerCart.setOnClickListener(view -> {
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
            modelClass.setMainName("FLOORMATS_CUSHIONS");
            modelClass.setSubName("AirFreshner");

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
                    Toast.makeText(fulldetails_AirFreshner.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


            databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("AirFreshner").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr = snapshot.child("quantity").getValue().toString();
                    Integer qty = Integer.parseInt(qtyStr);
                    modelClass.setTotalQty(qtyStr);
                    if (qty <= 0) {
                        Toast.makeText(fulldetails_AirFreshner.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(fulldetails_AirFreshner.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}