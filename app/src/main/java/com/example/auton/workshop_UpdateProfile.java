package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityWorkshopUpdateProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class workshop_UpdateProfile extends AppCompatActivity {
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String fullnameStr, emailStr, contactStr;
    private ActivityWorkshopUpdateProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkshopUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        sh = getSharedPreferences("MySharedPreferences1", MODE_PRIVATE); // to store data for temp time
        String s1 = sh.getString("Username", "");


        binding.updatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), user_Update_Password.class);
                i.putExtra("Username", s1);
                startActivity(i);
            }
        });

        //  TO UPDATE USER PROFILE
        databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(s1)) {
                    fullnameStr = snapshot.child(s1).child("Name").getValue(String.class);
                    emailStr = snapshot.child(s1).child("Email_Id").getValue(String.class);
                    contactStr = snapshot.child(s1).child("ContactNo").getValue(String.class);

                    binding.newName.setText(fullnameStr);
                    binding.newEmail.setText(emailStr);
                    binding.newContact.setText(contactStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //  UPDATE BUTTON
        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(s1)) {
                            fullnameStr = binding.newName.getText().toString();
                            emailStr = binding.newEmail.getText().toString();
                            contactStr = binding.newContact.getText().toString();

                            databaseReference.child("Workshop_Profile").child(s1).child("Name").setValue(fullnameStr);
                            databaseReference.child("Workshop_Profile").child(s1).child("Email_Id").setValue(emailStr);
                            databaseReference.child("Workshop_Profile").child(s1).child("ContactNo").setValue(contactStr);
                            Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}