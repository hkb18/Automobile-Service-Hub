package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewAcserviceRepairBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_ACServiceRepair extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewAcserviceRepairBinding binding;
    private ACServiceRepair_Adapter acservicerepairAdapter;
    private final ArrayList<ACServiceRepair_ModelClass> acservicerepairList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewAcserviceRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvACServiceRepair.setAdapter(acservicerepairAdapter);

        binding.rvACServiceRepair.setHasFixedSize(true);
        binding.rvACServiceRepair.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("ACservice_Repair").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                acservicerepairList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ACServiceRepair_ModelClass ss = dataSnapshot.getValue(ACServiceRepair_ModelClass.class);
                    acservicerepairList.add(ss);
                }

                acservicerepairAdapter = new ACServiceRepair_Adapter(user_view_ACServiceRepair.this, acservicerepairList);
                binding.rvACServiceRepair.setAdapter(acservicerepairAdapter);

                acservicerepairAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}