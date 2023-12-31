package com.example.nesthub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.nesthub.R;
import com.example.nesthub.adapters.CatAdapter;
import com.example.nesthub.adapters.HouseAdapter;
import com.example.nesthub.models.HouseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    FirebaseFirestore ff ;
    RecyclerView poprecycle;
    List<HouseModel> houseModelList;
    CatAdapter catAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        ff = FirebaseFirestore.getInstance();

        poprecycle = findViewById(R.id.popitems);
        houseModelList = new ArrayList<>();
        catAdapter = new CatAdapter(getApplicationContext(), houseModelList);
        poprecycle.setAdapter(catAdapter);



        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String category = extras.getString("cat");


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
                                    catAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }



    }
}