package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class workshop_AddMechanic extends AppCompatActivity {
    String mechNameStr, mechEmailStr, mechContactStr, PasswordStr;
    TextInputEditText name, email, contact, passwd;
    Button add;
    DatabaseReference databaseReference;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_add_mechanic);
        name = findViewById(R.id.mechanicName);
        email = findViewById(R.id.mechanicEmailId);
        contact = findViewById(R.id.mechanicContactNumber);
        passwd = findViewById(R.id.mechanicPassword);
        add = findViewById(R.id.btn_AddMechanic);

        SharedPreferences sh = getSharedPreferences("MySharedPreferences1", MODE_PRIVATE); // to store data for temp time
        String s1 = sh.getString("Username", "");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mechNameStr = name.getText().toString();
                mechEmailStr = email.getText().toString();
                mechContactStr = contact.getText().toString();
                PasswordStr = passwd.getText().toString();

                if (TextUtils.isEmpty(mechNameStr) || mechEmailStr.isEmpty() || mechContactStr.isEmpty() || PasswordStr.isEmpty()) {
                    Toast.makeText(workshop_AddMechanic.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else { //s1 is username of workshop
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(mechNameStr)) {
                                Toast.makeText(workshop_AddMechanic.this, "Already existing User", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), workshop_Login.class);
                                startActivity(i);
                            } else {
                                databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").child(mechContactStr).child("Name").setValue(mechNameStr);
                                databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").child(mechContactStr).child("Email").setValue(mechEmailStr);
                                databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").child(mechContactStr).child("ContactNo").setValue(mechContactStr);
                                databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").child(mechContactStr).child("Workshop").setValue(s1);
                                databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").child(mechContactStr).child("Password").setValue(PasswordStr);
                                databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").child(mechContactStr).child("Status").setValue(true);

                                databaseReference.child("Mechanic_Profile").child(mechContactStr).child("Name").setValue(mechNameStr);
                                databaseReference.child("Mechanic_Profile").child(mechContactStr).child("Email").setValue(mechEmailStr);
                                databaseReference.child("Mechanic_Profile").child(mechContactStr).child("ContactNo").setValue(mechContactStr);
                                databaseReference.child("Mechanic_Profile").child(mechContactStr).child("Workshop").setValue(s1);
                                databaseReference.child("Mechanic_Profile").child(mechContactStr).child("Password").setValue(PasswordStr);
                                databaseReference.child("Mechanic_Profile").child(mechContactStr).child("Status").setValue(true);

                                Toast.makeText(workshop_AddMechanic.this, "Mechanic Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), workshop_HomePage.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(workshop_AddMechanic.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}