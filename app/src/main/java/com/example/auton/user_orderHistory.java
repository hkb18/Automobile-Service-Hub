package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.auton.databinding.ActivityUserOrderHistoryBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class user_orderHistory extends AppCompatActivity {
    private ActivityUserOrderHistoryBinding binding;
    private OrderHistory_Adapter orderHistoryAdapter;
    ArrayList<OrderHistory_ModelClass> list=new ArrayList<>();
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        sh=getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        binding.rvOrderHistory.setAdapter(orderHistoryAdapter);

        binding.rvOrderHistory.setHasFixedSize(true);
        binding.rvOrderHistory.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("ORDER_HISTORY").child(s1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderHistory_ModelClass ap = dataSnapshot.getValue(OrderHistory_ModelClass.class);
                    list.add(ap);
                }

                orderHistoryAdapter =new OrderHistory_Adapter(user_orderHistory.this,list);
                binding.rvOrderHistory.setAdapter(orderHistoryAdapter);

                orderHistoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
binding.btnDate.setOnClickListener(view -> {
    final Calendar c=Calendar.getInstance();
   int mYear=c.get(Calendar.YEAR);
 int   mMonth=c.get(Calendar.MONTH);
   int mDay=c.get(Calendar.DAY_OF_MONTH);
    long minDate = c.getTimeInMillis();


  /*  DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthofYear, int dayofMonth) {
            String tempDate=dayofMonth +"-"+(monthofYear+1)+"-"+year;
            Date tempDate2=new Date(tempDate);
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            dateFormat.format(tempDate2);
            Log.e("TAG", "onDateSet: "+tempDate2 );
          //  mTimeText.setText("Time: " + dateFormat.format(date));
            ArrayList<OrderHistory_ModelClass> filteredlist = new ArrayList<>();

            // running a for loop to compare elements.
            for (OrderHistory_ModelClass item : list) {
                // checking if the entered string matched with any item of our recycler view.
                if ( item.getDate().toLowerCase().contains(tempDate.toLowerCase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item);
                }
            }
            if (filteredlist.isEmpty()) {
                // if no item is added in filtered list we are
                // displaying a toast message as no data found.
                Toast.makeText(getApplicationContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
            } else {
                // at last we are passing that filtered
                // list to our adapter class.
                orderHistoryAdapter.filterList(filteredlist);
            }
        }
    },mYear,mMonth,mDay);
    datePickerDialog.getDatePicker().setMinDate(minDate);
    datePickerDialog.show();*/
    MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
    MaterialDatePicker<Pair<Long, Long>> pickerRange = builder.build();
    pickerRange.show(getSupportFragmentManager(),"");

    pickerRange.addOnPositiveButtonClickListener(selection -> {
        Long startDate = selection.first;
        Long endDate = selection.second;

        String startDateString = DateFormat.format("dd-MM-yyyy", new Date(startDate)).toString();
        String endDateString = DateFormat.format("dd-MM-yyyy", new Date(endDate)).toString();
       // String date = "Start: " + startDateString + " End: " + endDateString;
       // Log.e("TAG", "onCreate: "  +date );

        ArrayList<OrderHistory_ModelClass> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (OrderHistory_ModelClass item : list) {
            // checking if the entered string matched with any item of our recycler view.
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = format.parse(item.getDate());
                Date date2 = format.parse(startDateString);
                Date date3 = format.parse(endDateString);
                if (date.before(date3)) {
                    filteredlist.add(item);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getApplicationContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            orderHistoryAdapter.filterList(filteredlist);
        }
    });


//    picker.addOnPositiveButtonClickListener(selection -> {
//        Log.e("TAG", "onCreate: "+ selection.toString().split(" ")[0]);
//        Log.e("TAG", "onCreate: "+ selection.toString().split(" ")[1]);
//        Log.e("TAG", "onCreate: "+ picker.getHeaderText());
//        String dt1 = selection.toString().split(" ")[0];
//        String dt2 = selection.toString().split(" ")[1];
//       String q1 =  dt1.replace("Pair{", "");
//      String q2=  dt2.replace("}", "");
//      String w1 = getDate(Long.parseLong(q1));
//      String w2 = getDate(Long.parseLong(q2));
//        Log.e("TAG", "onCreate: " + w1 + " ****" + w2 );
//
//    });

});
        //  SEARCH
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // creating a new array list to filter our data.
                ArrayList<OrderHistory_ModelClass> filteredlist = new ArrayList<>();

                // running a for loop to compare elements.
                for (OrderHistory_ModelClass item : list) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.getModel().toLowerCase().contains(newText.toLowerCase()) || item.getManufacturer().toLowerCase().contains(newText.toLowerCase())) {
                        // if the item is matched we are
                        // adding it to our filtered list.
                        filteredlist.add(item);
                    }
                }
                if (filteredlist.isEmpty()) {
                    // if no item is added in filtered list we are
                    // displaying a toast message as no data found.
                    Toast.makeText(getApplicationContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
                } else {
                    // at last we are passing that filtered
                    // list to our adapter class.
                    orderHistoryAdapter.filterList(filteredlist);
                }
                return false;
            }
        });

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }
}