package com.example.auton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityViewFeedbackBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewFeedback extends AppCompatActivity {
    DatabaseReference databaseReference;
    Feedback_Adapter feedbackAdapter;
    ArrayList<Feedback_ModelClass> list = new ArrayList<>();
    private ActivityViewFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("FEEDBACK");
        binding.rvUsers.setHasFixedSize(true);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        feedbackAdapter = new Feedback_Adapter(this, list);
        binding.rvUsers.setAdapter(feedbackAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Feedback_ModelClass feedback = dataSnapshot.getValue(Feedback_ModelClass.class);
                    list.add(feedback);
                }
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}