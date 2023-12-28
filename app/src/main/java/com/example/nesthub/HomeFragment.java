package com.example.nesthub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    FirebaseFirestore ff ;
    RecyclerView poprecycle;
    List<HouseModel> houseModelList;
    HouseAdapter houseAdapter;
    ConstraintLayout home,villa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ff = FirebaseFirestore.getInstance(); // Initialize Firestore

        home = view.findViewById(R.id.homecat);
        villa = view.findViewById(R.id.villacat);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("cat", "Home");
            }
        });

        villa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("cat", "Home");
            }
        });



        poprecycle = view.findViewById(R.id.popitems);
        poprecycle.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        houseModelList = new ArrayList<>();
        houseAdapter = new HouseAdapter(getContext(), houseModelList);
        poprecycle.setAdapter(houseAdapter);

        ff.collection("popular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HouseModel house = document.toObject(HouseModel.class);
                                houseModelList.add(house);
                                houseAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), "Error ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return view;
    }
}