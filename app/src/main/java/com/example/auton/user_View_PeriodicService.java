package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewPeriodicServiceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_View_PeriodicService extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewPeriodicServiceBinding binding;
    private PeriodicService_Adapter periodicServiceAdapter;
    private final ArrayList<PeriodicService_ModelClass> periodicserviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewPeriodicServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvPeriodicService.setAdapter(periodicServiceAdapter);

        binding.rvPeriodicService.setHasFixedSize(true);
        binding.rvPeriodicService.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("PERIODIC_SERVICE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                periodicserviceList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PeriodicService_ModelClass ss = dataSnapshot.getValue(PeriodicService_ModelClass.class);
                    periodicserviceList.add(ss);
                }

                periodicServiceAdapter = new PeriodicService_Adapter(user_View_PeriodicService.this, periodicserviceList);
                binding.rvPeriodicService.setAdapter(periodicServiceAdapter);

                periodicServiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}