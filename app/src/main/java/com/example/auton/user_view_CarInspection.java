package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewCarInspectionBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_CarInspection extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewCarInspectionBinding binding;
    private CarInspection_Adapter carInspectionAdapter;
    private final ArrayList<CarInspection_ModelClass> carinspectionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewCarInspectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvCarInspection.setAdapter(carInspectionAdapter);

        binding.rvCarInspection.setHasFixedSize(true);
        binding.rvCarInspection.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("CARINSPECTION").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carinspectionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CarInspection_ModelClass ss = dataSnapshot.getValue(CarInspection_ModelClass.class);
                    carinspectionList.add(ss);
                }

                carInspectionAdapter = new CarInspection_Adapter(user_view_CarInspection.this, carinspectionList);
                binding.rvCarInspection.setAdapter(carInspectionAdapter);

                carInspectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}