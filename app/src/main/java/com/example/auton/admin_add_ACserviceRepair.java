package com.example.auton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddAcserviceRepairBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_add_ACserviceRepair extends AppCompatActivity {
    DatabaseReference databaseReference;
    String servicenameStr, essentialserviceStr, performanceserviceStr, additionalserviceStr, priceStr, pk;
    private ActivityAdminAddAcserviceRepairBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddAcserviceRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.btnAddService.setOnClickListener(view -> {
            servicenameStr = binding.serviceName.getText().toString();
            essentialserviceStr = binding.essentialServices.getText().toString();
            performanceserviceStr = binding.performanceServices.getText().toString();
            additionalserviceStr = binding.additionalServices.getText().toString();
            priceStr = binding.price.getText().toString();

            if (servicenameStr.isEmpty() || essentialserviceStr.isEmpty() || performanceserviceStr.isEmpty() || additionalserviceStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Please Enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("SERVICE_TYPE").child("PERIODIC_SERVICE").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pk = databaseReference.push().getKey();
                        databaseReference.child("SERVICE_TYPE").child("ACservice_Repair").child(servicenameStr).child("ServiceName").setValue(servicenameStr);
                        databaseReference.child("SERVICE_TYPE").child("ACservice_Repair").child(servicenameStr).child("EssentialServices").setValue(essentialserviceStr);
                        databaseReference.child("SERVICE_TYPE").child("ACservice_Repair").child(servicenameStr).child("PerformanceServices").setValue(performanceserviceStr);
                        databaseReference.child("SERVICE_TYPE").child("ACservice_Repair").child(servicenameStr).child("AdditionalServices").setValue(additionalserviceStr);
                        databaseReference.child("SERVICE_TYPE").child("ACservice_Repair").child(servicenameStr).child("Price").setValue(priceStr);
                        databaseReference.child("SERVICE_TYPE").child("ACservice_Repair").child(servicenameStr).child("Key").setValue(pk);

                        Toast.makeText(admin_add_ACserviceRepair.this, "Value Entered Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_AddService.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_add_ACserviceRepair.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}