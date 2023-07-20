package com.example.auton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserDeleteServiceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_Delete_Service extends AppCompatActivity implements OnClickInterface {
    public static OnClickInterface onClickInterface;
    DatabaseReference databaseReference;
    BookedService_Delete_Adapter myAdapter;
    ArrayList<Worshop_View_Service_modelClass> list;
    SharedPreferences sh;
    String s1 = "";
    private ActivityUserDeleteServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDeleteServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClickInterface = this;
        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        s1 = sh.getString("Username", "");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.rvBookedService.setHasFixedSize(true);
        binding.rvBookedService.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new BookedService_Delete_Adapter(this, list);
        binding.rvBookedService.setAdapter(myAdapter);


        databaseReference.child("Service").child(s1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Worshop_View_Service_modelClass service = dataSnapshot.getValue(Worshop_View_Service_modelClass.class);
                    list.add(service);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void delmech(String delName, int position) {
        databaseReference.child("Service").child(s1).child(delName).removeValue();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void add(String quantity, int position) {

    }

    @Override
    public void remove(String quantity, int position) {

    }
}