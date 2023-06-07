package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_add_VacuumCleaners extends AppCompatActivity {
    Button add;
    DatabaseReference databaseReference;
    TextInputEditText textInputEditTextModel,textInputEditTextOperatingVoltage,textInputEditTextColor,textInputEditTextDimension,textInputEditTextWeight,textInputEditTextManufacturer,textInputEditTextItemsIncluded,textInputEditTextPrice,textInputEditTextQuantity;
    String modelStr,operatingVoltageStr,colorStr,dimensionStr,weightStr,manufacturerStr,itemsIncludedStr,priceStr,quantityStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_vacuum_cleaners);

        add=findViewById(R.id.btn_add_VacuumCleaners);
        textInputEditTextModel=findViewById(R.id.vacuumModel);
        textInputEditTextOperatingVoltage=findViewById(R.id.vacuumVoltage);
        textInputEditTextColor=findViewById(R.id.vacuumColor);
        textInputEditTextDimension=findViewById(R.id.vacuumDimensions);
        textInputEditTextWeight=findViewById(R.id.vacuumWeight);
        textInputEditTextManufacturer=findViewById(R.id.vacuumManufacturer);
        textInputEditTextItemsIncluded=findViewById(R.id.vacuumItemsIncluded);
        textInputEditTextPrice=findViewById(R.id.vacuumPrice);
        textInputEditTextQuantity=findViewById(R.id.vacuumQuantity);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelStr=textInputEditTextModel.getText().toString();
                operatingVoltageStr=textInputEditTextOperatingVoltage.getText().toString();
                colorStr=textInputEditTextColor.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                weightStr=textInputEditTextWeight.getText().toString();
                manufacturerStr=textInputEditTextManufacturer.getText().toString();
                itemsIncludedStr=textInputEditTextItemsIncluded.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();

                if(modelStr.isEmpty()|| operatingVoltageStr.isEmpty() || colorStr.isEmpty() || dimensionStr.isEmpty() || weightStr.isEmpty() || manufacturerStr.isEmpty() || itemsIncludedStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_VacuumCleaners.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("Model").setValue(modelStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("OperatingVoltage").setValue(operatingVoltageStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("Color").setValue(colorStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("Dimension").setValue(dimensionStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("Weight").setValue(weightStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("ItemsIncluded").setValue(itemsIncludedStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("Price").setValue(priceStr);
                            databaseReference.child("Accessories").child("VacuumCleaners").child(modelStr).child("Quantity").setValue(quantityStr);

//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_VacuumCleaners.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),admin_Add_Accessories.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_VacuumCleaners.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}