package com.example.auton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewRoadsideAssistanceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_RoadsideAssistance extends AppCompatActivity implements HornsProtectives_Interface {
    static HornsProtectives_Interface hornsProtectivesInterface;
    DatabaseReference databaseReference;
    private ActivityUserViewRoadsideAssistanceBinding binding;
    private final ArrayList<materialButton> list = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> accessoriesList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> toolkitList = new ArrayList<>();
    private final ArrayList<Accessories_ModelClass> tyreinflatorList = new ArrayList<>();
    private Toolkit_Adapter toolkitAdapter;
    private RoadsideAssistance_Adapter roadsideAssistanceAdapter;
    private TyreInflator_Adapter tyreInflatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewRoadsideAssistanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hornsProtectivesInterface = this;//interface

        binding.rvRoadsideAssistance.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        roadsideAssistanceAdapter = new RoadsideAssistance_Adapter(this, list);
        binding.rvRoadsideAssistance.setAdapter(roadsideAssistanceAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").addListenerForSingleValueEvent(new ValueEventListener() {
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
                roadsideAssistanceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(user_view_RoadsideAssistance.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /////////// TOOLKITS
        binding.rvToolkit.setAdapter(toolkitAdapter);

        binding.rvToolkit.setHasFixedSize(true);
        binding.rvToolkit.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                toolkitList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    toolkitList.add(ap);
                }

                toolkitAdapter = new Toolkit_Adapter(user_view_RoadsideAssistance.this, toolkitList, snapshot.getKey());
                binding.rvToolkit.setAdapter(toolkitAdapter);

                toolkitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        /////////// TYRE INFLATOR
        binding.rvTyreInflator.setAdapter(tyreInflatorAdapter);

        binding.rvTyreInflator.setHasFixedSize(true);
        binding.rvTyreInflator.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("TyreInflator").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tyreinflatorList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Accessories_ModelClass ap = dataSnapshot.getValue(Accessories_ModelClass.class);
                    tyreinflatorList.add(ap);
                }

                tyreInflatorAdapter = new TyreInflator_Adapter(user_view_RoadsideAssistance.this, tyreinflatorList, snapshot.getKey());
                binding.rvTyreInflator.setAdapter(tyreInflatorAdapter);

                tyreInflatorAdapter.notifyDataSetChanged();
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
            databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child(Model).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Model.equalsIgnoreCase("Toolkits")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(toolkitAdapter);
                        toolkitAdapter.notifyDataSetChanged();
                    } else if (Model.equalsIgnoreCase("TyreInflator")) {
                        accessoriesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Accessories_ModelClass ss = dataSnapshot.getValue(Accessories_ModelClass.class);
                            accessoriesList.add(ss);
                        }
                        binding.rv.setAdapter(tyreInflatorAdapter);
                        tyreInflatorAdapter.notifyDataSetChanged();
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