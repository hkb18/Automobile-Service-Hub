package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsWiperBladesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_WiperBlades extends AppCompatActivity {
    private ActivityFulldetailsWiperBladesBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,brandStr,dimensionStr,imageStr,materialStr,modelStr,positionStr,priceStr,weightStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFulldetailsWiperBladesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("WiperBlades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.wiperbladesModel.setText(modelStr);

                    dimensionStr=snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.wiperbladesDimensions.setText(dimensionStr);

                    materialStr=snapshot.child(key).child("Material").getValue(String.class);
                    binding.wiperbladesMaterial.setText(materialStr);

                    positionStr=snapshot.child(key).child("Position").getValue(String.class);
                    binding.wiperbladesPosition.setText(positionStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.wiperbladesImg);

                    brandStr=snapshot.child(key).child("Brand").getValue(String.class);
                    binding.wiperbladesBrand.setText(brandStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.wiperbladesPrice.setText(priceStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.wiperbladesWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnWiperbladesBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnWiperbladesCart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String key=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(brandStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(key);
            modelClass.setPrice(priceStr);
            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("WiperBlades").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("Quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    //   qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_WiperBlades.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                        //    databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("WiperBlades").child(modelStr).child("Quantity").setValue(qty.toString());
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