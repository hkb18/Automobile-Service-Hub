package com.example.auton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewScreensSpeakersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_View_Screens_Speakers extends AppCompatActivity implements AndroidScreen_Interface {
    static AndroidScreen_Interface androidScreen_interface;
    String androidscreenModelStr;
    DatabaseReference databaseReference;
    private ActivityUserViewScreensSpeakersBinding binding; //no need for findviewbyid
    private final ArrayList<materialButton> list = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> androidscreenList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> amplifierList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> basstubesList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> speakerList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> vacuumcleanersList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> accessoriesList = new ArrayList<>();
    private ScreensSpeakersAdapter adapter;
    private ScreenSpeaker_ItemAdapter ssAdapter;        //ANDROID SCREEN
    private Amplifier_Adapter amplifierAdapter;
    private BassTubes_Adapter basstubesAdapter;
    private Speaker_Adapter speakerAdapter;
    private VacuumCleaner_Adapter vacuumCleanerAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewScreensSpeakersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        androidScreen_interface = this;//interface
        binding.rvScreensSpeakers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ScreensSpeakersAdapter(this, list);
        binding.rvScreensSpeakers.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").addListenerForSingleValueEvent(new ValueEventListener() {
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
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
////////////////////////    AMPLIFIER
        binding.rvItemsAmplifier.setAdapter(amplifierAdapter);

        binding.rvItemsAmplifier.setHasFixedSize(true);
        binding.rvItemsAmplifier.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                amplifierList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                    amplifierList.add(ss);
                }

                amplifierAdapter = new Amplifier_Adapter(user_View_Screens_Speakers.this, amplifierList, snapshot.getKey());
                binding.rvItemsAmplifier.setAdapter(amplifierAdapter);

                amplifierAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    ANDROID SCREEN

        binding.rvItems.setAdapter(ssAdapter);

        binding.rvItems.setHasFixedSize(true);
        binding.rvItems.setLayoutManager(new LinearLayoutManager(this));

        //ScreeenSpeakerInterface=this;
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("AndroidScreens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                androidscreenList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                    androidscreenList.add(ss);
                }
                ssAdapter = new ScreenSpeaker_ItemAdapter(user_View_Screens_Speakers.this, androidscreenList, snapshot.getKey());
                binding.rvItems.setAdapter(ssAdapter);
                ssAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ////////////////////////    BASS TUBES
        binding.rvItemsBassTubes.setAdapter(basstubesAdapter);

        binding.rvItemsBassTubes.setHasFixedSize(true);
        binding.rvItemsBassTubes.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                basstubesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                    basstubesList.add(ss);
                }
                basstubesAdapter = new BassTubes_Adapter(user_View_Screens_Speakers.this, basstubesList, snapshot.getKey());
                binding.rvItemsBassTubes.setAdapter(basstubesAdapter);
                basstubesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    SPEAKERS

        binding.rvItemsSpeaker.setAdapter(speakerAdapter);

        binding.rvItemsSpeaker.setHasFixedSize(true);
        binding.rvItemsSpeaker.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                speakerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                    speakerList.add(ss);
                }
                speakerAdapter = new Speaker_Adapter(user_View_Screens_Speakers.this, speakerList, snapshot.getKey());
                binding.rvItemsSpeaker.setAdapter(speakerAdapter);
                speakerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ////////////////////////    VACUUM  CLEANER
        binding.rvItemsVacuumCleaner.setAdapter(vacuumCleanerAdapter);

        binding.rvItemsVacuumCleaner.setHasFixedSize(true);
        binding.rvItemsVacuumCleaner.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("VacuumCleaners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vacuumcleanersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                    vacuumcleanersList.add(ss);
                }
                vacuumCleanerAdapter = new VacuumCleaner_Adapter(user_View_Screens_Speakers.this, vacuumcleanersList, snapshot.getKey());
                binding.rvItemsVacuumCleaner.setAdapter(vacuumCleanerAdapter);
                vacuumCleanerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void details(String Model) {
        androidscreenModelStr = Model;
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

            databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child(Model).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Model.equalsIgnoreCase("Amplifiers")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(amplifierAdapter);
                        amplifierAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("AndroidScreens")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(ssAdapter);
                        ssAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("BassTubes")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(basstubesAdapter);
                        basstubesAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Speaker")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(speakerAdapter);
                        speakerAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("VacuumCleaners")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(vacuumCleanerAdapter);
                        vacuumCleanerAdapter.notifyDataSetChanged();
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
