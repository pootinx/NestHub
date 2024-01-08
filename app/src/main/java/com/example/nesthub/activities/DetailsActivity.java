package com.example.nesthub.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nesthub.R;

public class DetailsActivity extends AppCompatActivity {
    TextView duration, location, title, description, price,availability;
    ImageView url_image, imageViewBack;
    String textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        duration = findViewById(R.id.duration);
        availability =findViewById(R.id.availability);
        location = findViewById(R.id.location);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        url_image = findViewById(R.id.url_image);
        imageViewBack = findViewById(R.id.imageView4);

        findViewById(R.id.reserv_bottom).setOnClickListener(new View.OnClickListener() {    @Override
        public void onClick(View view) {        // Ouvre l'application WhatsApp
            Intent intent = new Intent(Intent.ACTION_VIEW);        intent.setData(Uri.parse("https://wa.me/212763972823?text=I+want+to+reserve+this+item+"+textMsg));
            startActivity(intent);    }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titleValue, priceValue, durationValue, locationValue, descriptionValue, availabilityValue, url_imageValue;
            titleValue = extras.getString("title");
            priceValue = extras.getString("price");
            durationValue = extras.getString("duration");
            locationValue = extras.getString("location");
            availabilityValue = extras.getString("availability");
            descriptionValue = extras.getString("description");
            url_imageValue = extras.getString("url_image");

            textMsg = titleValue + "+in+" + locationValue + "+on+" + availabilityValue + "+:\n+" + url_imageValue;


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
}
