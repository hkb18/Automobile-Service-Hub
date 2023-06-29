package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.auton.databinding.ActivityUserViewCarecarePurifiersBinding;
import com.example.auton.databinding.ActivityUserViewFloormatsCushionsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_View_FloormatsCushions extends AppCompatActivity implements FloormatCushions_Interface{
    private ActivityUserViewFloormatsCushionsBinding binding;
    static FloormatCushions_Interface floormatCushions_interface;
    DatabaseReference databaseReference;
    String ccpModelStr;
    private ArrayList<materialButton> list=new ArrayList<>();
    private AirFreshner_Adapter airFreshnerAdapter;
    private CleaningKit_Adapter cleaningKitAdapter;
    private Cleansers_Adapter cleansersAdapter;
    private Detailing_Adapter detailingAdapter;
    private MicroFibres_Adapter microFibresAdapter;
    private Washers_Adapter washersAdapter;
    private WiperBlades_Adapter wiperBladesAdapter;
    private ArrayList<AirFreshner_ModelClass> airfreshnerList= new ArrayList<>();
    private ArrayList<CleaningKit_ModelClass> cleaningkitList= new ArrayList<>();
    private ArrayList<Cleaners_ModelClass> cleansersList= new ArrayList<>();
    private ArrayList<Detailing_ModelClass> detailingList= new ArrayList<>();
    private ArrayList<MicroFibres_ModelClass> microfibresList= new ArrayList<>();
    private ArrayList<Washers_ModelClass> washersList= new ArrayList<>();
    private ArrayList<WiperBlades_ModelClass> wiperbladesList= new ArrayList<>();
    private  FloormatCushions_Adapter floormatCushionsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserViewFloormatsCushionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        floormatCushions_interface=this;//interface

        binding.rvFloormatCushions.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        floormatCushionsAdapter=new FloormatCushions_Adapter(this,list);
        binding.rvFloormatCushions.setAdapter(floormatCushionsAdapter);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("FLOORMATS_CUSHIONS").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    AirFreshner_ModelClass ap = dataSnapshot.getValue(AirFreshner_ModelClass.class);
                    airfreshnerList.add(ap);
                }

                airFreshnerAdapter =new AirFreshner_Adapter(user_View_FloormatsCushions.this,airfreshnerList, snapshot.getKey());
                binding.rvItemsAirFreshner.setAdapter(airFreshnerAdapter);

                airFreshnerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void details(String Model) {

    }

    @Override
    public void onClickItem(String Model) {

    }
}