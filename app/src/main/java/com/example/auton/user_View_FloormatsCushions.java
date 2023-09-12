package com.example.auton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewFloormatsCushionsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_View_FloormatsCushions extends AppCompatActivity implements FloormatCushions_Interface {
    static FloormatCushions_Interface floormatCushions_interface;
    DatabaseReference databaseReference;
    String ccpModelStr;
    private ActivityUserViewFloormatsCushionsBinding binding;
    private final ArrayList<materialButton> list = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> accessoriesList = new ArrayList<>();
    private AirFreshner_Adapter airFreshnerAdapter;
    private BackCushions_Adapter backCushionsAdapter;
    private Mats_Adapter matsAdapter;
    private NeckCushions_Adapter neckCushionsAdapter;
    private final ArrayList<Accessories_ModelClass> airfreshnerList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> backcushionList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> matsList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> neckcushionList = new ArrayList<>();
    private FloormatCushions_Adapter floormatCushionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewFloormatsCushionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        floormatCushions_interface = this;//interface

        binding.rvFloormatCushions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        floormatCushionsAdapter = new FloormatCushions_Adapter(this, list);
        binding.rvFloormatCushions.setAdapter(floormatCushionsAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").addListenerForSingleValueEvent(new ValueEventListener() {
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
                floormatCushionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(user_View_FloormatsCushions.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    AIR FRESHNER
        binding.rvItemsAirFreshner.setAdapter(airFreshnerAdapter);

        binding.rvItemsAirFreshner.setHasFixedSize(true);
        binding.rvItemsAirFreshner.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("AirFreshner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                airfreshnerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    airfreshnerList.add(ap);
                }

                airFreshnerAdapter = new AirFreshner_Adapter(user_View_FloormatsCushions.this, airfreshnerList, snapshot.getKey());
                binding.rvItemsAirFreshner.setAdapter(airFreshnerAdapter);

                airFreshnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    BACK CUSHIONS
        binding.rvItemsBackCushions.setAdapter(backCushionsAdapter);

        binding.rvItemsBackCushions.setHasFixedSize(true);
        binding.rvItemsBackCushions.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("BackCushions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                backcushionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    backcushionList.add(ap);
                }

                backCushionsAdapter = new BackCushions_Adapter(user_View_FloormatsCushions.this, backcushionList, snapshot.getKey());
                binding.rvItemsBackCushions.setAdapter(backCushionsAdapter);

                backCushionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    MATS
        binding.rvItemsMats.setAdapter(matsAdapter);

        binding.rvItemsMats.setHasFixedSize(true);
        binding.rvItemsMats.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("Mats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    matsList.add(ap);
                }

                matsAdapter = new Mats_Adapter(user_View_FloormatsCushions.this, matsList, snapshot.getKey());
                binding.rvItemsMats.setAdapter(matsAdapter);

                matsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    NECK CUSHIONS
        binding.rvItemsNeckCushions.setAdapter(neckCushionsAdapter);

        binding.rvItemsNeckCushions.setHasFixedSize(true);
        binding.rvItemsNeckCushions.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child("NeckCushions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                neckcushionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    neckcushionList.add(ap);
                }

                neckCushionsAdapter = new NeckCushions_Adapter(user_View_FloormatsCushions.this, neckcushionList, snapshot.getKey());
                binding.rvItemsNeckCushions.setAdapter(neckCushionsAdapter);

                neckCushionsAdapter.notifyDataSetChanged();
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
            databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").child(Model).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Model.equalsIgnoreCase("AirFreshner")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(airFreshnerAdapter);
                        airFreshnerAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("BackCushions")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(backCushionsAdapter);
                        backCushionsAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Mats")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(matsAdapter);
                        matsAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("NeckCushions")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(neckCushionsAdapter);
                        neckCushionsAdapter.notifyDataSetChanged();
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