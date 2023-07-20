package com.example.auton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddBodyTypeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_AddBodyType extends AppCompatActivity {
    DatabaseReference databaseReference;
    String bodytypeStr, priceStr;
    private ActivityAdminAddBodyTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddBodyTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.btnAddCarBodyType.setOnClickListener(v -> {
            bodytypeStr = binding.carBodyType.getText().toString();
            priceStr = binding.carBodyTypePrice.getText().toString();
            if (priceStr.isEmpty() || bodytypeStr.isEmpty()) {
                Toast.makeText(admin_AddBodyType.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("CAR_BODYTYPE").child(bodytypeStr).child("BodyType").setValue(bodytypeStr);
                        databaseReference.child("CAR_BODYTYPE").child(bodytypeStr).child("Price").setValue(priceStr);
                        Toast.makeText(admin_AddBodyType.this, "Value Entered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_mainactivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_AddBodyType.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}