package com.example.auton;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.auton.databinding.FragmentWorkshopDashboardBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link workshop_Dashboard_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class workshop_Dashboard_Fragment extends Fragment implements ViewBookedService_Interface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ViewBookedService_Interface viewBookedService_interface;
    LottieAnimationView lottieAnimationView;
    DatabaseReference databaseReference;
    Workshop_BookedService_Adapter myAdapter;
    ArrayList<Worshop_View_Service_modelClass> list;
    String s1 = "";
    boolean yes = true, no = false;
    String selName = "";
    private FragmentWorkshopDashboardBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public workshop_Dashboard_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment workshop_Dashboard_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static workshop_Dashboard_Fragment newInstance(String param1, String param2) {
        workshop_Dashboard_Fragment fragment = new workshop_Dashboard_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWorkshopDashboardBinding.inflate(getLayoutInflater());

        viewBookedService_interface = this;

        SharedPreferences sh = requireContext().getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        s1 = sh.getString("Username", "");
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        list = new ArrayList<>();

        binding.rvBookedService.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new Workshop_BookedService_Adapter(getContext(), list);
        binding.rvBookedService.setAdapter(myAdapter);
        databaseReference.child("Service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Worshop_View_Service_modelClass bookedService = dataSnapshot1.getValue(Worshop_View_Service_modelClass.class);
                        String dtStart = bookedService.getDate();


                        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");
                        try {
                            Date date = format.parse(dtStart);

                            String date1 = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());
                            Date d = format.parse(date1);

                            if (!date.before(d)) {
                                list.add(bookedService);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading data" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
       /* lottieAnimationView=v.findViewById(R.id.lottie);
        lottieAnimationView.animate().translationX(2000).setStartDelay(2900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },5000);
        */
        return binding.getRoot();
    }

    @Override
    public void accept(String username, String key, int position, String userLat, String userLong) {
        try {
            databaseReference.child("Service").child(username).child(key).child("ACCEPT_SERVICE").setValue(1);
            databaseReference.child("Service").child(username).child(key).child("ServiceStatus").setValue("Accepted");
            myAdapter.notifyDataSetChanged();

            ArrayList<Mechanic> mechanicList = new ArrayList<>();
            databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mechanicList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Mechanic mechanic = dataSnapshot.getValue(Mechanic.class);
                        mechanicList.add(mechanic);
                    }
                    showpopup(mechanicList, username, key, userLat, userLong);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(requireContext(), "NO MECHANIC FOUND..Add Mechanics", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void delete(String username, String key, int position) {
        databaseReference.child("Service").child(username).child(key).child("ACCEPT_SERVICE").setValue(2);
        databaseReference.child("Service").child(username).child(key).child("ServiceStatus").setValue("Rejected");
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void view(String key, int position) {
    }

    private void showpopup(ArrayList<Mechanic> mechanicList, String username, String key, String userLat, String userLong) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("PLEASE SELECT A MECHANIC");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        dialog.show();

        MaterialButton assign = customLayout.findViewById(R.id.btn_assignMechanic);
        Spinner mechanic = customLayout.findViewById(R.id.spinnerMechanic);

        ArrayList<String> name = new ArrayList<>();
        for (int i = 0; i < mechanicList.size(); i++) {
            name.add(mechanicList.get(i).getName());
        }
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, name);
        mechanic.setAdapter(brandAdapter);

        mechanic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selName = name.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //  ASSIGNS MECHANIC AND WORKSHOP FOR SERVICE REQ
        assign.setOnClickListener(v -> {
            databaseReference.child("Service").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference.child("Service").child(username).child(key).child("Workshop").setValue(s1);
                    databaseReference.child("Service").child(username).child(key).child("AssignedMechanic").setValue(selName);
                    startMap(userLat, userLong, username);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            builder.setCancelable(true);
            dialog.dismiss();
        });
    }

    private void startMap(String userLat, String userLong, String username) {
        databaseReference.child("Workshop_Profile").child(s1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String tempLat = snapshot.child("latitude").getValue(String.class);
                final String tempLong = snapshot.child("longitude").getValue(String.class);
                final String tempName = snapshot.child("Name").getValue(String.class);

                Intent intent = new Intent(requireContext(), MapsActivity_Workshop.class);
                intent.putExtra("longitude", tempLong);
                intent.putExtra("latitude", tempLat);
                intent.putExtra("userLongitude", userLong);
                intent.putExtra("userLatitude", userLat);
                intent.putExtra("name", tempName);
                intent.putExtra("userName", username);
                intent.putExtra("activity", "workshopMap");
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}