package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
    private  ScreensSpeakersAdapter adapter;
    private ArrayList<materialButton> list;
    private ArrayList<ScreensSpeakers_ModelClass> ssList;
    private ScreenSpeaker_ItemAdapter ssAdapter;
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
        databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Log.e("", "onDataChange: "+dataSnapshot );
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

        binding.rvItems.setAdapter(ssAdapter);
        ssList=new ArrayList<>();

        binding.rvItems.setHasFixedSize(true);
        binding.rvItems.setLayoutManager(new LinearLayoutManager(this));

        ssAdapter =new ScreenSpeaker_ItemAdapter(this,ssList);
        binding.rvItems.setAdapter(ssAdapter);

        //ScreeenSpeakerInterface=this;

        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ssList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ScreensSpeakers_ModelClass ss = dataSnapshot1.getValue(ScreensSpeakers_ModelClass.class);
                    ssList.add(ss);
                }}
                ssAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
