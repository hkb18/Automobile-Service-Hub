package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.auton.databinding.ActivityUserViewCarecarePurifiersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_View_Carecare_Purifiers extends AppCompatActivity implements AndroidScreen_Interface{
    private ActivityUserViewCarecarePurifiersBinding binding;
    static AndroidScreen_Interface androidScreen_interface;
    DatabaseReference databaseReference;
    String ccpModelStr;
    private ArrayList<materialButton> list=new ArrayList<>();
    private AirPurifier_Adapter airpurifierAdapter;
    private ArrayList<Airpurifier_ModelClass> airpurifierList= new ArrayList<>();
    private  ScreensSpeakersAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserViewCarecarePurifiersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        androidScreen_interface=this;//interface

        binding.rvScreensSpeakers.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter=new ScreensSpeakersAdapter(this,list);
        binding.rvScreensSpeakers.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                materialButton button1=new materialButton();
                button1.setName("All");
                button1.setSelected(true);
                list.add(button1);
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

        ////////////////////////    AIR PURIFIER
        binding.rvItemsAirpurifier.setAdapter(airpurifierAdapter);

        binding.rvItemsAirpurifier.setHasFixedSize(true);
        binding.rvItemsAirpurifier.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                airpurifierList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Airpurifier_ModelClass ap = dataSnapshot.getValue(Airpurifier_ModelClass.class);
                    airpurifierList.add(ap);
                }

                airpurifierAdapter =new AirPurifier_Adapter(user_View_Carecare_Purifiers.this,airpurifierList, snapshot.getKey());
                binding.rvItemsAirpurifier.setAdapter(airpurifierAdapter);

                airpurifierAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void details(String Model) {
        ccpModelStr=Model;
    }

    @Override
    public void onClickItem(String Model) {
        if (Model.equalsIgnoreCase("All")){
            binding.rv.setVisibility(View.GONE);
            binding.scrollViewItems.setVisibility(View.VISIBLE);
        }else {
            binding.rv.setVisibility(View.VISIBLE);
            binding.scrollViewItems.setVisibility(View.GONE);
            binding.rv.setLayoutManager(new LinearLayoutManager(this));


            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child(Model).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Model.equalsIgnoreCase("AirPurifier")){
                        airpurifierList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Airpurifier_ModelClass ss = dataSnapshot.getValue(Airpurifier_ModelClass.class);
                            airpurifierList.add(ss);
                        }
                        binding.rv.setAdapter(airpurifierAdapter);
                        airpurifierAdapter.notifyDataSetChanged();
                    }
                    /*else if (Model.equalsIgnoreCase("CleaningKit")) {
                        ssList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ScreensSpeakers_ModelClass ss = dataSnapshot.getValue(ScreensSpeakers_ModelClass.class);
                            ssList.add(ss);
                        }
                        binding.rv.setAdapter(ssAdapter);
                        ssAdapter.notifyDataSetChanged();
                    }
                    else if (Model.equalsIgnoreCase("Cleansers")) {
                        basstubesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            BassTubes_ModelClass ss = dataSnapshot.getValue(BassTubes_ModelClass.class);
                            basstubesList.add(ss);
                        }
                        binding.rv.setAdapter(basstubesAdapter);
                        basstubesAdapter.notifyDataSetChanged();
                    }
                    else if (Model.equalsIgnoreCase("Detailing")) {
                        speakerList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Speaker_ModelClass ss = dataSnapshot.getValue(Speaker_ModelClass.class);
                            speakerList.add(ss);
                        }
                        binding.rv.setAdapter(speakerAdapter);
                        speakerAdapter.notifyDataSetChanged();
                    }
                    else if (Model.equalsIgnoreCase("MicroFibres")) {
                        vacuumCleanerList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            VacuumCleaner_ModelClass ss = dataSnapshot.getValue(VacuumCleaner_ModelClass.class);
                            vacuumCleanerList.add(ss);
                        }
                        binding.rv.setAdapter(vacuumCleanerAdapter);
                        vacuumCleanerAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Washers")) {
                        
                    }else if (Model.equalsIgnoreCase("WiperBlades")){

                    }*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}