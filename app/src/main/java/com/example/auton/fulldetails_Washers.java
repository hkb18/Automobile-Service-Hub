package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsWashersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fulldetails_Washers extends AppCompatActivity {
    private ActivityFulldetailsWashersBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,colorStr,dimensionStr,hoselengthStr,imageStr,manufacturerStr,maxpressureStr,modelStr,poweroutputStr,priceStr,weightStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFulldetailsWashersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Washers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.washersModel.setText(modelStr);

                    colorStr=snapshot.child(key).child("Color").getValue(String.class);
                    binding.washersColor.setText(colorStr);

                    dimensionStr=snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.washersDimensions.setText(dimensionStr);

                    hoselengthStr=snapshot.child(key).child("HoseLength").getValue(String.class);
                    binding.washersHoseLength.setText(hoselengthStr);

                    maxpressureStr=snapshot.child(key).child("MaxPressure").getValue(String.class);
                    binding.washersMaximumPressure.setText(maxpressureStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.washersImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.washersManufacturer.setText(manufacturerStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.washersPrice.setText(priceStr);

                    poweroutputStr=snapshot.child(key).child("PowerOutput").getValue(String.class);
                    binding.washersPowerOutput.setText(poweroutputStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.washersWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnWashersBuyNow.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnWashersCart.setOnClickListener(view -> {
            cart_ModelClass modelClass=new cart_ModelClass();
            String key=databaseReference.push().getKey();
            modelClass.setModel(modelStr);
            modelClass.setImage(imageStr);
            modelClass.setMaufacturer(manufacturerStr);
            modelClass.setQuantity("1");
            modelClass.setUsername(s1);
            modelClass.setKey(key);
            modelClass.setPrice(priceStr);
            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Washers").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("Quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_Washers.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Washers").child(modelStr).child("Quantity").setValue(qty.toString());
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