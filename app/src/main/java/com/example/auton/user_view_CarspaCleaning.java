package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewCarspaCleaningBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_CarspaCleaning extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewCarspaCleaningBinding binding;
    private CarspaCleaning_Adapter carspaCleaningAdapter;
    private final ArrayList<CarspaCleaning_ModelClass> carspaCleaningList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewCarspaCleaningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvCarspaCleaning.setAdapter(carspaCleaningAdapter);

        binding.rvCarspaCleaning.setHasFixedSize(true);
        binding.rvCarspaCleaning.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("CARSPA_CLEANING").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carspaCleaningList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CarspaCleaning_ModelClass ss = dataSnapshot.getValue(CarspaCleaning_ModelClass.class);
                    carspaCleaningList.add(ss);
                }

                carspaCleaningAdapter = new CarspaCleaning_Adapter(user_view_CarspaCleaning.this, carspaCleaningList);
                binding.rvCarspaCleaning.setAdapter(carspaCleaningAdapter);

                carspaCleaningAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}