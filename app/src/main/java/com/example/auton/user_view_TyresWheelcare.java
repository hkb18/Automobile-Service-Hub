package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewTyresWheelcareBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_TyresWheelcare extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewTyresWheelcareBinding binding;
    private TyresWheelcare_Adapter tyresWheelcareAdapter;
    private final ArrayList<TyresWheelcare_ModelClass> tyreswheelcareList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewTyresWheelcareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvTyreWheelcare.setAdapter(tyresWheelcareAdapter);

        binding.rvTyreWheelcare.setHasFixedSize(true);
        binding.rvTyreWheelcare.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tyreswheelcareList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TyresWheelcare_ModelClass ss = dataSnapshot.getValue(TyresWheelcare_ModelClass.class);
                    tyreswheelcareList.add(ss);
                }

                tyresWheelcareAdapter = new TyresWheelcare_Adapter(user_view_TyresWheelcare.this, tyreswheelcareList);
                binding.rvTyreWheelcare.setAdapter(tyresWheelcareAdapter);

                tyresWheelcareAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}