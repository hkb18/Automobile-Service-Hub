package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class admin_add_BassTubes extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button addBasstubes;
    TextInputEditText textInputEditTextModel,textInputEditTextDimension,textInputEditTextPowerOutput,textInputEditTextFrequency,textInputEditTextSalientFeature,textInputEditTextColor,
            textInputEditTextSensitivity,textInputEditTextWeight,textInputEditTextDesign,textInputEditTextManufacturer,textInputEditTextPrice,textInputEditTextQuantity;
    String modelStr,dimensionStr,poweroutputStr,frequencyStr,salientfeatureStr,colorStr,sensitivityStr,designStr,weightStr,manufacturerStr,priceStr,quantityStr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_bass_tubes);

        textInputEditTextModel=findViewById(R.id.basstubes_Model);
        textInputEditTextDimension=findViewById(R.id.basstubes_Dimensions);
        textInputEditTextPowerOutput=findViewById(R.id.basstubes_PowerOutput);
        textInputEditTextFrequency=findViewById(R.id.basstubes_Frequency);
        textInputEditTextSalientFeature=findViewById(R.id.basstubes_SalientFeature);
        textInputEditTextSensitivity=findViewById(R.id.basstubes_Sensitivity);
        textInputEditTextWeight=findViewById(R.id.basstubes_Weight);
        textInputEditTextColor=findViewById(R.id.basstubes_Color);
        textInputEditTextDesign=findViewById(R.id.basstubes_Design);
        textInputEditTextManufacturer=findViewById(R.id.basstubes_Manufacturer);
        textInputEditTextPrice=findViewById(R.id.basstubes_Price);
        textInputEditTextQuantity=findViewById(R.id.basstubes_Quantity);
        addBasstubes=findViewById(R.id.btn_bass);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        addBasstubes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelStr=textInputEditTextModel.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                poweroutputStr=textInputEditTextPowerOutput.getText().toString();
                frequencyStr=textInputEditTextFrequency.getText().toString();
                salientfeatureStr=textInputEditTextSalientFeature.getText().toString();
                colorStr=textInputEditTextColor.getText().toString();
                weightStr=textInputEditTextWeight.getText().toString();
                sensitivityStr=textInputEditTextSensitivity.getText().toString();
                designStr=textInputEditTextDesign.getText().toString();
                manufacturerStr=textInputEditTextManufacturer.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();

                if(TextUtils.isEmpty(modelStr)|| dimensionStr.isEmpty() || poweroutputStr.isEmpty() || frequencyStr.isEmpty() || salientfeatureStr.isEmpty() || colorStr.isEmpty() || weightStr.isEmpty() ||sensitivityStr.isEmpty() ||designStr.isEmpty() || manufacturerStr.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_BassTubes.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(modelStr) && snapshot.hasChild(manufacturerStr)){
                                Toast.makeText(admin_add_BassTubes.this,"Already existing Model",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),admin_HomePage.class);
                                startActivity(i);
                            }
                            else {
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Model").setValue(modelStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Dimension").setValue(dimensionStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("PowerOutput").setValue(poweroutputStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Frequency").setValue(frequencyStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("SalientFeature").setValue(salientfeatureStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Sensitivity").setValue(sensitivityStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Weight").setValue(weightStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Color").setValue(colorStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Design").setValue(designStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                                databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).child("Price").setValue(priceStr);
                                databaseReference.child("Accessories").child("BassTubes").child(modelStr).child("Quantity").setValue(quantityStr);
                                Toast.makeText(admin_add_BassTubes.this, "Value Entered", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),admin_Add_Accessories.class);
                                startActivity(i);
                            }}

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_BassTubes.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });


    }
}