package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_Update_Password extends AppCompatActivity {
Button buttonPasswdUpdate;

TextInputEditText changedPassword,confirmNewPassword,password;
String passwordStr,newPasswordStr,originalPasswordStr;
DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_password);

        changedPassword=(TextInputEditText) findViewById(R.id.confirmPassword);
        buttonPasswdUpdate=findViewById(R.id.btn_UpdatePasswd);
        confirmNewPassword=(TextInputEditText) findViewById(R.id.confirmNewPassword);
        password=(TextInputEditText)findViewById(R.id.password);

        buttonPasswdUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), user_Update_Password.class);
                Bundle extras = getIntent().getExtras();
                String username = extras.getString("Username");

                databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

                passwordStr = changedPassword.getText().toString();
                newPasswordStr = confirmNewPassword.getText().toString();
                originalPasswordStr=password.getText().toString();

                // SharedPreferences sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE);
                // String s1=sh.getString("Username","");

                if (passwordStr.equals(newPasswordStr)) {
                    databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                passwordStr = snapshot.child(username).child("Password").getValue(String.class);
                                if (originalPasswordStr.equals(passwordStr)){
                                    changedPassword.setText(passwordStr);
                                    Toast.makeText(user_Update_Password.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(user_Update_Password.this, "Current Password Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    startActivity(i);
                }
                else {
                    Toast.makeText(user_Update_Password.this, "Password not same", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}