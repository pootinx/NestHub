// CategoryActivity.java
package com.example.nesthub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nesthub.R;
import com.example.nesthub.adapters.CatAdapter;
import com.example.nesthub.models.HouseModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CatAdapter.OnDataFetchCompleteListener {
    FirebaseFirestore ff;
    RecyclerView poprecycle;
    List<HouseModel> houseModelList;
    CatAdapter catAdapter;
    TextView titlecat;
    ShimmerFrameLayout shimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ff = FirebaseFirestore.getInstance();

        poprecycle = findViewById(R.id.popitems);
        houseModelList = new ArrayList<>();
        catAdapter = new CatAdapter(getApplicationContext(), houseModelList, this);
        poprecycle.setAdapter(catAdapter);
        titlecat = findViewById(R.id.titlecat);
        shimmerViewContainer = findViewById(R.id.shimmerLayout);
        shimmerViewContainer.startShimmer();

        fetchDataAndStopShimmer();
    }

    private void fetchDataAndStopShimmer() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String category = extras.getString("cat");
            titlecat.setText(category);

            if (category.equals("Recommanded")) {
                ff.collection("popular")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        HouseModel house = document.toObject(HouseModel.class);
                                        houseModelList.add(house);
                                    }
                                    catAdapter.notifyDataSetChanged();
                                    onDataFetchComplete();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Query query = ff.collection("Nests").whereEqualTo("category", category);

                query
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        HouseModel house = document.toObject(HouseModel.class);
                                        houseModelList.add(house);
                                    }
                                    catAdapter.notifyDataSetChanged();
                                    onDataFetchComplete();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onDataFetchComplete() {
        shimmerViewContainer.stopShimmer();
        shimmerViewContainer.setVisibility(View.GONE);
    }
}
