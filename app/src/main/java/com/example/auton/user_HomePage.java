package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class user_HomePage extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        //GETTING username
        Bundle extras=getIntent().getExtras();
        username= extras.getString("Username");
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new user_Dashboard_Fragment().newInstance("",username)).commit();
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
            }
          if(selectedFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            }
            return true;
        };

}