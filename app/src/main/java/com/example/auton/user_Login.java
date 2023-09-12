package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_Login extends AppCompatActivity {
    Button login;
    TextInputEditText username, password;
    String usernameStr, passwordStr;
    DatabaseReference databaseReference;
    TextView notRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        login = findViewById(R.id.button_UserLogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        notRegistered = findViewById(R.id.registration);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameStr = username.getText().toString();
                passwordStr = password.getText().toString();
                if (passwordStr.isEmpty() || usernameStr.isEmpty()) {
                    Toast.makeText(user_Login.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernameStr)) {
                                final String getPassword = snapshot.child(usernameStr).child("Password").getValue(String.class);
                                final String getUsername = snapshot.child(usernameStr).child("Username").getValue(String.class);
                                //  ADMIN LOGIN
                                if (getUsername.equals("Admin")) {
                                    if (getPassword.equals(passwordStr)) {
                                        Intent i = new Intent(getApplicationContext(), admin_HomePage.class);
                                        startActivity(i);
                                        Toast.makeText(user_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(user_Login.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //  USER LOGIN
                                else if (getPassword.equals(passwordStr)) {
                                    Intent i = new Intent(getApplicationContext(), user_HomePage.class);
                                    i.putExtra("Username", usernameStr);// username passing
                                    startActivity(i);
                                    Toast.makeText(user_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(user_Login.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(user_Login.this, "Wrong credentials ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(user_Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), user_Registration.class);
                startActivity(i);
            }
        });
    }

    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("Username", username.getText().toString());
        //myEdit.putString("Password",password.getText().toString());
        myEdit.apply();
    }


}