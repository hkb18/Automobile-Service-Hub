package com.example.auton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_HomePage extends AppCompatActivity {
    String username,gotocart,s1, deleteCart;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        //GETTING username
        Bundle extras=getIntent().getExtras();
        username= extras.getString("Username");
        gotocart=extras.getString("iscart","0");
        deleteCart=extras.getString("deleteCart","0");

        SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        s1 = sh.getString("Username", "");

        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        if (gotocart.equals("1")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new user_Cart_fragment().newInstance("",username)).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new user_Dashboard_Fragment().newInstance("",username)).commit();
        }

        if(deleteCart.equals("1")){
            databaseReference.child("CART").child(s1).removeValue();
        }
    }
      private final  BottomNavigationView.OnNavigationItemSelectedListener navListener= item -> {
            Fragment selectedFragment = null;
            int itemId=item.getItemId();
            if(itemId==R.id.profile){
                selectedFragment=new user_MyProfile_Fragment();
            } else if (itemId==R.id.home) {
                selectedFragment = new user_Dashboard_Fragment().newInstance("",username);
            } else if (itemId==R.id.accessory) {
                selectedFragment=new user_Accessories_Fragment().newInstance("",username);
            } else if ((itemId==R.id.cart)) {
                selectedFragment = new user_Cart_fragment().newInstance("",username);
            }
          if(selectedFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            }
            return true;
        };

}