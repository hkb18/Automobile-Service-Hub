package com.example.auton;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.auton.databinding.ActivityUserViewBookedServiceBinding;
import com.example.auton.databinding.FragmentWorkshopDashboardBinding;
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
    RecyclerView recyclerView;

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
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_workshop__dashboard, container, false);

        viewBookedService_interface=this;

        SharedPreferences sh= getContext().getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        s1=sh.getString("Username","");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        list=new ArrayList<>();

        recyclerView=v.findViewById(R.id.rvBookedService);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter =new Workshop_BookedService_Adapter(getContext(),list);
        recyclerView.setAdapter(myAdapter);


        databaseReference.child("Service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Worshop_View_Service_modelClass bookedService = dataSnapshot.getValue(Worshop_View_Service_modelClass.class);
                    list.add(dataSnapshot.getValue(Worshop_View_Service_modelClass.class));
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(v.getContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
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


        return v;
    }

    @Override
    public void view(String key, int position) {

    }
}