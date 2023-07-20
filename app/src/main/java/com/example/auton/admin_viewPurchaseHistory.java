package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityAdminViewPurchaseHistoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_viewPurchaseHistory extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<OrderHistory_ModelClass> list = new ArrayList<>();
    private ActivityAdminViewPurchaseHistoryBinding binding;
    private admin_OrderHistory_Adapter orderHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewPurchaseHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        orderHistoryAdapter = new admin_OrderHistory_Adapter(this, list);
        binding.rv.setAdapter(orderHistoryAdapter);

        databaseReference.child("ORDER_HISTORY").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        OrderHistory_ModelClass oh = dataSnapshot1.getValue(OrderHistory_ModelClass.class);
                        list.add(oh);
                    }
                }
                orderHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(admin_viewPurchaseHistory.this, "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}