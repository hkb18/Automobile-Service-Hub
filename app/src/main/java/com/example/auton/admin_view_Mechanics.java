package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityAdminViewMechanicsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_view_Mechanics extends AppCompatActivity {
    String username;
    DatabaseReference databaseReference;
    Mechanic_Adapter2 adapter;
    ArrayList<Mechanic> list = new ArrayList<>();
    private ActivityAdminViewMechanicsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewMechanicsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        username = getIntent().getStringExtra("key");
        Toast.makeText(this, "" + username, Toast.LENGTH_SHORT).show();


        binding.rvUsers.setHasFixedSize(true);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Mechanic_Adapter2(admin_view_Mechanics.this, list);
        binding.rvUsers.setAdapter(adapter);

        databaseReference.child("Workshop_Profile").child(username).child("Mechanic_Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Mechanic ap = dataSnapshot.getValue(Mechanic.class);
                    list.add(ap);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}