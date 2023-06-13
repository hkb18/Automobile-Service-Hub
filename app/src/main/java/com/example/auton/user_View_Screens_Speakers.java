package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.denzcoskun.imageslider.adapters.ViewPagerAdapter;
import com.example.auton.databinding.ActivityUserViewScreensSpeakersBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class user_View_Screens_Speakers extends AppCompatActivity {
    private ActivityUserViewScreensSpeakersBinding binding; //no need for findviewbyid


    private ArrayList<materialButton> list;
    private ArrayList<ScreensSpeakers_ModelClass> ssList;   //ANDROID SCREEN
    private ArrayList<Amplifier_ModelClass> ampList;
    private ArrayList<BassTubes_ModelClass> basstubesList;
    private ArrayList<Speaker_ModelClass> speakerList;
    private ArrayList<VacuumCleaner_ModelClass> vacuumCleanerList;
    private  ScreensSpeakersAdapter adapter;
    private ScreenSpeaker_ItemAdapter ssAdapter;        //ANDROID SCREEN
    private Amplifier_Adapter amplifierAdapter;
    private BassTubes_Adapter basstubesAdapter;
    private Speaker_Adapter speakerAdapter;
    private VacuumCleaner_Adapter vacuumCleanerAdapter;
    DatabaseReference databaseReference;
    public static ScreeenSpeakerInterface ScreeenSpeakerInterface;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserViewScreensSpeakersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list=new ArrayList<>();

        binding.rvScreensSpeakers.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter=new ScreensSpeakersAdapter(this,list);
        binding.rvScreensSpeakers.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    materialButton button=new materialButton();
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


        ////////////////////////    ANDROID SCREEN

        binding.rvItems.setAdapter(ssAdapter);
        ssList=new ArrayList<>();

        binding.rvItems.setHasFixedSize(true);
        binding.rvItems.setLayoutManager(new GridLayoutManager(this,2));

        ssAdapter =new ScreenSpeaker_ItemAdapter(this,ssList);
        binding.rvItems.setAdapter(ssAdapter);

        //ScreeenSpeakerInterface=this;

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("AndroidScreens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ssList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ScreensSpeakers_ModelClass ss = dataSnapshot.getValue(ScreensSpeakers_ModelClass.class);
                    ssList.add(ss);
                }
                ssAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        ////////////////////////    AMPLIFIER
        binding.rvItemsAmplifier.setAdapter(amplifierAdapter);
        ampList=new ArrayList<>();

        binding.rvItemsAmplifier.setHasFixedSize(true);
        binding.rvItemsAmplifier.setLayoutManager(new LinearLayoutManager(this));

        amplifierAdapter =new Amplifier_Adapter(this,ampList);
        binding.rvItemsAmplifier.setAdapter(amplifierAdapter);

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ampList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Amplifier_ModelClass ss = dataSnapshot.getValue(Amplifier_ModelClass.class);
                    ampList.add(ss);
                }
                ssAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    BASS TUBES
        binding.rvItemsBassTubes.setAdapter(basstubesAdapter);
        basstubesList=new ArrayList<>();

        binding.rvItemsBassTubes.setHasFixedSize(true);
        binding.rvItemsBassTubes.setLayoutManager(new LinearLayoutManager(this));

        basstubesAdapter =new BassTubes_Adapter(this,basstubesList);
        binding.rvItemsBassTubes.setAdapter(basstubesAdapter);

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                basstubesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BassTubes_ModelClass ss = dataSnapshot.getValue(BassTubes_ModelClass.class);
                    basstubesList.add(ss);
                }
                basstubesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        ////////////////////////    SPEAKERS
        binding.rvItemsSpeaker.setAdapter(speakerAdapter);
        speakerList=new ArrayList<>();

        binding.rvItemsSpeaker.setHasFixedSize(true);
        binding.rvItemsSpeaker.setLayoutManager(new LinearLayoutManager(this));

        speakerAdapter =new Speaker_Adapter(this,speakerList);
        binding.rvItemsSpeaker.setAdapter(speakerAdapter);

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                speakerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Speaker_ModelClass ss = dataSnapshot.getValue(Speaker_ModelClass.class);
                    speakerList.add(ss);
                }
                basstubesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        ////////////////////////    VACUUM  CLEANER
        binding.rvItemsVacuumCleaner.setAdapter(vacuumCleanerAdapter);
        vacuumCleanerList=new ArrayList<>();

        binding.rvItemsVacuumCleaner.setHasFixedSize(true);
        binding.rvItemsVacuumCleaner.setLayoutManager(new LinearLayoutManager(this));

        vacuumCleanerAdapter =new VacuumCleaner_Adapter(this,vacuumCleanerList);
        binding.rvItemsVacuumCleaner.setAdapter(vacuumCleanerAdapter);

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("VacuumCleaners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vacuumCleanerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Speaker_ModelClass ss = dataSnapshot.getValue(Speaker_ModelClass.class);
                    speakerList.add(ss);
                }
                vacuumCleanerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
