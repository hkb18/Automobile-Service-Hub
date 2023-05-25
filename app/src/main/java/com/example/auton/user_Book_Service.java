package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class user_Book_Service extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button date,location,booking;
    String modelstr,typestr,datestr,timestr,modestr,locationstr;
    EditText edittextdate,edittextlocation;
    Spinner carbrand,carmodel,servicetype,servicetime,paymentmode;
    String[] brand={"Maruti Suzuki","Hyundai","Tata","Mahindra","Kia","Toyota","Honda","Renault","Volkswagen","Benz","BMW"};
    String[][] models={{"Alto","Wagon R","800","Baleno","Swift","Ciaz","Ignis","Dzire"},
            {"Santro","i 10","i 20","Venuw","Creta","Eon","Alcazar"},
            {"Nexon","Punch","Harrier","Safari","Altroz"},
            {"Thar","Scorpio","Bolero","XUV 700","XUV 300"},
            {"Seltos","Sonet","Carnival","Carens"},
            {"Crysta","Fortuner","Innova,","Glanza","Vellfire","Ertiga"},
            {"City","Amaze","Jazz","Civic"},
            {"Duster","Kwid","Triber","Kiger"},
            {"Skoda","Polo","Virtus","Taigun","Tiguan"},
            {"AMG GT 63 S E","Benz G-Class","Benz C-Class"},
            {"BMW XM"," BMW 2 Series","BMW M340i"," BMW X1"}};
    String brandStr,username;
    String[] type={"Normal Service","Flat Tyre","Flat Battery","Car Wash","Recovery","Engine Trouble"};
    String[] time={"Morning","Afternoon","Evening"};
    String[] payment={"UPI Payment","COD","Net Banking"};
    private int mYear,mMonth,mDay;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_service);

        edittextlocation=findViewById(R.id.edittextLocation);
        edittextdate=findViewById(R.id.edittextDate);
        carmodel=findViewById(R.id.carModel);
        carmodel.setOnItemSelectedListener(this);

        carbrand=findViewById(R.id.carBrand);
        carbrand.setOnItemSelectedListener(this);
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brand);
        carbrand.setAdapter(brandAdapter);
        carbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String[] modelss=models[position];
                ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelss);
                carmodel.setAdapter(modelAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        servicetype=findViewById(R.id.serviceType);
        servicetype.setOnItemSelectedListener(this);
        ArrayAdapter st=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,type);
        st.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicetype.setAdapter(st);

        servicetime=findViewById(R.id.serviceTime);
        servicetime.setOnItemSelectedListener(this);
        ArrayAdapter ac=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,time);
        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicetime.setAdapter(ac);

        date=findViewById(R.id.buttonDate);
        location=findViewById(R.id.buttonLocation);
        servicetime=findViewById(R.id.serviceTime);

        paymentmode=findViewById(R.id.paymentMode);
        paymentmode.setOnItemSelectedListener(this);
        ArrayAdapter pm=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,payment);
        pm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentmode.setAdapter(pm);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        Bundle extras=getIntent().getExtras();
        username= extras.getString("Username");
        Toast.makeText(getApplicationContext(), ""+username, Toast.LENGTH_SHORT).show();

        //  LOCATION
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent i=new Intent(getApplicationContext(),);
              //  startActivity(i);
            }
        });

        //  DATE
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view==date)
                {
                    final Calendar c=Calendar.getInstance();
                    mYear=c.get(Calendar.YEAR);
                    mMonth=c.get(Calendar.MONTH);
                    mDay=c.get(Calendar.DAY_OF_MONTH);
                    long minDate = c.getTimeInMillis();


                    DatePickerDialog datePickerDialog=new DatePickerDialog(user_Book_Service.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthofYear, int dayofMonth) {
                            edittextdate.setText(dayofMonth +"-"+(monthofYear+1)+"-"+year);
                            datestr=edittextdate.getText().toString();
                        }
                    },mYear,mMonth,mDay);
                    datePickerDialog.getDatePicker().setMinDate(minDate);
                    datePickerDialog.show();
                }
            }
        });
        booking=findViewById(R.id.btn_BookService);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler;
                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i=new Intent(getApplicationContext(),user_View_Service.class);

                        i.putExtra("Username",username);
                        startActivity(i);
                    }
                },3000);
                brandStr=carbrand.getSelectedItem().toString();
                modelstr=carmodel.getSelectedItem().toString();
                typestr=servicetype.getSelectedItem().toString();
                timestr=servicetime.getSelectedItem().toString();
                locationstr=edittextlocation.getText().toString();
                modestr=paymentmode.getSelectedItem().toString();
                if(datestr.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter date", Toast.LENGTH_SHORT).show();
                }
                databaseReference.child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //passing to Users
                        databaseReference.child("Users").child(username).child("Service").child("Username").setValue(username);
                        databaseReference.child("Users").child(username).child("Service").child("CarBrand").setValue(brandStr);
                        databaseReference.child("Users").child(username).child("Service").child("CarModel").setValue(modelstr);
                        databaseReference.child("Users").child(username).child("Service").child("ServiceType").setValue(typestr);
                        databaseReference.child("Users").child(username).child("Service").child("Date").setValue(datestr);
                        databaseReference.child("Users").child(username).child("Service").child("ServiceTime").setValue(timestr);
                        databaseReference.child("Users").child(username).child("Service").child("Location").setValue(locationstr);
                        databaseReference.child("Users").child(username).child("Service").child("PaymentMode").setValue(modestr);


                        //passing to service table
                        databaseReference.child("Service").child(username).child("Username").setValue(username);
                        databaseReference.child("Service").child(username).child("CarBrand").setValue(brandStr);
                        databaseReference.child("Service").child(username).child("CarModel").setValue(modelstr);
                        databaseReference.child("Service").child(username).child("ServiceType").setValue(typestr);
                        databaseReference.child("Service").child(username).child("Date").setValue(datestr);
                        databaseReference.child("Service").child(username).child("ServiceTime").setValue(timestr);
                        databaseReference.child("Service").child(username).child("Location").setValue(locationstr);
                        databaseReference.child("Service").child(username).child("PaymentMode").setValue(modestr);
                        Toast.makeText(getApplicationContext(), "Service Sussecfully Booked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(user_Book_Service.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        //Toast.makeText(getApplicationContext(),brand[position],Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}