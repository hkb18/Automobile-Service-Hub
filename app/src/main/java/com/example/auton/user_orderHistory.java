package com.example.auton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.auton.databinding.ActivityUserOrderHistoryBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
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
    ArrayList<OrderHistory_ModelClass> list = new ArrayList<>();
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1;
    private ActivityUserOrderHistoryBinding binding;
    private OrderHistory_Adapter orderHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        s1 = sh.getString("Username", "");

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

                orderHistoryAdapter = new OrderHistory_Adapter(user_orderHistory.this, list);
                binding.rvOrderHistory.setAdapter(orderHistoryAdapter);

                orderHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnDate.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            long minDate = c.getTimeInMillis();


            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
            MaterialDatePicker<Pair<Long, Long>> pickerRange = builder.build();
            pickerRange.show(getSupportFragmentManager(), "");

            pickerRange.addOnPositiveButtonClickListener(selection -> {
                Long startDate = selection.first;
                Long endDate = selection.second;

                String startDateString = DateFormat.format("dd-MM-yyyy", new Date(startDate)).toString();
                String endDateString = DateFormat.format("dd-MM-yyyy", new Date(endDate)).toString();


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