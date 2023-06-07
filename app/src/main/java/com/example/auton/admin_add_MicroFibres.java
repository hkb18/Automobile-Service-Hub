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

public class admin_add_MicroFibres extends AppCompatActivity {
    Button add;
    TextInputEditText textInputEditTextDimension,textInputEditTextMaterialType,textInputEditTextFabricType,textInputEditTextQuantity,textInputEditTextColor,textInputEditTextBrand,textInputEditTextPrice;
    DatabaseReference databaseReference;
    String dimensionStr,materialTypeStr,fabricTypeStr,quantityStr,priceStr,colorStr,brandStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_micro_fibres);

        add=findViewById(R.id.btn_add_MicroFibres);
        textInputEditTextDimension=findViewById(R.id.microfibresDimensions);
        textInputEditTextMaterialType=findViewById(R.id.microfibresMaterialType);
        textInputEditTextFabricType=findViewById(R.id.microfibresFabricType);
        textInputEditTextQuantity=findViewById(R.id.microfibresQuantity);
        textInputEditTextColor=findViewById(R.id.microfibresColor);
        textInputEditTextBrand=findViewById(R.id.microfibresBrand);
        textInputEditTextPrice=findViewById(R.id.microfibresPrice);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimensionStr=textInputEditTextDimension.getText().toString();
                materialTypeStr=textInputEditTextMaterialType.getText().toString();
                fabricTypeStr=textInputEditTextFabricType.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();
                colorStr=textInputEditTextColor.getText().toString();
                brandStr=textInputEditTextBrand.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                if(dimensionStr.isEmpty()|| materialTypeStr.isEmpty()||fabricTypeStr.isEmpty() ||quantityStr.isEmpty()|| colorStr.isEmpty() || brandStr.isEmpty() ||priceStr.isEmpty()) {
                    Toast.makeText(admin_add_MicroFibres.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Accessories").child("MicroFibres").child(brandStr).child("Dimension").setValue(dimensionStr);
                            databaseReference.child("Accessories").child("MicroFibres").child(brandStr).child("MaterialType").setValue(materialTypeStr);
                            databaseReference.child("Accessories").child("MicroFibres").child(brandStr).child("FabricType").setValue(fabricTypeStr);
                            databaseReference.child("Accessories").child("MicroFibres").child(brandStr).child("Quantity").setValue(quantityStr);
                            databaseReference.child("Accessories").child("MicroFibres").child(brandStr).child("Color").setValue(colorStr);
                            databaseReference.child("Accessories").child("MicroFibres").child(brandStr).child("Brand").setValue(brandStr);
                            databaseReference.child("Accessories").child("MicroFibres").child(brandStr).child("Price").setValue(priceStr);

//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_MicroFibres.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), admin_Add_Accessories.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_MicroFibres.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}