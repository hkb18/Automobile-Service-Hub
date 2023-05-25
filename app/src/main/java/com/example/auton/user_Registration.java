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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_Registration extends AppCompatActivity {
    String fullname,emailid,contactno,username,password,confirmpassword;
 // TextInputLayout textInputEditText1,textInputEditText2,textInputEditText3,textInputEditText4,textInputEditText5,textInputEditText6;

    TextInputEditText textInputEditText1,textInputEditText2,textInputEditText3,textInputEditText4,textInputEditText5,textInputEditText6;
    Button signUp,signIn;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        signUp=(Button) findViewById(R.id.button_UserSignUp);
        signIn=(Button) findViewById(R.id.button_UserLogin);
        textInputEditText1=(TextInputEditText) findViewById(R.id.fullName);
        textInputEditText2=(TextInputEditText)findViewById(R.id.emailId);
        textInputEditText3=(TextInputEditText)findViewById(R.id.contactNumber);
        textInputEditText4=(TextInputEditText)findViewById(R.id.username);
        textInputEditText5=(TextInputEditText)findViewById(R.id.password);
        textInputEditText6=(TextInputEditText)findViewById(R.id.confirmPassword);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),user_Login.class);
                startActivity(i);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname=textInputEditText1.getText().toString();
                emailid=textInputEditText2.getText().toString();
                contactno=textInputEditText3.getText().toString();
                username=textInputEditText4.getText().toString();
                password=textInputEditText5.getText().toString();
                confirmpassword=textInputEditText6.getText().toString();


                //if(!TextUtils.isEmpty(fullname)|| !emailid.isEmpty() || !contactno.isEmpty() || !username.isEmpty() || !password.isEmpty() || !confirmpassword.isEmpty() ) {
                if(TextUtils.isEmpty(fullname)|| emailid.isEmpty() || contactno.isEmpty() || username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() ) {

                    Toast.makeText(user_Registration.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(user_Registration.this,password,Toast.LENGTH_SHORT).show();
                    // Toast.makeText(user_Registration.this,confirmpassword,Toast.LENGTH_SHORT).show();
                }
                   else if(password.equals(confirmpassword)){
                        databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(username)){
                                    Toast.makeText(user_Registration.this,"Already existing User",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),user_Login.class);
                                    startActivity(i);
                                }
                                else {
                                    databaseReference.child("Profile").child(username).child("Username").setValue(username);
                                    databaseReference.child("Profile").child(username).child("Name").setValue(fullname);
                                    databaseReference.child("Profile").child(username).child("ContactNo").setValue(contactno);
                                    databaseReference.child("Profile").child(username).child("EmailId").setValue(emailid);
                                    databaseReference.child("Profile").child(username).child("Password").setValue(password);
                                    Toast.makeText(user_Registration.this,"User Successfully Registered",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),user_Login.class);
                                    startActivity(i);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(user_Registration.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(username)){
                                Toast.makeText(user_Registration.this,"Already existing User",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),user_Login.class);
                                startActivity(i);
                            }
                            else {
                                databaseReference.child("Users").child(username).child("Username").setValue(username);
                                databaseReference.child("Users").child(username).child("Name").setValue(fullname);
                                databaseReference.child("Users").child(username).child("ContactNo").setValue(contactno);
                                databaseReference.child("Users").child(username).child("EmailId").setValue(emailid);
                                databaseReference.child("Users").child(username).child("Password").setValue(password);
                                Toast.makeText(user_Registration.this,"User Successfully Registered",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),user_Login.class);
                                startActivity(i);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(user_Registration.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    } else {
                        Toast.makeText(user_Registration.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                    }


                //Intent
               /* Intent i=new Intent(getApplicationContext(),user_Login.class);
                i.putExtra("Value1",fullname);
                i.putExtra("Value2",emailid);
                i.putExtra("Value3",contactno);
                i.putExtra("Value4",username);
                i.putExtra("Value5",password);
                i.putExtra("Value6",confirmpassword);
                startActivity(i);*/
            }
        });
    }
}