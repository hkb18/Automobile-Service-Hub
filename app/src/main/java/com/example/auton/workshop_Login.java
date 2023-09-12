package com.example.auton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityWorkshopLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class workshop_Login extends AppCompatActivity {
    private ActivityWorkshopLoginBinding binding;

    String workshopnameStr, passwordStr;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkshopLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.buttonWorkshopLogin.setOnClickListener(view -> {
            workshopnameStr = binding.workshopName.getText().toString();
            passwordStr = binding.password.getText().toString();
            if (passwordStr.isEmpty() || workshopnameStr.isEmpty()) {
                Toast.makeText(workshop_Login.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Mechanic_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(workshopnameStr)) {
                            final String getPassword = snapshot.child(workshopnameStr).child("Password").getValue(String.class);
                            if (getPassword.equals(passwordStr)) {
                                Intent i = new Intent(getApplicationContext(), mechanic_HomePage.class);
                                startActivity(i);
                                Toast.makeText(workshop_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(workshop_Login.this, "Wrong Credentials ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(workshop_Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        String s1 = sh.getString("Username", "");
        // String s2=sh.getString("WorkshopPassword","");
        binding.workshopName.setText(s1);
        // password.setText(s2);

    }

    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("Username", binding.workshopName.getText().toString());
        // myEdit.putString("WorkshopPassword",password.getText().toString());
        myEdit.apply();
    }
}