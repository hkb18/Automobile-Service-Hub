package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsCleansersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fulldetails_cleansers extends AppCompatActivity {
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1, key, quantityStr, dimensionStr, imageStr, priceStr, weightStr, boxincludeStr, brandStr, categoryStr, itemformStr, volumeStr, root;
    String model = "";
    private ActivityFulldetailsCleansersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFulldetailsCleansersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        s1 = sh.getString("Username", "");

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Cleansers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)) {
                    boxincludeStr = snapshot.child(key).child("boxIncluded").getValue(String.class);
                    binding.cleanserBoxIncludes.setText(boxincludeStr);

                    brandStr = snapshot.child(key).child("brand").getValue(String.class);
                    binding.cleanserBrand.setText(brandStr);

                    categoryStr = snapshot.child(key).child("category").getValue(String.class);
                    binding.cleanserCategory.setText(categoryStr);

                    dimensionStr = snapshot.child(key).child("dimension").getValue(String.class);
                    binding.cleanserDimensions.setText(dimensionStr);

                    itemformStr = snapshot.child(key).child("itemForm").getValue(String.class);
                    binding.cleanserItemForm.setText(itemformStr);

                    imageStr = snapshot.child(key).child("image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.cleanserImg);

                    volumeStr = snapshot.child(key).child("volume").getValue(String.class);
                    binding.cleanserVolume.setText(volumeStr);

                    priceStr = snapshot.child(key).child("price").getValue(String.class);
                    binding.cleanserPrice.setText(priceStr);

                    weightStr = snapshot.child(key).child("weight").getValue(String.class);
                    binding.cleanserWeight.setText(weightStr);
                    quantityStr = snapshot.child(key).child("quantity").getValue(String.class);
                    model = snapshot.child(key).child("model").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(fulldetails_cleansers.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        binding.btnCleanserBuyNow.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), RazorPay.class);
            i.putExtra("totalPrice", priceStr);
            i.putExtra("key", key); //suppose to be model
            i.putExtra("activity", "buynow");
            i.putExtra("mainName", "CARCARE_PURIFIERS");
            i.putExtra("subName", "Cleansers");
            i.putExtra("image", imageStr);
            i.putExtra("manufacturer", brandStr);
            i.putExtra("model", "");//no model
            i.putExtra("quantity", quantityStr);
            startActivity(i);
        });


        binding.btnCleanserCart.setOnClickListener(view -> {
            cart_ModelClass modelClass = new cart_ModelClass();
            String keyz = databaseReference.push().getKey();
            modelClass.setModel("");
            modelClass.setImage(imageStr);
            modelClass.setManufacturer(brandStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(keyz);
            modelClass.setPrice(priceStr);
            modelClass.setProductKey(key);
            modelClass.setMainName("CARCARE_PURIFIERS");
            modelClass.setSubName("Cleansers");


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
                    Toast.makeText(fulldetails_cleansers.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Cleansers").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr = snapshot.child("quantity").getValue().toString();
                    Integer qty = Integer.parseInt(qtyStr);
                    modelClass.setTotalQty(qtyStr);
                    if (qty <= 0) {
                        Toast.makeText(fulldetails_cleansers.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(fulldetails_cleansers.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}