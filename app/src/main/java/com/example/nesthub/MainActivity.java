package com.example.nesthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nesthub.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment()  );
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int id= item.getItemId();

            if (id==R.id.home){

                replaceFragment(new HomeFragment());

            } else if (id==R.id.favorite) {

                replaceFragment(new FavoriteFragment());

            } else if (id==R.id.profile) {

                replaceFragment(new ProfileFragment());

            }else if (id==R.id.explore) {

                replaceFragment(new ExploreFragment());

            }


            return true;
        });

    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();


    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//
//
//        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item){
//        int id= item.getItemId();
//
//
//       if (id==R.id.home){
//
//       } else if (id==R.id.home) {
//
//       } else if () {
//
//       } else if () {
//
//       }
//
//
//        return super.onOptionsItemSelected(item);
//
//    }
}