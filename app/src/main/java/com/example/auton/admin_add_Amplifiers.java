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

public class admin_add_Amplifiers extends AppCompatActivity {
    Button add;
    TextInputEditText textInputEditTextModel,textInputEditTextMaxVoltage,textInputEditTextMountingHardware,textInputEditTextDimension,textInputEditTextChannels,textInputEditTextWeight,textInputEditTextManufacturer,textInputEditTextPrice,textInputEditTextQuantity;
    String modelStr,maxVoltageStr,mountingHardwareStr,dimensionStr,channelStr,weightStr,manufacturerStr,priceStr,quantityStr;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_amplifiers);

        add=findViewById(R.id.btn_add_Amplifier);
        textInputEditTextModel=findViewById(R.id.amplifierModel);
        textInputEditTextMaxVoltage=findViewById(R.id.amplifierMaxVoltage);
        textInputEditTextMountingHardware=findViewById(R.id.amplifierMountingHardware);
        textInputEditTextDimension=findViewById(R.id.amplifierDimensions);
        textInputEditTextChannels=findViewById(R.id.amplifierChannels);
        textInputEditTextWeight=findViewById(R.id.amplifierWeight);
        textInputEditTextManufacturer=findViewById(R.id.amplifierManufacturer);
        textInputEditTextPrice=findViewById(R.id.amplifierPrice);
        textInputEditTextQuantity=findViewById(R.id.amplifierQuantity);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelStr=textInputEditTextModel.getText().toString();
                maxVoltageStr=textInputEditTextMaxVoltage.getText().toString();
                mountingHardwareStr=textInputEditTextMountingHardware.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                channelStr=textInputEditTextChannels.getText().toString();
                weightStr=textInputEditTextWeight.getText().toString();
                manufacturerStr=textInputEditTextManufacturer.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();

                if(modelStr.isEmpty()|| maxVoltageStr.isEmpty() || mountingHardwareStr.isEmpty() || dimensionStr.isEmpty() || channelStr.isEmpty() || weightStr.isEmpty() || manufacturerStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_Amplifiers.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("Model").setValue(modelStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("MaxVoltage").setValue(maxVoltageStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("MountingHardware").setValue(mountingHardwareStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("Dimenension").setValue(dimensionStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("Channel").setValue(channelStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("Weight").setValue(weightStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("Price").setValue(priceStr);
                            databaseReference.child("Accessories").child("Amplifiers").child(modelStr).child("Quantity").setValue(quantityStr);

//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_Amplifiers.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), admin_Add_Accessories.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_Amplifiers.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}