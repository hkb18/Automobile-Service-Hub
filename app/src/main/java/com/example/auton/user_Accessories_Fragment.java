package com.example.auton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.auton.databinding.FragmentUserAccessoriesBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_Accessories_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_Accessories_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentUserAccessoriesBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_Accessories_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_Accessories_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user_Accessories_Fragment newInstance(String param1, String param2) {
        user_Accessories_Fragment fragment = new user_Accessories_Fragment();
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
        binding = FragmentUserAccessoriesBinding.inflate(getLayoutInflater());

        binding.screensSpeakers.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), user_View_Screens_Speakers.class);
            startActivity(i);
        });
        binding.carCarePurifier.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), user_View_Carecare_Purifiers.class);
            startActivity(i);
        });
        binding.floorMatCushion.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), user_View_FloormatsCushions.class);
            startActivity(i);
        });
        binding.hornsProtectives.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), user_view_HornsProtectives.class);
            startActivity(i);
        });
        binding.lightsChargers.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), user_view_LightsChargers.class);
            startActivity(i);
        });
        binding.roadsideAssistance.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), user_view_RoadsideAssistance.class);
            startActivity(i);
        });
        return binding.getRoot();
    }
}