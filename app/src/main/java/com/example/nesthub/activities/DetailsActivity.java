package com.example.nesthub.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nesthub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {
    TextView duration, location, title, description, price, availability;
    ImageView url_image, imageViewBack;
    FirebaseAuth mAuth;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mAuth = FirebaseAuth.getInstance();

        duration = findViewById(R.id.duration);
        availability = findViewById(R.id.availability);
        location = findViewById(R.id.location);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        url_image = findViewById(R.id.url_image);
        imageViewBack = findViewById(R.id.imageView4);

        findViewById(R.id.reserv_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add reservation details to Firebase Realtime Database
                addReservationToDatabase();
            }
        });

        // Use the class-level extras variable
        extras = getIntent().getExtras();
        if (extras != null) {
            String titleValue, priceValue, durationValue, locationValue, descriptionValue, availabilityValue, url_imageValue;
            titleValue = extras.getString("title");
            priceValue = extras.getString("price");
            durationValue = extras.getString("duration");
            locationValue = extras.getString("location");
            availabilityValue = extras.getString("availability");
            descriptionValue = extras.getString("description");
            url_imageValue = extras.getString("url_image");

            // Set the values to the corresponding TextViews
            title.setText(titleValue);
            price.setText(priceValue);
            duration.setText(durationValue);
            location.setText(locationValue);
            description.setText(descriptionValue);
            availability.setText(availabilityValue);

            // Load the image using Glide
            Glide.with(this).load(url_imageValue).into(url_image);
        }

        // Set up click listener for the back button
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity to go back
                finish();
            }
        });
    }

    private void addReservationToDatabase() {
        // Get the reference to the current user's reservations node
        DatabaseReference reservationsRef = FirebaseDatabase.getInstance().getReference()
                .child("UserInfo").child(mAuth.getCurrentUser().getUid()).child("reservations");

        // Check if extras is not null before accessing its values
        if (extras != null) {
            // Get the title, location, and url_image of the item
            String itemTitle = title.getText().toString();
            String itemLocation = location.getText().toString();
            String itemImageUrl = extras.getString("url_image");

            // Add the reservation details to the database
            DatabaseReference newReservationRef = reservationsRef.push();
            newReservationRef.child("title").setValue(itemTitle);
            newReservationRef.child("location").setValue(itemLocation);
            newReservationRef.child("url_image").setValue(itemImageUrl)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Reservation added successfully
                            showToast("Reservation added successfully");
                        } else {
                            // Failed to add reservation
                            showToast("Failed to add reservation");
                        }
                    });
        }
    }

    private void showToast(String message) {
        Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
