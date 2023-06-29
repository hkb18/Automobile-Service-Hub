package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsAirFreshnerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_AirFreshner extends AppCompatActivity {
    private ActivityFulldetailsAirFreshnerBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,modelStr,dimensionStr,imageStr,manufacturerStr,priceStr,weightStr,colorStr,itemsformStr,durationStr,fragrenceStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsAirFreshnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("AirFreshner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.airfreshnerModel.setText(modelStr);

                    colorStr=snapshot.child(key).child("Color").getValue(String.class);
                    binding.airfreshnerColor.setText(colorStr);

                    dimensionStr=snapshot.child(key).child("Dimenension").getValue(String.class);
                    binding.airfreshnerDimensions.setText(dimensionStr);

                    itemsformStr=snapshot.child(key).child("ItemForm").getValue(String.class);
                    binding.airfreshnerItemForm.setText(itemsformStr);

                    durationStr=snapshot.child(key).child("Duration").getValue(String.class);
                    binding.airfreshnerDuration.setText(durationStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.airfreshnerImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.airfreshnerManufacturer.setText(manufacturerStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.airfreshnerPrice.setText(priceStr);

                    fragrenceStr=snapshot.child(key).child("Fragrence").getValue(String.class);
                    binding.airfreshnerFragrence.setText(fragrenceStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.airfreshnerWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnAirfreshnerBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnAirfreshnerCart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String key=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(manufacturerStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(key);
            modelClass.setPrice(priceStr);
            databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("AirFreshner").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("Quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_AirFreshner.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("AirFreshner").child(modelStr).child("Quantity").setValue(qty.toString());
                        Intent i=new Intent(getApplicationContext(),user_HomePage.class);
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