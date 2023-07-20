package com.example.auton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserViewBookedServiceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_View_Booked_Service extends AppCompatActivity implements ViewBookedService_Interface {
    public static ViewBookedService_Interface viewBookedService_interface;
    DatabaseReference databaseReference;
    BookedService_Adapter myAdapter;
    ArrayList<Worshop_View_Service_modelClass> list;
    SharedPreferences sh;
    String s1 = "";
    private ActivityUserViewBookedServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewBookedServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewBookedService_interface = this;

        SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        s1 = sh.getString("Username", "");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        list = new ArrayList<>();

        binding.rvBookedService.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new BookedService_Adapter(this, list);
        binding.rvBookedService.setAdapter(myAdapter);


        databaseReference.child("Service").child(s1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Worshop_View_Service_modelClass bookedService = dataSnapshot.getValue(Worshop_View_Service_modelClass.class);
                    list.add(bookedService);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //  SEARCH
    }

    @Override
    public void accept(String username, String key, int position, String userLat, String userLong) {

    }

    @Override
    public void delete(String username, String key, int position) {

    }

    @Override
    public void view(String key, int position) {

    }
}