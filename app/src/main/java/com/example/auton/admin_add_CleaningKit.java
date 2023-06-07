package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_add_CleaningKit extends AppCompatActivity {
    Button add;
    TextInputEditText textInputEditTextWeight,textInputEditTextDimension,textInputEditTextBrand,textInputEditTextVolume,textInputEditTextBoxIncludes,textInputEditTextItemForm,textInputEditTextPrice,textInputEditTextQuantity;
    String weightStr,dimensionStr,brandStr,volumeStr,boxIncudeStr,itemFormStr,priceStr,quantityStr;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_cleaning_kit);

        add=findViewById(R.id.btn_add_CleaningKit);
        textInputEditTextWeight=findViewById(R.id.cleaningkitWeight);
        textInputEditTextDimension=findViewById(R.id.cleaningkitDimensions);
        textInputEditTextBrand=findViewById(R.id.cleaningkitBrand);
        textInputEditTextVolume=findViewById(R.id.cleaningkitVolume);
        textInputEditTextBoxIncludes=findViewById(R.id.cleaningkitBoxIncludes);
        textInputEditTextItemForm=findViewById(R.id.cleaningkitItemForm);
        textInputEditTextPrice=findViewById(R.id.cleaningkitPrice);
        textInputEditTextQuantity=findViewById(R.id.cleaningkitQuantity);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightStr=textInputEditTextWeight.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                brandStr=textInputEditTextBrand.getText().toString();
                volumeStr=textInputEditTextVolume.getText().toString();
                boxIncudeStr=textInputEditTextBoxIncludes.getText().toString();
                itemFormStr=textInputEditTextItemForm.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();
                if(weightStr.isEmpty()|| dimensionStr.isEmpty()||brandStr.isEmpty() ||volumeStr.isEmpty()|| boxIncudeStr.isEmpty() || itemFormStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_CleaningKit.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else{
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("Weight").setValue(weightStr);
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("Dimensions").setValue(dimensionStr);
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("Brand").setValue(brandStr);
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("Volume").setValue(volumeStr);
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("BoxIncluded").setValue(boxIncudeStr);
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("ItemForm").setValue(itemFormStr);
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("Price").setValue(priceStr);
                            databaseReference.child("Accessories").child("CleaningKit").child(brandStr).child("Quantity").setValue(quantityStr);

//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_CleaningKit.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), admin_Add_Accessories.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_CleaningKit.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}