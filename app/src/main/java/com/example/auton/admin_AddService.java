package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.auton.databinding.ActivityAdminAddServiceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_AddService extends AppCompatActivity {
    private ActivityAdminAddServiceBinding binding;
    DatabaseReference databaseReference;
    String serviceStr,descStr,priceStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminAddServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.btnAddService.setOnClickListener(v -> {
            serviceStr=binding.Service.getText().toString();
            descStr=binding.Description.getText().toString();
            priceStr=binding.Price.getText().toString();
            if(priceStr.isEmpty()||serviceStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(admin_AddService.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("SERVICETYPE").child(serviceStr).child("ServiceType").setValue(serviceStr);
                        databaseReference.child("SERVICETYPE").child(serviceStr).child("Description").setValue(descStr);
                        databaseReference.child("SERVICETYPE").child(serviceStr).child("Price").setValue(priceStr);
                        Toast.makeText(admin_AddService.this, "Value Entered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_mainactivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_AddService.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}