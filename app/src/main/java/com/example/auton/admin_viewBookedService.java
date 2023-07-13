package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityAdminViewBookedServiceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_viewBookedService extends AppCompatActivity {
    DatabaseReference databaseReference;
    admin_ViewBookedService_Adapter myAdapter;
    ArrayList<Worshop_View_Service_modelClass> list = new ArrayList<>();
    private ActivityAdminViewBookedServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewBookedServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new admin_ViewBookedService_Adapter(this, list);
        binding.rv.setAdapter(myAdapter);

        databaseReference.child("Service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Worshop_View_Service_modelClass bookedService = dataSnapshot1.getValue(Worshop_View_Service_modelClass.class);
                        list.add(bookedService);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(admin_viewBookedService.this, "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}