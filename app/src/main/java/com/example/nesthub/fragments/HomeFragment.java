package com.example.nesthub.fragments;

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

import com.example.nesthub.activities.CategoryActivity;
import com.example.nesthub.activities.DetailsActivity;
import com.example.nesthub.adapters.HouseAdapter;
import com.example.nesthub.models.HouseModel;
import com.example.nesthub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FirebaseFirestore ff;
    private RecyclerView poprecycle;
    private List<HouseModel> houseModelList;
    private HouseAdapter houseAdapter;
    private ConstraintLayout home, villa, office, room;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ff = FirebaseFirestore.getInstance(); // Initialize Firestore

        // Initialize your views
        home = view.findViewById(R.id.homecat);
        villa = view.findViewById(R.id.villacat);
        office = view.findViewById(R.id.officecat);
        room = view.findViewById(R.id.Roomcat);

        // Set up click listeners for categories
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCategoryActivity("Home");
            }
        });

        villa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCategoryActivity("Villa");
            }
        });

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCategoryActivity("Room");
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCategoryActivity("Office");
            }
        });

        // Set up RecyclerView
        poprecycle = view.findViewById(R.id.popitems);
        poprecycle.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        houseModelList = new ArrayList<>();
        houseAdapter = new HouseAdapter(getContext(), houseModelList);

        // Set up item click listener for the adapter
        houseAdapter.setOnItemClickListener(new HouseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click, start DetailsActivity and pass information
                HouseModel selectedHouse = houseModelList.get(position);
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("title", selectedHouse.getTitle());
                intent.putExtra("availability", selectedHouse.getAvailability());
                // Add other information as needed
                startActivity(intent);
            }
        });

        poprecycle.setAdapter(houseAdapter);

        // Fetch data from Firestore
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

    // Helper method to start CategoryActivity
    private void startCategoryActivity(String category) {
        Intent intent = new Intent(getContext(), CategoryActivity.class);
        intent.putExtra("cat", category);
        startActivity(intent);
    }
}
