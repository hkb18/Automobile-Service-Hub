package com.example.auton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class workshop_View_Mechanic extends AppCompatActivity implements OnClickInterface {
    public static OnClickInterface onClickInterface;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MechanicAdapter myAdapter;
    ArrayList<Mechanic> list;
    SearchView searchView;
    String s1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_view_mechanic);

        SharedPreferences sh = getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        s1 = sh.getString("Username", "");
        recyclerView = findViewById(R.id.viewRecyclerView);
        searchView = findViewById(R.id.searchView);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MechanicAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        onClickInterface = this;

        databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Mechanic mechanic = dataSnapshot.getValue(Mechanic.class);
                    list.add(mechanic);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //  SEARCH
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // creating a new array list to filter our data.
                ArrayList<Mechanic> filteredlist = new ArrayList<>();

                // running a for loop to compare elements.
                for (Mechanic item : list) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
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
                    myAdapter.filterList(filteredlist);
                }
                return false;
            }
        });
    }

    @Override
    public void delmech(String delName, int position) {
        databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").child(delName).removeValue();
        databaseReference.child("Mechanic_Profile").child(delName).removeValue();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void add(String quantity, int position) {

    }

    @Override
    public void remove(String quantity, int position) {

    }
}