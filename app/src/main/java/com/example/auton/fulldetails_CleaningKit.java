package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsCleaningKitBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fulldetails_CleaningKit extends AppCompatActivity {
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1, key, modelStr, quantityStr, boxincludedStr, brandStr, dimensionStr, imageStr, itemformStr, priceStr, volumeStr, weightStr;
    private ActivityFulldetailsCleaningKitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFulldetailsCleaningKitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        s1 = sh.getString("Username", "");

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)) {
                    modelStr = snapshot.child(key).child("model").getValue(String.class);
                    binding.cleaningkitModel.setText(modelStr);

                    boxincludedStr = snapshot.child(key).child("boxIncluded").getValue(String.class);
                    binding.cleaningkitBoxIncluded.setText(boxincludedStr);

                    dimensionStr = snapshot.child(key).child("dimension").getValue(String.class);
                    binding.cleaningkitDimensions.setText(dimensionStr);

                    itemformStr = snapshot.child(key).child("itemForm").getValue(String.class);
                    binding.cleaningkitItemForm.setText(itemformStr);

                    volumeStr = snapshot.child(key).child("volume").getValue(String.class);
                    binding.cleaningkitVolume.setText(volumeStr);

                    imageStr = snapshot.child(key).child("image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.cleaningkitImg);

                    brandStr = snapshot.child(key).child("brand").getValue(String.class);
                    binding.cleaningkitBrand.setText(brandStr);

                    priceStr = snapshot.child(key).child("price").getValue(String.class);
                    binding.cleaningkitPrice.setText(priceStr);

                    weightStr = snapshot.child(key).child("weight").getValue(String.class);
                    binding.cleaningkitWeight.setText(weightStr);
                    quantityStr = snapshot.child(key).child("quantity").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(fulldetails_CleaningKit.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        binding.btnCleaningkitBuyNow.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), RazorPay.class);
            i.putExtra("totalPrice", priceStr);
            i.putExtra("key", key);
            i.putExtra("activity", "buynow");
            i.putExtra("mainName", "CARCARE_PURIFIERS");
            i.putExtra("subName", "CleaningKit");
            i.putExtra("image", imageStr);
            i.putExtra("manufacturer", brandStr);
            i.putExtra("model", modelStr);
            i.putExtra("quantity", quantityStr);
            startActivity(i);
        });

        binding.btnCleaningkitCart.setOnClickListener(view -> {
            cart_ModelClass modelClass = new cart_ModelClass();
            String keyz = databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setManufacturer(brandStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(keyz);
            modelClass.setPrice(priceStr);
            modelClass.setProductKey(key);
            modelClass.setMainName("CARCARE_PURIFIERS");
            modelClass.setSubName("CleaningKit");

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
                    Toast.makeText(fulldetails_CleaningKit.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr = snapshot.child("quantity").getValue().toString();
                    Integer qty = Integer.parseInt(qtyStr);
                    modelClass.setTotalQty(qtyStr);
                    if (qty <= 0) {
                        Toast.makeText(fulldetails_CleaningKit.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(fulldetails_CleaningKit.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });
    }
}