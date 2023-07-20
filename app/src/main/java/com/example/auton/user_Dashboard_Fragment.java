package com.example.auton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_Dashboard_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_Dashboard_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button book, cancel, view;
    TextView invisibleusername;
    ImageSlider imageSlider;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_Dashboard_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_Dashboard_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user_Dashboard_Fragment newInstance(String param1, String param2) {
        user_Dashboard_Fragment fragment = new user_Dashboard_Fragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user__dashboard, container, false);

        invisibleusername = v.findViewById(R.id.invisibleusername);
        invisibleusername.setText(mParam2);//username in mParam2

        imageSlider = v.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.service1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.service2, ScaleTypes.FIT));
       /* slideModels.add(new SlideModel(R.drawable.car_icon, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.service_icon, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.view, ScaleTypes.FIT));*/

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        book = v.findViewById(R.id.btn_BookService);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getContext(), user_Book_Service.class);
                Intent i = new Intent(getContext(), user_Select_Service_Type.class);
                i.putExtra("Username", mParam2);
                startActivity(i);
            }
        });
        view = v.findViewById(R.id.btn_ViewService);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), user_View_Booked_Service.class);
                intent.putExtra("Username", mParam2);
                startActivity(intent);
            }
        });
        cancel = v.findViewById(R.id.btn_CancelService);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), user_Delete_Service.class);
                startActivity(intent);
            }
        });
        return v;
    }
}