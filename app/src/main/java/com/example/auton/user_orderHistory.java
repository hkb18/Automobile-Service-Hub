package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.auton.databinding.ActivityUserOrderHistoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_orderHistory extends AppCompatActivity {
    private ActivityUserOrderHistoryBinding binding;
    private OrderHistory_Adapter orderHistoryAdapter;
    ArrayList<OrderHistory_ModelClass> list=new ArrayList<>();
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        binding.rvOrderHistory.setAdapter(orderHistoryAdapter);

        binding.rvOrderHistory.setHasFixedSize(true);
        binding.rvOrderHistory.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("ORDER_HISTORY").child(s1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderHistory_ModelClass ap = dataSnapshot.getValue(OrderHistory_ModelClass.class);
                    list.add(ap);
                }

                orderHistoryAdapter =new OrderHistory_Adapter(user_orderHistory.this,list);
                binding.rvOrderHistory.setAdapter(orderHistoryAdapter);

                orderHistoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}