package com.example.auton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewCarecarePurifiersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_View_Carecare_Purifiers extends AppCompatActivity implements CarcarePurifiers_Interface {
    static CarcarePurifiers_Interface carcarePurifiersInterface;
    DatabaseReference databaseReference;
    String ccpModelStr;
    private ActivityUserViewCarecarePurifiersBinding binding;
    private final ArrayList<materialButton> list = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> accessoriesList = new ArrayList<>();
    private AirPurifier_Adapter airpurifierAdapter;
    private CleaningKit_Adapter cleaningKitAdapter;
    private Cleansers_Adapter cleansersAdapter;
    private Detailing_Adapter detailingAdapter;
    private MicroFibres_Adapter microFibresAdapter;
    private Washers_Adapter washersAdapter;
    private WiperBlades_Adapter wiperBladesAdapter;
    private final ArrayList<Accessories_ModelClass> airpurifierList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> cleaningkitList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> cleansersList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> detailingList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> microfibresList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> washersList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> wiperbladesList = new ArrayList<>();
    private CarecarePurifiers_Adapter carecarePurifiers_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewCarecarePurifiersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        carcarePurifiersInterface = this;//interface

        binding.rvCarecarePurifiers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        carecarePurifiers_adapter = new CarecarePurifiers_Adapter(this, list);
        binding.rvCarecarePurifiers.setAdapter(carecarePurifiers_adapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").addListenerForSingleValueEvent(new ValueEventListener() {
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
                carecarePurifiers_adapter.notifyDataSetChanged();
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
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    airpurifierList.add(ap);
                }

                airpurifierAdapter = new AirPurifier_Adapter(user_View_Carecare_Purifiers.this, airpurifierList, snapshot.getKey());
                binding.rvItemsAirpurifier.setAdapter(airpurifierAdapter);

                airpurifierAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ///////CLEANING KIT
        binding.rvItemsCleaningKit.setAdapter(cleaningKitAdapter);

        binding.rvItemsCleaningKit.setHasFixedSize(true);
        binding.rvItemsCleaningKit.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cleaningkitList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    cleaningkitList.add(ap);
                }

                cleaningKitAdapter = new CleaningKit_Adapter(user_View_Carecare_Purifiers.this, cleaningkitList, snapshot.getKey());
                binding.rvItemsCleaningKit.setAdapter(cleaningKitAdapter);

                cleaningKitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ////////CLEANSERS
        binding.rvItemsCleansers.setAdapter(cleansersAdapter);

        binding.rvItemsCleansers.setHasFixedSize(true);
        binding.rvItemsCleansers.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Cleansers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    cleansersList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                        cleansersList.add(ap);
                    }

                    cleansersAdapter = new Cleansers_Adapter(user_View_Carecare_Purifiers.this, cleansersList, snapshot.getKey());
                    binding.rvItemsCleansers.setAdapter(cleansersAdapter);

                    cleansersAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ///////DETAILING
        binding.rvItemsDetailing.setAdapter(detailingAdapter);

        binding.rvItemsDetailing.setHasFixedSize(true);
        binding.rvItemsDetailing.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Detailing").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    detailingList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                        detailingList.add(ap);
                    }

                    detailingAdapter = new Detailing_Adapter(user_View_Carecare_Purifiers.this, detailingList, snapshot.getKey());
                    binding.rvItemsDetailing.setAdapter(detailingAdapter);

                    detailingAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ///////MICROFIBRES
        binding.rvItemsMicroFibres.setAdapter(microFibresAdapter);

        binding.rvItemsMicroFibres.setHasFixedSize(true);
        binding.rvItemsMicroFibres.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                microfibresList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    microfibresList.add(ap);
                }

                microFibresAdapter = new MicroFibres_Adapter(user_View_Carecare_Purifiers.this, microfibresList, snapshot.getKey());
                binding.rvItemsMicroFibres.setAdapter(microFibresAdapter);

                microFibresAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        //////WASHERS
        binding.rvItemsWashers.setAdapter(washersAdapter);

        binding.rvItemsWashers.setHasFixedSize(true);
        binding.rvItemsWashers.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("Washers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                washersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    washersList.add(ap);
                }

                washersAdapter = new Washers_Adapter(user_View_Carecare_Purifiers.this, washersList, snapshot.getKey());
                binding.rvItemsWashers.setAdapter(washersAdapter);

                washersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        //////WIPER BLADES
        binding.rvItemsWiperBlades.setAdapter(wiperBladesAdapter);

        binding.rvItemsWiperBlades.setHasFixedSize(true);
        binding.rvItemsWiperBlades.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("WiperBlades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wiperbladesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    wiperbladesList.add(ap);
                }

                wiperBladesAdapter = new WiperBlades_Adapter(user_View_Carecare_Purifiers.this, wiperbladesList, snapshot.getKey());
                binding.rvItemsWiperBlades.setAdapter(wiperBladesAdapter);

                wiperBladesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void details(String Model) {
        ccpModelStr = Model;
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


            databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child(Model).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Model.equalsIgnoreCase("AirPurifier")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(airpurifierAdapter);
                        airpurifierAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("CleaningKit")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(cleaningKitAdapter);
                        cleaningKitAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Cleansers")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(cleansersAdapter);
                        cleansersAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Detailing")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(detailingAdapter);
                        detailingAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("MicroFibres")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(microFibresAdapter);
                        microFibresAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("Washers")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(washersAdapter);
                        washersAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("WiperBlades")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(wiperBladesAdapter);
                        wiperBladesAdapter.notifyDataSetChanged();
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