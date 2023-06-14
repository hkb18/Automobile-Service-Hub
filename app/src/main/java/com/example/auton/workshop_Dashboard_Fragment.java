package com.example.auton;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.auton.databinding.ActivityUserViewBookedServiceBinding;
import com.example.auton.databinding.FragmentWorkshopDashboardBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link workshop_Dashboard_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class workshop_Dashboard_Fragment extends Fragment implements ViewBookedService_Interface{
    LottieAnimationView lottieAnimationView;
    public static ViewBookedService_Interface viewBookedService_interface;
    private FragmentWorkshopDashboardBinding binding;
    DatabaseReference databaseReference;
    Workshop_BookedService_Adapter myAdapter;
    ArrayList<Worshop_View_Service_modelClass> list;
    String s1="";
    boolean yes=true,no=false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
       // View v=inflater.inflate(R.layout.fragment_workshop__dashboard, container, false);

         binding=FragmentWorkshopDashboardBinding.inflate(getLayoutInflater());


        viewBookedService_interface=this;

        SharedPreferences sh= getContext().getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        s1=sh.getString("Username","");
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        list=new ArrayList<>();

        binding.rvBookedService.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter =new Workshop_BookedService_Adapter(getContext(),list);
        binding.rvBookedService.setAdapter(myAdapter);
        databaseReference.child("Service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Worshop_View_Service_modelClass bookedService = dataSnapshot1.getValue(Worshop_View_Service_modelClass.class);
                    list.add(bookedService);
                }}
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
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
    public void accept(String username, String key, int position) {
        databaseReference.child("Service").child(username).child(key).child("ACCEPT_SERVICE").setValue(true);
        myAdapter.notifyDataSetChanged();
        showpopup();
    }



    @Override
    public void delete(String username, String key, int position) {
        databaseReference.child("Service").child(username).child(key).child("ACCEPT_SERVICE").setValue(false);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void view(String key, int position) {

    }
    private void showpopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("PLEASE SELECT A MECHANIC");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);


        ArrayList<Mechanic> mechanicList=new ArrayList<>();
        databaseReference.child("Workshop_Profile").child(s1).child("Mechanic_Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mechanicList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Mechanic mechanic=dataSnapshot.getValue(Mechanic.class);
                    mechanicList.add(mechanic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        MaterialButton assign=customLayout.findViewById(R.id.btn_assignMechanic);
        Spinner mechanic=customLayout.findViewById(R.id.spinnerMechanic);

        ArrayAdapter<Mechanic> brandAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mechanicList);
        mechanic.setAdapter(brandAdapter);
        mechanic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}