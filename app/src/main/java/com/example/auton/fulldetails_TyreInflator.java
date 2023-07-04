package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsTyreInflatorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fulldetails_TyreInflator extends AppCompatActivity {
    private ActivityFulldetailsTyreInflatorBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,imageStr,modelStr,maxcapacityStr,colorStr,dimensionStr,weightStr,voltageStr,itemincludedStr,brandStr,priceStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsTyreInflatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("TyreInflator").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("model").getValue(String.class);
                    binding.tyreinflatorModel.setText(modelStr);

                    colorStr=snapshot.child(key).child("color").getValue(String.class);
                    binding.tyreinflatorColor.setText(colorStr);

                    dimensionStr=snapshot.child(key).child("dimension").getValue(String.class);
                    binding.tyreinflatorDimensions.setText(dimensionStr);

                    imageStr=snapshot.child(key).child("image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.tyreinflatorImg);

                    brandStr=snapshot.child(key).child("brand").getValue(String.class);
                    binding.tyreinflatorBrand.setText(brandStr);

                    priceStr=snapshot.child(key).child("price").getValue(String.class);
                    binding.tyreinflatorPrice.setText(priceStr);

                    weightStr=snapshot.child(key).child("weight").getValue(String.class);
                    binding.tyreinflatorWeight.setText(weightStr);

                    maxcapacityStr=snapshot.child(key).child("maxVoltage").getValue(String.class);
                    binding.tyreinflatorMaxVoltage.setText(maxcapacityStr);

                    voltageStr=snapshot.child(key).child("voltage").getValue(String.class);
                    binding.tyreinflatorVoltage.setText(voltageStr);

                    itemincludedStr=snapshot.child(key).child("itemsIncluded").getValue(String.class);
                    binding.tyreinflatorItemsIncluded.setText(itemincludedStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnTyreinflatorBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnTyreinflatorCart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String keyz=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(brandStr);
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


            databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("TyreInflator").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    // qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_TyreInflator.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(fulldetails_TyreInflator.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}