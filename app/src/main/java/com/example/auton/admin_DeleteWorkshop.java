package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.auton.databinding.ActivityAdminDeleteWorkshopBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_DeleteWorkshop extends AppCompatActivity implements OnClickInterface{
    private ActivityAdminDeleteWorkshopBinding binding;
    DatabaseReference databaseReference;
    Workshop_Adapter2 myAdapter;
    ArrayList<Workshop_ModelClass> list;
    public static OnClickInterface onClickInterface;
    String s1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminDeleteWorkshopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sh= getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        s1=sh.getString("Username","");
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        myAdapter =new Workshop_Adapter2(this,list);
        binding.rv.setAdapter(myAdapter);

        onClickInterface=this;

        databaseReference.child("Workshop_Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Workshop_ModelClass mechanic = dataSnapshot.getValue(Workshop_ModelClass.class);
                    list.add(mechanic);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void delmech(String delName, int position) {
        databaseReference.child("Workshop_Profile").child(delName).removeValue();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void add(String quantity, int position) {

    }

    @Override
    public void remove(String quantity, int position) {

    }
}