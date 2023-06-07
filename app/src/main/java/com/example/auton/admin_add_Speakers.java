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

public class admin_add_Speakers extends AppCompatActivity {
    TextInputEditText textInputEditTextSpeakerType,textInputEditTextPowerOutput,textInputEditTextFrequency,textInputEditTextSensitivity,textInputEditTextDiameter,textInputEditTextManufacturer,textInputEditTextColor,textInputEditTextPrice,textInputEditTextQuantity;
    Button add;
    String SpeakerTypeStr,PowerOutputStr,FrequencyStr,SensitivityStr,DiameterStr,ColorStr,ManufacturerStr,PriceStr,QuantityStr;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_speakers);

        add=findViewById(R.id.btn_addSpeaker);
        textInputEditTextSpeakerType=findViewById(R.id.speakerType);
        textInputEditTextPowerOutput=findViewById(R.id.powerOutput);
        textInputEditTextFrequency=findViewById(R.id.frequency);
        textInputEditTextSensitivity=findViewById(R.id.sensitivity);
        textInputEditTextDiameter=findViewById(R.id.diameter);
        textInputEditTextManufacturer=findViewById(R.id.speakerManufacturer);
        textInputEditTextColor=findViewById(R.id.color);
        textInputEditTextPrice=findViewById(R.id.speakerPrice);
        textInputEditTextQuantity=findViewById(R.id.speakerQuantity);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeakerTypeStr=textInputEditTextSpeakerType.getText().toString();
                PowerOutputStr=textInputEditTextPowerOutput.getText().toString();
                FrequencyStr=textInputEditTextFrequency.getText().toString();
                SensitivityStr=textInputEditTextSensitivity.getText().toString();
                DiameterStr=textInputEditTextDiameter.getText().toString();
                ManufacturerStr=textInputEditTextManufacturer.getText().toString();
                ColorStr=textInputEditTextColor.getText().toString();
                PriceStr=textInputEditTextPrice.getText().toString();
                QuantityStr=textInputEditTextQuantity.getText().toString();

                if(TextUtils.isEmpty(SpeakerTypeStr)|| PowerOutputStr.isEmpty() || FrequencyStr.isEmpty() || SensitivityStr.isEmpty() || DiameterStr.isEmpty() || ManufacturerStr.isEmpty() || ColorStr.isEmpty() ||PriceStr.isEmpty() ||  QuantityStr.isEmpty()) {
                    Toast.makeText(admin_add_Speakers.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Accessories").child("Speakers").child(ManufacturerStr).child("SpeakerType").setValue(SpeakerTypeStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("PowerOutput").setValue(PowerOutputStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("Frequency").setValue(FrequencyStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("Sensitivity").setValue(SensitivityStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("Diameter").setValue(DiameterStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("Manufacturer").setValue(ManufacturerStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("Color").setValue(ColorStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("Price").setValue(PriceStr);
                            databaseReference.child("Accessories").child("Speaker").child(ManufacturerStr).child("Quantity").setValue(QuantityStr);

//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_Speakers.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),admin_Add_Accessories.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_Speakers.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}