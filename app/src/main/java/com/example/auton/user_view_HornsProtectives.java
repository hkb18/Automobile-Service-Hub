package com.example.auton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewHornsProtectivesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_HornsProtectives extends AppCompatActivity implements HornsProtectives_Interface {
    static HornsProtectives_Interface hornsProtectivesInterface;
    DatabaseReference databaseReference;
    String ccpModelStr;
    private ActivityUserViewHornsProtectivesBinding binding;
    private final ArrayList<materialButton> list = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> accessoriesList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> hornsList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> protectivesList = new ArrayList<>();
    private Horns_Adapter hornsAdapter;
    private HornsProtectives_Adapter hornsProtectives_adapter;
    private Protectives_Adapter protectivesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewHornsProtectivesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hornsProtectivesInterface = this;//interface

        binding.rvHornsProtectives.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hornsProtectives_adapter = new HornsProtectives_Adapter(this, list);
        binding.rvHornsProtectives.setAdapter(hornsProtectives_adapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                materialButton button1 = new materialButton();
                button1.setName("All");
                button1.setSelected(true);
                list.add(button1);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    materialButton button = new materialButton();
                    button.setName(dataSnapshot.getKey());
                    button.setSelected(false);
                    list.add(button);
                }
                hornsProtectives_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(user_view_HornsProtectives.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /////////////////HORNS
        binding.rvHorns.setAdapter(hornsAdapter);

        binding.rvHorns.setHasFixedSize(true);
        binding.rvHorns.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hornsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    hornsList.add(ap);
                }

                hornsAdapter = new Horns_Adapter(user_view_HornsProtectives.this, hornsList, snapshot.getKey());
                binding.rvHorns.setAdapter(hornsAdapter);

                hornsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        /////////////////PROTECTIVES
        binding.rvProtectives.setAdapter(protectivesAdapter);

        binding.rvProtectives.setHasFixedSize(true);
        binding.rvProtectives.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Protectives").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                protectivesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    protectivesList.add(ap);
                }

                protectivesAdapter = new Protectives_Adapter(user_view_HornsProtectives.this, protectivesList, snapshot.getKey());
                binding.rvProtectives.setAdapter(protectivesAdapter);

                protectivesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void details(String Model) {

    }

    @Override
    public void onClickItem(String Model) {
        if (Model.equalsIgnoreCase("All")) {
            binding.rv.setVisibility(View.GONE);
            binding.scrollViewItems.setVisibility(View.VISIBLE);
        } else {
            binding.rv.setVisibility(View.VISIBLE);
            binding.scrollViewItems.setVisibility(View.GONE);
            binding.rv.setLayoutManager(new LinearLayoutManager(this));
            databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child(Model).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Model.equalsIgnoreCase("Horns")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(hornsAdapter);
                        hornsAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Protectives")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(protectivesAdapter);
                        protectivesAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}