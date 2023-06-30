package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsMicrofiberBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_Microfiber extends AppCompatActivity {
    private ActivityFulldetailsMicrofiberBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,brandStr,colorStr,dimensionStr,fabrictypeStr,materialtypeStr,imageStr,modelStr,priceStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFulldetailsMicrofiberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.microfiberModel.setText(modelStr);

                    colorStr=snapshot.child(key).child("Color").getValue(String.class);
                    binding.microfiberColor.setText(colorStr);

                    dimensionStr=snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.microfiberDimensions.setText(dimensionStr);

                    brandStr=snapshot.child(key).child("Brand").getValue(String.class);
                    binding.microfiberBrand.setText(brandStr);

                    fabrictypeStr=snapshot.child(key).child("FabricType").getValue(String.class);
                    binding.microfiberFabricType.setText(fabrictypeStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.microfiberImg);

                    materialtypeStr=snapshot.child(key).child("MaterialType").getValue(String.class);
                    binding.microfiberMaterialType.setText(materialtypeStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.microfiberPrice.setText(priceStr);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnMicrofiberBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnMicrofiberCart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String key=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(brandStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(key);
            modelClass.setPrice(priceStr);
            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("Quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    // qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_Microfiber.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                        //   databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Quantity").setValue(qty.toString());
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