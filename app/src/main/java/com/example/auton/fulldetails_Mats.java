package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsMatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fulldetails_Mats extends AppCompatActivity {
    private ActivityFulldetailsMatsBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,modelStr,imageStr,manufacturerStr,priceStr,colorStr,materialStr,patternStr,featureStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsMatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("Mats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.matsModel.setText(modelStr);

                    colorStr=snapshot.child(key).child("Color").getValue(String.class);
                    binding.matsColor.setText(colorStr);

                    featureStr=snapshot.child(key).child("Feature").getValue(String.class);
                    binding.matsFeature.setText(featureStr);

                    materialStr=snapshot.child(key).child("Material").getValue(String.class);
                    binding.matsMaterial.setText(materialStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.matsImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.matsManufacturer.setText(manufacturerStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.matsPrice.setText(priceStr);

                    patternStr=snapshot.child(key).child("Pattern").getValue(String.class);
                    binding.matsPattern.setText(patternStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnMatsBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnMatsCart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String keyz=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(manufacturerStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(keyz);
            modelClass.setProductKey(key);
            modelClass.setPrice(priceStr);
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


            databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("Mats").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("Quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                   // qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_Mats.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                    //    databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("Mats").child(modelStr).child("Quantity").setValue(qty.toString());
                        Intent i=new Intent(getApplicationContext(),user_HomePage.class);
                        i.putExtra("Username", s1);
                        i.putExtra("iscart", "1");
                        startActivity(i);
                        finishAffinity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(fulldetails_Mats.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}