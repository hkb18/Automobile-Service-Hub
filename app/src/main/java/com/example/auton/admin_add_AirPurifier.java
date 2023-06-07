package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class admin_add_AirPurifier extends AppCompatActivity {
    Button add;
    DatabaseReference databaseReference;
    TextInputEditText textInputEditTextModel,textInputEditTextVoltage,textInputEditTextColor,textInputEditTextDimension,textInputEditTextWeight,textInputEditTextManufacturer,textInputEditTextWarrenty,textInputEditTextItemIncluded,textInputEditTextPrice,textInputEditTextQuantity;
    String modelStr,operatingVoltageStr,colorStr,dimensionStr,weightStr,manufacturerStr,warrentyStr,itemsIncludedStr,priceStr,quantityStr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_air_purifier);

        add=findViewById(R.id.btn_add_AirPurifier);
        textInputEditTextModel=findViewById(R.id.airpurifierModel);
        textInputEditTextVoltage=findViewById(R.id.airpurifierOperatingVoltage);
        textInputEditTextColor=findViewById(R.id.airpurifierColor);
        textInputEditTextDimension=findViewById(R.id.airpurifierDimensions);
        textInputEditTextWeight=findViewById(R.id.airpurifierWeight);
        textInputEditTextManufacturer=findViewById(R.id.airpurifierManufacturer);
        textInputEditTextWarrenty=findViewById(R.id.airpurifierWarrenty);
        textInputEditTextItemIncluded=findViewById(R.id.airpurifierItemsIncluded);
        textInputEditTextPrice=findViewById(R.id.airpurifierPrice);
        textInputEditTextQuantity=findViewById(R.id.airpurifierQuantity);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelStr=textInputEditTextModel.getText().toString();
                operatingVoltageStr=textInputEditTextVoltage.getText().toString();
                colorStr=textInputEditTextColor.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                weightStr=textInputEditTextWeight.getText().toString();
                manufacturerStr=textInputEditTextManufacturer.getText().toString();
                warrentyStr=textInputEditTextWarrenty.getText().toString();
                itemsIncludedStr=textInputEditTextItemIncluded.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();

                if(modelStr.isEmpty()|| operatingVoltageStr.isEmpty()||itemsIncludedStr.isEmpty() ||warrentyStr.isEmpty()|| colorStr.isEmpty() || dimensionStr.isEmpty() || weightStr.isEmpty() || weightStr.isEmpty() || manufacturerStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_AirPurifier.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Model").setValue(modelStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("OperatingVoltage").setValue(operatingVoltageStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Color").setValue(colorStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Dimenension").setValue(dimensionStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Weight").setValue(weightStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Warrenty").setValue(warrentyStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("ItemsIncluded").setValue(itemsIncludedStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Price").setValue(priceStr);
                            databaseReference.child("Accessories").child("AirPurifier").child(modelStr).child("Quantity").setValue(quantityStr);

//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_AirPurifier.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), admin_Add_Accessories.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_AirPurifier.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}