package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RazorPay extends AppCompatActivity implements PaymentResultListener {
    TextView amt;
    Button payBtn;
    String priceStr="",keyStr="",pk="",usernameStr,brandStr,modelstr,servicenameStr,serviceStr,datestr,timestr,latitudeStr,longitudeStr,activity;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        sh=getApplicationContext().getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        String s1=sh.getString("Username","");

        activity= getIntent().getStringExtra("activity");
        priceStr= getIntent().getStringExtra("price");
        usernameStr= getIntent().getStringExtra("Username");
        brandStr= getIntent().getStringExtra("CarBrand");
        modelstr= getIntent().getStringExtra("CarModel");
        servicenameStr= getIntent().getStringExtra("ServiceType");
        serviceStr= getIntent().getStringExtra("ServiceName");
        datestr= getIntent().getStringExtra("Date");
        timestr= getIntent().getStringExtra("ServiceTime");
        latitudeStr= getIntent().getStringExtra("Latitude");
        longitudeStr= getIntent().getStringExtra("Longitude");
        keyStr= getIntent().getStringExtra("key");

        amt = findViewById(R.id.amt);
        payBtn = findViewById(R.id.idBtnPay);
        amt.setText(priceStr);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // amount that is entered by user.
                String samount = priceStr;

                // rounding off the amount.
                int amount = Math.round(Float.parseFloat(samount) * 100);

                // initialize Razorpay account.
                Checkout checkout = new Checkout();

                // set your id as below
                checkout.setKeyID("rzp_test_04poRZd72nQ9eN");

                // set image
                checkout.setImage(R.drawable.logo);

                // initialize json object
                JSONObject object = new JSONObject();
                Toast.makeText(RazorPay.this, ""+activity, Toast.LENGTH_SHORT).show();
                try {
                    // to put name
                    object.put("name", "AUTOMOBILE SERVICE HUB");

                    // put description
                    object.put("description", "Test payment");

                    // to set theme color
                    object.put("theme.color", "");

                    // put the currency
                    object.put("currency", "INR");
                    // put amount
                    object.put("amount", amount);

                    // put mobile number
                    object.put("prefill.contact", "9072233806");

                    // put email
                    object.put("prefill.email", "hitheshkbimal2000@gmail.com");

                    // open razorpay to checkout activity
                    checkout.open(RazorPay.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                if(activity.equals("cart")) {
                    Toast.makeText(RazorPay.this, "Bye", Toast.LENGTH_SHORT).show();
                    getData data = new getData();
                    data.setKey(keyStr);
                    data.setPrice(priceStr);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("PURCHASED_ACCESSORIES").child(s1).child(databaseReference.push().getKey()).setValue(data);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RazorPay.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else if (activity.equals("bookService")) {
                    Toast.makeText(RazorPay.this, "Hi", Toast.LENGTH_SHORT).show();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //passing to service table
                            pk=databaseReference.push().getKey();
                            databaseReference.child("Service").child(s1).child(pk).child("Username").setValue(s1);
                            databaseReference.child("Service").child(s1).child(pk).child("CarBrand").setValue(brandStr);
                            databaseReference.child("Service").child(s1).child(pk).child("CarModel").setValue(modelstr);
                            databaseReference.child("Service").child(s1).child(pk).child("ServiceType").setValue(servicenameStr);
                            databaseReference.child("Service").child(s1).child(pk).child("ServiceName").setValue(serviceStr);
                            databaseReference.child("Service").child(s1).child(pk).child("Date").setValue(datestr);
                            databaseReference.child("Service").child(s1).child(pk).child("ServiceTime").setValue(timestr);
                            databaseReference.child("Service").child(s1).child(pk).child("Latitude").setValue(latitudeStr);
                            databaseReference.child("Service").child(s1).child(pk).child("Longitude").setValue(longitudeStr);
                            databaseReference.child("Service").child(s1).child(pk).child("Price").setValue(priceStr);
                            databaseReference.child("Service").child(s1).child(pk).child("Key").setValue(pk);
                            Toast.makeText(getApplicationContext(), "Service Sussecfully Booked", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(RazorPay.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }


        });


    }
    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();


        Intent i=new Intent(getApplicationContext(),user_HomePage.class);
        i.putExtra("Username", s1);
        i.putExtra("deleteCart", "1");
        startActivity(i);
        finishAffinity();
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

  static   class getData{
        String key;
        String price;

      public getData() {
      }

      public getData(String key, String price) {
          this.key = key;
          this.price = price;
      }

      public String getKey() {
          return key;
      }

      public void setKey(String key) {
          this.key = key;
      }

      public String getPrice() {
          return price;
      }

      public void setPrice(String price) {
          this.price = price;
      }
  }
}