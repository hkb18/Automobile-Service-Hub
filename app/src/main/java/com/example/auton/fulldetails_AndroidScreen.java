package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.auton.databinding.ActivityFulldetailsAndroidScreenBinding;
import com.example.auton.databinding.ActivityUserViewScreensSpeakersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fulldetails_AndroidScreen extends AppCompatActivity implements AndroidScreen_Interface{
    String androidscreenModelStr;
    private ActivityFulldetailsAndroidScreenBinding binding;
    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1,key,dimensionStr,displaytypeStr,imageStr,manufacturerStr,modelStr,ostypeStr,priceStr,ramStr,romStr,screensizeStr,weightStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFulldetailsAndroidScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        androidScreen_interface=this;//interface

        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        Bundle extras=getIntent().getExtras();
        key= extras.getString("key");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("AndroidScreens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(key)){
                    modelStr=snapshot.child(key).child("Model").getValue(String.class);
                    binding.androidscreenModel.setText(modelStr);

                    dimensionStr=snapshot.child(key).child("Dimension").getValue(String.class);
                    binding.androidscreenDimension.setText(dimensionStr);

                    displaytypeStr=snapshot.child(key).child("DisplayType").getValue(String.class);
                    binding.androidscreenDisplayType.setText(displaytypeStr);

                    imageStr=snapshot.child(key).child("Image").getValue(String.class);
                    Glide.with(getApplicationContext()).load(imageStr).into(binding.androidscreenImg);

                    manufacturerStr=snapshot.child(key).child("Manufacturer").getValue(String.class);
                    binding.androidscreenManufacturer.setText(manufacturerStr);

                    ostypeStr=snapshot.child(key).child("OSType").getValue(String.class);
                    binding.androidscreenOSType.setText(ostypeStr);

                    priceStr=snapshot.child(key).child("Price").getValue(String.class);
                    binding.androidscreenPrice.setText(priceStr);

                    /*quantityStr=snapshot.child(key).child("Quantity").getValue(String.class);
                    binding.androidscreen.setText(modelStr);*/
                    ramStr=snapshot.child(key).child("RAM").getValue(String.class);
                    binding.androidscreenRAM.setText(ramStr);

                    romStr=snapshot.child(key).child("ROM").getValue(String.class);
                    binding.androidscreenROM.setText(romStr);

                    screensizeStr=snapshot.child(key).child("ScreenSize").getValue(String.class);
                    binding.androidscreenScreenSize.setText(screensizeStr);

                    weightStr=snapshot.child(key).child("Weight").getValue(String.class);
                    binding.androidscreenWeight.setText(weightStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.btnBuy.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),RazorPay.class);
            i.putExtra("price",priceStr);
            i.putExtra("key",modelStr);
            startActivity(i);
        });

        binding.btnAddtocart.setOnClickListener(view -> {
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


            databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("AndroidScreens").child(modelStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String qtyStr=snapshot.child("Quantity").getValue().toString();
                    Integer qty=Integer.parseInt(qtyStr);
                    //qty--;
                    if (qty<=0){
                        Toast.makeText(fulldetails_AndroidScreen.this, "OUT OF STOCK!!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.child("CART").child(s1).child(key).setValue(modelClass);
                       // databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("AndroidScreens").child(modelStr).child("Quantity").setValue(qty.toString());
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


    @Override
    public void details(String Model) {
        androidscreenModelStr=Model;
    }

    @Override
    public void onClickItem(String Model) {

    }
}