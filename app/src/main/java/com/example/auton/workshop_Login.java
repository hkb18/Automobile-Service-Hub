package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class workshop_Login extends AppCompatActivity {
    Button login;
    TextInputEditText username,password;
    String workshopnameStr,passwordStr;
    DatabaseReference databaseReference;
    TextView notRegistered;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_login);

        login=(Button) findViewById(R.id.button_WorkshopLogin);
        username=findViewById(R.id.workshopName);
        password=findViewById(R.id.password);
        notRegistered=(TextView) findViewById(R.id.workshop_registration);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               workshopnameStr=username.getText().toString();
               passwordStr=password.getText().toString();
               if (passwordStr.isEmpty() || workshopnameStr.isEmpty()){
                   Toast.makeText(workshop_Login.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
               } else {
                   databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if (snapshot.hasChild(workshopnameStr)){
                               final String getPassword=snapshot.child(workshopnameStr).child("Password").getValue(String.class);
                               if (getPassword.equals(passwordStr)){
                                   Intent i=new Intent(getApplicationContext(),workshop_HomePage.class);
                                   startActivity(i);
                                   Toast.makeText(workshop_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                               }else {
                                   Toast.makeText(workshop_Login.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                               }
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {
                           Toast.makeText(workshop_Login.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           }
       });
       notRegistered.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(getApplicationContext(), worker_Registration.class);
               startActivity(i);
           }
       });
    }
    public void onResume(){
        super.onResume();
        SharedPreferences sh=getSharedPreferences("MySharedPreferences1",MODE_PRIVATE);
        String s1=sh.getString("Username","");
       // String s2=sh.getString("WorkshopPassword","");
        username.setText(s1);
       // password.setText(s2);

    }
    public void onPause(){
        super.onPause();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPreferences1",MODE_PRIVATE);
        SharedPreferences.Editor myEdit=sharedPreferences.edit();

        myEdit.putString("Username",username.getText().toString());
       // myEdit.putString("WorkshopPassword",password.getText().toString());
        myEdit.apply();
    }
}