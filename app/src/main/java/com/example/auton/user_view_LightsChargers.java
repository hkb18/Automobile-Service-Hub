package com.example.auton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewLightsChargersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_LightsChargers extends AppCompatActivity implements LightsChargers_Interface {
    static LightsChargers_Interface lightsChargersInterface;
    DatabaseReference databaseReference;
    String ccpModelStr;
    private ActivityUserViewLightsChargersBinding binding;
    private final ArrayList<materialButton> list = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> accessoriesList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> chargersList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> hidList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> projectorsList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> mobileholdersList = new ArrayList<>();
    private MobileHolder_Adapter mobileHolderAdapter;
    private HID_Adapter hidAdapter;
    private Projectors_Adapter projectorsAdapter;
    private Charger_Adapter chargerAdapter;
    private LightsChargers_Adapter lightsChargers_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewLightsChargersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lightsChargersInterface = this;//interface

        binding.rvLightsChargers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        lightsChargers_adapter = new LightsChargers_Adapter(this, list);
        binding.rvLightsChargers.setAdapter(lightsChargers_adapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("LIGHTS_CHARGERS").addListenerForSingleValueEvent(new ValueEventListener() {
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
                lightsChargers_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(user_view_LightsChargers.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    Chargers
        binding.rvItemsChargers.setAdapter(chargerAdapter);

        binding.rvItemsChargers.setHasFixedSize(true);
        binding.rvItemsChargers.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("LIGHTS_CHARGERS").child("Chargers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chargersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    chargersList.add(ap);
                }

                chargerAdapter = new Charger_Adapter(user_view_LightsChargers.this, chargersList, snapshot.getKey());
                binding.rvItemsChargers.setAdapter(chargerAdapter);

                chargerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    HID
        binding.rvItemsHID.setAdapter(hidAdapter);

        binding.rvItemsHID.setHasFixedSize(true);
        binding.rvItemsHID.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("LIGHTS_CHARGERS").child("HID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hidList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    hidList.add(ap);
                }

                hidAdapter = new HID_Adapter(user_view_LightsChargers.this, hidList, snapshot.getKey());
                binding.rvItemsHID.setAdapter(hidAdapter);

                hidAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    Projectors
        binding.rvItemsProjectors.setAdapter(projectorsAdapter);

        binding.rvItemsProjectors.setHasFixedSize(true);
        binding.rvItemsProjectors.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("LIGHTS_CHARGERS").child("Projectors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                projectorsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    projectorsList.add(ap);
                }

                projectorsAdapter = new Projectors_Adapter(user_view_LightsChargers.this, projectorsList, snapshot.getKey());
                binding.rvItemsProjectors.setAdapter(projectorsAdapter);

                projectorsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////    Mobile Holder
        binding.rvItemsMobileHolders.setAdapter(mobileHolderAdapter);

        binding.rvItemsMobileHolders.setHasFixedSize(true);
        binding.rvItemsMobileHolders.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("LIGHTS_CHARGERS").child("MobileHolder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mobileholdersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    mobileholdersList.add(ap);
                }

                mobileHolderAdapter = new MobileHolder_Adapter(user_view_LightsChargers.this, mobileholdersList, snapshot.getKey());
                binding.rvItemsMobileHolders.setAdapter(mobileHolderAdapter);

                mobileHolderAdapter.notifyDataSetChanged();
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
            databaseReference.child("Accessories").child("LIGHTS_CHARGERS").child(Model).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Model.equalsIgnoreCase("Chargers")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(chargerAdapter);
                        chargerAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("HID")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(hidAdapter);
                        hidAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Projectors")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(projectorsAdapter);
                        projectorsAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("MobileHolder")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(mobileHolderAdapter);
                        mobileHolderAdapter.notifyDataSetChanged();
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