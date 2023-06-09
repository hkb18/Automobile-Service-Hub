package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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
    String s1, key, dimensionStr, imageStr, priceStr, weightStr, boxincludeStr, brandStr, categoryStr, itemformStr, volumeStr, root;
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
        //root=databaseReference.getRoot().toString();

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Cleansers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)) {
                    boxincludeStr = snapshot.child(key).child("BoxIncludes").getValue(String.class);
                    binding.cleanserBoxIncludes.setText(boxincludeStr);

                    brandStr = snapshot.child(key).child("Brand").getValue(String.class);
                    binding.cleanserBrand.setText(brandStr);

                    categoryStr = snapshot.child(key).child("Category").getValue(String.class);
                    binding.cleanserCategory.setText(categoryStr);

                    dimensionStr = snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.cleanserDimensions.setText(dimensionStr);

                    itemformStr = snapshot.child(key).child("ItemForm").getValue(String.class);
                    binding.cleanserItemForm.setText(itemformStr);

                    imageStr = snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.cleanserImg);

                    volumeStr = snapshot.child(key).child("Volume").getValue(String.class);
                    binding.cleanserVolume.setText(volumeStr);

                    priceStr = snapshot.child(key).child("Price").getValue(String.class);
                    binding.cleanserPrice.setText(priceStr);

                    weightStr = snapshot.child(key).child("Weight").getValue(String.class);
                    binding.cleanserWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.btnCleanserBuyNow.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), RazorPay.class);
            i.putExtra("price", priceStr);
            i.putExtra("key", key); //suppose to be model
            startActivity(i);
        });


        binding.btnCleanserCart.setOnClickListener(view -> {
            cart_ModelClass modelClass = new cart_ModelClass();
            String keyz = databaseReference.push().getKey();
            modelClass.setModel(categoryStr);
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

            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Cleansers").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr = snapshot.child("Quantity").getValue().toString();
                    Integer qty = Integer.parseInt(qtyStr);
                    //  qty--;
                    modelClass.setTotalQty(qtyStr);
                    if (qty <= 0) {
                        Toast.makeText(fulldetails_cleansers.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    } else {
                     /*   databaseReference.child("CART").child(s1).child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String qtyStr=snapshot.child("quantity").getValue(String.class);
                                Integer qty=Integer.parseInt(qtyStr);
                                cart_ModelClass cart= snapshot.getValue(cart_ModelClass.class);
                                cart.setQuantity(qty.toString());
                                databaseReference.child("CART").child(s1).child(keyz).setValue(cart);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });*/


                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);

                    /*    databaseReference.child("CART").child(s1).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String qtyStr = snapshot.child("quantity").getValue(String.class);
                                Integer qty = Integer.parseInt(qtyStr);
                                qty++;
                                cart_ModelClass cart = snapshot.getValue(cart_ModelClass.class);
                                cart.setQuantity(qty.toString());
                                databaseReference.child("CART").child(s1).child(key).setValue(cart);
//                                ArrayList<cart_ModelClass> list=new ArrayList<>();
//                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                                    list.add(dataSnapshot.getValue(cart_ModelClass.class));
//                                }
//                                for (cart_ModelClass x: list){
//                                    if (x.getProductKey().equals(key)){
//                                        Integer tempQty=Integer.parseInt(x.getQuantity());
//                                        tempQty++;
//                                        modelClass.setQuantity(tempQty.toString());
//                                        snapshot.child("sd").
//                                    }
//                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });*/
                        //    databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Cleansers").child(key).child("Quantity").setValue(qty.toString());
                        Intent i = new Intent(getApplicationContext(), user_HomePage.class);
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