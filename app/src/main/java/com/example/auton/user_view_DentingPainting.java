package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewDentingPaintingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_DentingPainting extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ActivityUserViewDentingPaintingBinding binding;
    private DentingPainting_Adapter dentingPaintingAdapter;
    private final ArrayList<DentingPainting_ModelClass> dentingpaintingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewDentingPaintingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvDentingPainting.setAdapter(dentingPaintingAdapter);

        binding.rvDentingPainting.setHasFixedSize(true);
        binding.rvDentingPainting.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("SERVICE_TYPE").child("DENTING_PAINITNG").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dentingpaintingList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DentingPainting_ModelClass ss = dataSnapshot.getValue(DentingPainting_ModelClass.class);
                    dentingpaintingList.add(ss);
                }

                dentingPaintingAdapter = new DentingPainting_Adapter(user_view_DentingPainting.this, dentingpaintingList);
                binding.rvDentingPainting.setAdapter(dentingPaintingAdapter);

                dentingPaintingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}