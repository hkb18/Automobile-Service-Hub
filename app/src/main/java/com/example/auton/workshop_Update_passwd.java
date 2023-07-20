package com.example.auton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class workshop_Update_passwd extends AppCompatActivity {
    Button buttonPasswdUpdate;
    TextInputEditText changedPassword, confirmNewPassword, password;
    String passwordStr, newPasswordStr, originalPasswordStr;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_update_passwd);
        changedPassword = (TextInputEditText) findViewById(R.id.confirmPassword);
        buttonPasswdUpdate = findViewById(R.id.btn_UpdatePasswd);
        confirmNewPassword = (TextInputEditText) findViewById(R.id.confirmNewPassword);
        password = (TextInputEditText) findViewById(R.id.password);

        buttonPasswdUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), workshop_Update_passwd.class);
                Bundle extras = getIntent().getExtras();
                String username = extras.getString("Username");

                databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

                passwordStr = changedPassword.getText().toString();
                newPasswordStr = confirmNewPassword.getText().toString();
                originalPasswordStr = password.getText().toString();

                if (passwordStr.equals(newPasswordStr)) {
                    databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                passwordStr = snapshot.child(username).child("Password").getValue(String.class);
                                if (originalPasswordStr.equals(passwordStr)) {
                                    changedPassword.setText(passwordStr);
                                    Toast.makeText(workshop_Update_passwd.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(workshop_Update_passwd.this, "Current Password Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    startActivity(i);
                } else {
                    Toast.makeText(workshop_Update_passwd.this, "Password not same", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}