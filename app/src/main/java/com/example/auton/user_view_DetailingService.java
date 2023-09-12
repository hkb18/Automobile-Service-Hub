package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewDetailingServiceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_DetailingService extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewDetailingServiceBinding binding;
    private DetailingService_Adapter detailingServiceAdapter;
    private final ArrayList<DetailingService_ModelClass> detailingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewDetailingServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvDetailingService.setAdapter(detailingServiceAdapter);

        binding.rvDetailingService.setHasFixedSize(true);
        binding.rvDetailingService.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("DETAILING_SERVICE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                detailingList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DetailingService_ModelClass ss = dataSnapshot.getValue(DetailingService_ModelClass.class);
                    detailingList.add(ss);
                }

                detailingServiceAdapter = new DetailingService_Adapter(user_view_DetailingService.this, detailingList);
                binding.rvDetailingService.setAdapter(detailingServiceAdapter);

                detailingServiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}