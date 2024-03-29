package com.example.auton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.auton.databinding.FragmentAdminHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_Home_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAdminHomeBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_Home_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_Home_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_Home_Fragment newInstance(String param1, String param2) {
        admin_Home_Fragment fragment = new admin_Home_Fragment();
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
        binding = FragmentAdminHomeBinding.inflate(getLayoutInflater());

        binding.btnBookedService.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), admin_viewBookedService.class);
            startActivity(i);
        });
        binding.btnPurchaseHistory.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), admin_viewPurchaseHistory.class);
            startActivity(i);
        });
        binding.btnViewFeedback.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), viewFeedback.class);
            startActivity(i);
        });
        return binding.getRoot();
    }
}