package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.auton.databinding.ActivityAdminViewUsersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_View_Users extends AppCompatActivity {
    private ActivityAdminViewUsersBinding binding;
    DatabaseReference databaseReference;
    UserAdapter adapter;
    Workshop_Adapter workshopAdapter;
    ArrayList<User> list=new ArrayList<>();
    ArrayList<Workshop_ModelClass> workshoplist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ///////////////USERS
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        binding.rvUsers.setHasFixedSize(true);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));

        adapter=new UserAdapter(this,list);
        binding.rvUsers.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    User user=dataSnapshot.getValue(User.class);
                    //String getPassword=snapshot.child("Admin").child("Email_Id").getValue(String.class);
                  //  Toast.makeText(admin_View_Users.this,""+ getPassword, Toast.LENGTH_SHORT).show();
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        ///////////////Workshop
        databaseReference= FirebaseDatabase.getInstance().getReference("Workshop_Profile");
        binding.rvWorkshops.setHasFixedSize(true);
        binding.rvWorkshops.setLayoutManager(new LinearLayoutManager(this));

        workshopAdapter=new Workshop_Adapter(this,workshoplist);
        binding.rvWorkshops.setAdapter(workshopAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Workshop_ModelClass workshop=dataSnapshot.getValue(Workshop_ModelClass.class);
                    //String getPassword=snapshot.child("Admin").child("Email_Id").getValue(String.class);
                    //  Toast.makeText(admin_View_Users.this,""+ getPassword, Toast.LENGTH_SHORT).show();
                    workshoplist.add(workshop);
                }
                workshopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}