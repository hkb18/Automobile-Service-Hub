package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsHornsBinding;
import com.example.auton.databinding.ActivityFulldetailsProtectivesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fulldetails_Horns extends AppCompatActivity {
    private ActivityFulldetailsHornsBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,imageStr,dimensionStr,materialtypeStr,colorStr,weightStr,manufacturerStr,modelStr,voltageStr,noiselevelStr,fittypeStr,featureStr,priceStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsHornsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("model").getValue(String.class);
                    binding.hornsModel.setText(modelStr);

                    colorStr=snapshot.child(key).child("color").getValue(String.class);
                    binding.hornsColor.setText(colorStr);

                    dimensionStr=snapshot.child(key).child("dimension").getValue(String.class);
                    binding.hornsDimensions.setText(dimensionStr);

                    imageStr=snapshot.child(key).child("image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.hornsImg);

                    manufacturerStr=snapshot.child(key).child("manufacturer").getValue(String.class);
                    binding.hornsManufacturer.setText(manufacturerStr);

                    priceStr=snapshot.child(key).child("price").getValue(String.class);
                    binding.hornsPrice.setText(priceStr);

                    weightStr=snapshot.child(key).child("weight").getValue(String.class);
                    binding.hornsWeight.setText(weightStr);

                    materialtypeStr=snapshot.child(key).child("materialType").getValue(String.class);
                    binding.hornsMaterialType.setText(materialtypeStr);

                    featureStr=snapshot.child(key).child("feature").getValue(String.class);
                    binding.hornsFeature.setText(featureStr);

                    fittypeStr=snapshot.child(key).child("fitType").getValue(String.class);
                    binding.hornsFitType.setText(fittypeStr);

                    voltageStr=snapshot.child(key).child("voltage").getValue(String.class);
                    binding.hornsVoltage.setText(voltageStr);

                    noiselevelStr=snapshot.child(key).child("noiseLevel").getValue(String.class);
                    binding.hornsNoiseLevel.setText(noiselevelStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnHornsBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnHornsCart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String keyz=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(manufacturerStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(keyz);
            modelClass.setPrice(priceStr);
            modelClass.setProductKey(key);

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
                        } else  {
                            modelClass.setQuantity("1");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    // qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_Horns.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                        // databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("BackCushions").child(modelStr).child("Quantity").setValue(qty.toString());
                        Intent i=new Intent(getApplicationContext(),user_HomePage.class);
                        i.putExtra("Username", s1);
                        i.putExtra("iscart", "1");
                        startActivity(i);
                        finishAffinity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(fulldetails_Horns.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}