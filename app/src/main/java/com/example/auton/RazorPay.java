package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RazorPay extends AppCompatActivity implements PaymentResultListener {
    TextView amt;
    ArrayList<cart_ModelClass> list = new ArrayList<>();
    Button payBtn;
    String priceStr = "0", keyStr = "", pk = "", usernameStr, brandStr, modelstr, servicenameStr, serviceStr, datestr, timestr, latitudeStr, longitudeStr, activity, imgStr, contactStr;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1 = "";
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        sh = getApplicationContext().getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        s1 = sh.getString("Username", "");

        activity = getIntent().getStringExtra("activity");
        priceStr = getIntent().getStringExtra("totalPrice");
        usernameStr = getIntent().getStringExtra("Username");
        brandStr = getIntent().getStringExtra("CarBrand");
        modelstr = getIntent().getStringExtra("CarModel");
        servicenameStr = getIntent().getStringExtra("ServiceType");
        serviceStr = getIntent().getStringExtra("ServiceName");
        datestr = getIntent().getStringExtra("Date");
        timestr = getIntent().getStringExtra("ServiceTime");
        latitudeStr = getIntent().getStringExtra("Latitude");
        longitudeStr = getIntent().getStringExtra("Longitude");
        keyStr = getIntent().getStringExtra("key");
        imgStr = getIntent().getStringExtra("Img");


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

            }


        });


    }

    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        Toast.makeText(this, "Payment is successful : ", Toast.LENGTH_SHORT).show();
        if (activity.equals("cart")) {

            databaseReference.child("CART").child(s1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getValue(cart_ModelClass.class));
                    }

                    list.forEach((it -> {
                        if (!it.getModel().isEmpty()) {
                            databaseReference.child("Accessories").child(it.getMainName()).child(it.getSubName()).child(it.getModel()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Accessories_ModelClass accModelclass = snapshot.getValue(Accessories_ModelClass.class);

                                    int productQty = Integer.parseInt(accModelclass.getQuantity());
                                    int pFinalQty = productQty - Integer.parseInt(it.getQuantity());
                                    if (pFinalQty <= 0) {
                                        Toast.makeText(getApplicationContext(), "Not enough products", Toast.LENGTH_SHORT).show();
                                        pFinalQty = 0;
                                    }
                                    accModelclass.setQuantity(String.valueOf(pFinalQty));
                                    databaseReference.child("Accessories").child(it.getMainName()).child(it.getSubName()).child(it.getModel()).setValue(accModelclass);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            databaseReference.child("Accessories").child(it.getMainName()).child(it.getSubName()).child(it.getProductKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Accessories_ModelClass accModelclass = snapshot.getValue(Accessories_ModelClass.class);

                                    int productQty = Integer.parseInt(accModelclass.getQuantity());
                                    int pFinalQty = productQty - Integer.parseInt(it.getQuantity());
                                    if (pFinalQty <= 0) {
                                        Toast.makeText(getApplicationContext(), "Not enough products", Toast.LENGTH_SHORT).show();
                                        pFinalQty = 0;
                                    }
                                    accModelclass.setQuantity(String.valueOf(pFinalQty));
                                    databaseReference.child("Accessories").child(it.getMainName()).child(it.getSubName()).child(it.getProductKey()).setValue(accModelclass);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            copyRecord(databaseReference.child("CART").child(s1), databaseReference.child("ORDER_HISTORY").child(s1));


        } else if (activity.equals("bookService")) {


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //passing to service table
                    pk = databaseReference.push().getKey();

                    databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(s1)) {
                                contactStr = snapshot.child(s1).child("ContactNo").getValue(String.class);

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
                                databaseReference.child("Service").child(s1).child(pk).child("ContactNo").setValue(contactStr);
                                databaseReference.child("Service").child(s1).child(pk).child("ACCEPT_SERVICE").setValue(3);
                                databaseReference.child("Service").child(s1).child(pk).child("ServiceStatus").setValue("Requested");
                                Intent i = new Intent(getApplicationContext(), user_HomePage.class);
                                i.putExtra("Username", s1);
                                i.putExtra("deleteCart", "0");
                                i.putExtra("iscart", "0");
                                startActivity(i);
                                finishAffinity();
                                Toast.makeText(getApplicationContext(), "Service Successfully Booked", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(RazorPay.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (activity.equals("bookService2")) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //passing to service table
                    pk = databaseReference.push().getKey();
                    databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(s1)) {
                                contactStr = snapshot.child(s1).child("ContactNo").getValue(String.class);
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
                                databaseReference.child("Service").child(s1).child(pk).child("Image").setValue(imgStr);
                                databaseReference.child("Service").child(s1).child(pk).child("Key").setValue(pk);
                                databaseReference.child("Service").child(s1).child(pk).child("ContactNo").setValue(contactStr);
                                databaseReference.child("Service").child(s1).child(pk).child("ACCEPT_SERVICE").setValue(3);
                                databaseReference.child("Service").child(s1).child(pk).child("ServiceStatus").setValue("Requested");
                                Intent i = new Intent(getApplicationContext(), user_HomePage.class);
                                i.putExtra("Username", s1);
                                i.putExtra("deleteCart", "0");
                                i.putExtra("iscart", "0");
                                startActivity(i);
                                finishAffinity();
                                Toast.makeText(getApplicationContext(), "Service Successfully Booked", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(RazorPay.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (activity.equals("buynow")) {
            OrderHistory_ModelClass oh = new OrderHistory_ModelClass();
            String mainName = getIntent().getStringExtra("mainName");
            String subName = getIntent().getStringExtra("subName");
            String model = getIntent().getStringExtra("model");
            String manufacturer = getIntent().getStringExtra("manufacturer");
            String image = getIntent().getStringExtra("image");
            String quantity = getIntent().getStringExtra("quantity");
            oh.setKey(keyStr);
            oh.setPrice(priceStr);
            oh.setModel(model);
            oh.setManufacturer(manufacturer);
            oh.setImage(image);
            oh.setTotalQty(quantity);
            oh.setQuantity("1");
            oh.setUsername(s1);
            oh.setMainName(mainName);
            oh.setSubName(subName);

            databaseReference.child("Accessories").child(mainName).child(subName).child(keyStr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Accessories_ModelClass accModelclass = snapshot.getValue(Accessories_ModelClass.class);

                    int productQty = Integer.parseInt(accModelclass.getQuantity());
                    productQty--;
                    accModelclass.setQuantity(String.valueOf(productQty));
                    databaseReference.child("Accessories").child(mainName).child(subName).child(keyStr).setValue(accModelclass);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RazorPay.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference.child("ORDER_HISTORY").child(s1).child(databaseReference.push().getKey()).setValue(oh);
                    Intent i = new Intent(getApplicationContext(), user_HomePage.class);
                    i.putExtra("Username", s1);
                    i.putExtra("deleteCart", "0");
                    i.putExtra("iscart", "0");
                    startActivity(i);
                    finishAffinity();
                    Toast.makeText(RazorPay.this, "Accessory Purchased Successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RazorPay.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        //finishAffinity();
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }


    private void copyRecord(Query fromPath, final DatabaseReference toPath) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isComplete()) {

                            Intent i = new Intent(getApplicationContext(), user_HomePage.class);
                            i.putExtra("Username", s1);
                            i.putExtra("deleteCart", "1");
                            i.putExtra("iscart", "0");
                            startActivity(i);
                            finishAffinity();
                            Toast.makeText(RazorPay.this, "Accessory Purchased Successfully", Toast.LENGTH_SHORT).show();

                        } else {

                            System.out.println("Failed");

                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        fromPath.addListenerForSingleValueEvent(valueEventListener);

    }
}