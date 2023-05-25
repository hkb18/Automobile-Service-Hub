package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class user_Login extends AppCompatActivity {
Button login;
TextInputEditText username,password;
String usernameStr,passwordStr;
DatabaseReference databaseReference;
TextView notRegistered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        login=(Button) findViewById(R.id.button_UserLogin);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        notRegistered=(TextView) findViewById(R.id.registration);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameStr=username.getText().toString();
                passwordStr=password.getText().toString();
                if(passwordStr.isEmpty() || usernameStr.isEmpty()){
                    Toast.makeText(user_Login.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                }   else {
                    databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernameStr)){
                                final String getPassword=snapshot.child(usernameStr).child("Password").getValue(String.class);
                                final String getUsername=snapshot.child(usernameStr).child("Username").getValue(String.class);
                                //  ADMIN LOGIN
                                if(getUsername.equals("Admin")){
                                    if (getPassword.equals(passwordStr)){
                                        Intent i=new Intent(getApplicationContext(),admin_HomePage.class);
                                        startActivity(i);
                                        Toast.makeText(user_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(user_Login.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //  USER LOGIN
                                else if (getPassword.equals(passwordStr)){
                                    Intent i=new Intent(getApplicationContext(),user_HomePage.class);
                                    i.putExtra("username", usernameStr);// username passing
                                    startActivity(i);
                                    Toast.makeText(user_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(user_Login.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(user_Login.this, "Wrong credentials ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(user_Login.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), user_Registration.class);
                startActivity(i);
            }
        });
    }
    public void onResume(){
        super.onResume();
        SharedPreferences sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE);  // saves username
        String s1=sh.getString("Username","");
       // String s2=sh.getString("Password","");
       // username.setText(s1); // if u wanna save it like cache..uncomment
       // password.setText(s2);

    }
    public void onPause(){
        super.onPause();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor myEdit=sharedPreferences.edit();

        myEdit.putString("Username",username.getText().toString());
        //myEdit.putString("Password",password.getText().toString());
        myEdit.apply();
    }
}