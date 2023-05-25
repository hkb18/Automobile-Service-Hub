package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class worker_Registration extends AppCompatActivity {
    String workshopnameStr,emailidStr,contactnoStr,usernameStr,passwordStr,confirmpasswordStr;

    TextInputEditText textInputEditText1,textInputEditText2,textInputEditText3,textInputEditText4,textInputEditText5,textInputEditText6;
    Button signUp,signIn;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_registration);

        signUp=(Button) findViewById(R.id.button_WorkshopSignUp);
        signIn=(Button) findViewById(R.id.button_WorkshopLogin);
        textInputEditText1=(TextInputEditText) findViewById(R.id.companyName);
        textInputEditText2=(TextInputEditText)findViewById(R.id.emailId);
        textInputEditText3=(TextInputEditText)findViewById(R.id.contactNumber);
        textInputEditText4=(TextInputEditText)findViewById(R.id.username);
        textInputEditText5=(TextInputEditText)findViewById(R.id.password);
        textInputEditText6=(TextInputEditText)findViewById(R.id.confirmPassword);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(), workshop_Login.class);
                startActivity(i);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workshopnameStr=textInputEditText1.getText().toString();
                emailidStr=textInputEditText2.getText().toString();
                contactnoStr=textInputEditText3.getText().toString();
                usernameStr=textInputEditText4.getText().toString();
                passwordStr=textInputEditText5.getText().toString();
                confirmpasswordStr=textInputEditText6.getText().toString();

                if(TextUtils.isEmpty(workshopnameStr)|| emailidStr.isEmpty() || contactnoStr.isEmpty() || usernameStr.isEmpty() || passwordStr.isEmpty() || confirmpasswordStr.isEmpty() ) {
                    Toast.makeText(worker_Registration.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else if (passwordStr.equals(confirmpasswordStr)) {
                    databaseReference.child("Workshop_Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernameStr)){
                                Toast.makeText(worker_Registration.this,"Already existing User",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),workshop_Login.class);
                                startActivity(i);
                            } else {
                                databaseReference.child("Workshop_Profile").child(usernameStr).child("Username").setValue(usernameStr);
                                databaseReference.child("Workshop_Profile").child(usernameStr).child("Name").setValue(workshopnameStr);
                                databaseReference.child("Workshop_Profile").child(usernameStr).child("ContactNo").setValue(contactnoStr);
                                databaseReference.child("Workshop_Profile").child(usernameStr).child("Email_Id").setValue(emailidStr);
                                databaseReference.child("Workshop_Profile").child(usernameStr).child("Password").setValue(passwordStr);
                                Toast.makeText(worker_Registration.this,"Workshop Successfully Registered",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),workshop_Login.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(worker_Registration.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(worker_Registration.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}