package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewBatteriesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_Batteries extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewBatteriesBinding binding;
    private Batteries_Adapter batteriesAdapter;
    private final ArrayList<Batteries_ModelClass> batteriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewBatteriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvBatteries.setAdapter(batteriesAdapter);

        binding.rvBatteries.setHasFixedSize(true);
        binding.rvBatteries.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("Batteries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                batteriesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Batteries_ModelClass ss = dataSnapshot.getValue(Batteries_ModelClass.class);
                    batteriesList.add(ss);
                }

                batteriesAdapter = new Batteries_Adapter(user_view_Batteries.this, batteriesList);
                binding.rvBatteries.setAdapter(batteriesAdapter);

                batteriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}