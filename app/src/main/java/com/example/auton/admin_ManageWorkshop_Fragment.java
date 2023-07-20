package com.example.auton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.auton.databinding.FragmentAdminManageWorkshopBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_ManageWorkshop_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_ManageWorkshop_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference databaseReference;
    private FragmentAdminManageWorkshopBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_ManageWorkshop_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_ManageWorkshop_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_ManageWorkshop_Fragment newInstance(String param1, String param2) {
        admin_ManageWorkshop_Fragment fragment = new admin_ManageWorkshop_Fragment();
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
        // Inflate the layout for this fragment
        binding = FragmentAdminManageWorkshopBinding.inflate(getLayoutInflater());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        // ADD NEW WORKSHOP
        binding.linearlayoutAddWorkshop.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), admin_Add_Workshop.class);
            startActivity(i);
        });

        //DELETE EXISTING WORKSHOP
        binding.linearlayoutDeleteWorkshop.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), admin_DeleteWorkshop.class);
            startActivity(i);
        });

        //  ADD CARS
        binding.linearlayoutAddCar.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), admin_AddCars.class);
            startActivity(i);
        });

        //  ADD CARS BODYTYPE
        binding.linearlayoutAddCarBodyType.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), admin_AddBodyType.class);
            startActivity(i);
        });

        // ADD SERVICE
        binding.linearlayoutAddService.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), admin_AddService.class);
            startActivity(i);
        });

        return binding.getRoot();
    }
}