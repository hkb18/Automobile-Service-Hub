package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String usernameStr, passwordStr;
    SharedPreferences loginPref;
    SharedPreferences.Editor loginPrefEditor;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginPref = getSharedPreferences("login", MODE_PRIVATE);
        loginPrefEditor = loginPref.edit();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.btnLogin.setOnClickListener(view -> {
            usernameStr = binding.username.getText().toString();
            passwordStr = binding.password.getText().toString();
            if (usernameStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "Please Enter all details", Toast.LENGTH_SHORT).show();
            } else {
                if (binding.segmentedbutton.getPosition() == 0) {  //ADMIN & USER LOGIN
                    databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernameStr)) {
                                final String getPassword = snapshot.child(usernameStr).child("Password").getValue(String.class);
                                final String getUsername = snapshot.child(usernameStr).child("Username").getValue(String.class);
                                //  ADMIN LOGIN
                                if (getUsername.equals("Admin")) {
                                    if (getPassword.equals(passwordStr)) {
                                        SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putString("Username", usernameStr);
                                        loginPrefEditor.putBoolean("isLogin", true);
                                        loginPrefEditor.putInt("type", 0);
                                        editor.apply();
                                        loginPrefEditor.apply();
                                        Log.e("TAG", "onDataChange: " + usernameStr);
                                        Intent i = new Intent(getApplicationContext(), admin_HomePage.class);
                                        startActivity(i);
                                        Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Wrong credentials ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //  USER LOGIN
                                else if (getPassword.equals(passwordStr)) {
                                    SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sh.edit();
                                    editor.putString("Username", usernameStr);
                                    loginPrefEditor.putBoolean("isLogin", true);
                                    loginPrefEditor.putInt("type", 1);
                                    editor.apply();
                                    loginPrefEditor.apply();
                                    Log.e("TAG", "onDataChange: " + usernameStr);
                                    Intent i = new Intent(getApplicationContext(), user_HomePage.class);
                                    i.putExtra("Username", usernameStr);// username passing
                                    startActivity(i);
                                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Wrong credentials ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong credentials ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {     //WORKSHOP LOGIN
                    databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernameStr)) {
                                final String getPassword = snapshot.child(usernameStr).child("Password").getValue(String.class);
                                if (getPassword.equals(passwordStr)) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putString("Username", usernameStr);
                                    loginPrefEditor.putBoolean("isLogin", true);
                                    loginPrefEditor.putInt("type", 3);
                                    myEdit.apply();
                                    loginPrefEditor.apply();
                                    Intent i = new Intent(getApplicationContext(), workshop_HomePage.class);
                                    startActivity(i);
                                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong Credentials ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        binding.signin.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Registration.class);
            startActivity(i);
        });

        binding.workshopRegistration.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), worker_Registration.class);
            startActivity(i);
        });
    }

    public void onPause() {
        super.onPause();
        //USER & ADMIN
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("Username", binding.username.getText().toString());
        myEdit.apply();

        //WORKSHOP
        SharedPreferences sharedPreferences1 = getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        SharedPreferences.Editor myEdit1 = sharedPreferences.edit();
        myEdit1.putString("Username", binding.username.getText().toString());
        myEdit1.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();


     /*   if (loginPref.getBoolean("isLogin", false)){
            if(loginPref.getInt("type", 0) == 0){
                Intent i=new Intent(getApplicationContext(),admin_HomePage.class);
                startActivity(i);
            } else if (loginPref.getInt("type", 0) == 1){
                Intent i=new Intent(getApplicationContext(),user_HomePage.class);
                i.putExtra("Username", usernameStr);// username passing
                startActivity(i);

            } else {
                Intent i=new Intent(getApplicationContext(),workshop_HomePage.class);
                startActivity(i);

            }
        }*/
    }
}