package com.example.nesthub.adapters;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nesthub.models.HouseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter {

    private final Context context;
    private final RecyclerView recyclerView;
    private final List<HouseModel> houseModelList;
    private final CatAdapter catAdapter;

    private final FirebaseFirestore ff;

    public ExploreAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;

        this.houseModelList = new ArrayList<>();
        this.catAdapter = new CatAdapter(context, houseModelList);

        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.setAdapter(catAdapter);

        this.ff = FirebaseFirestore.getInstance();
    }

    public void fetchNestsData() {
        ff.collection("Nests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            houseModelList.clear(); // Clear existing data
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HouseModel house = document.toObject(HouseModel.class);
                                houseModelList.add(house);
                            }
                            catAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
