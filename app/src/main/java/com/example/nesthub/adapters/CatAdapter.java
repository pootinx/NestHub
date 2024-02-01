package com.example.nesthub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nesthub.R;
import com.example.nesthub.activities.CategoryActivity;
import com.example.nesthub.activities.DetailsActivity;
import com.example.nesthub.models.HouseModel;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {


    Context context;
    List<HouseModel> houselist;

    public CatAdapter(Context context, List<HouseModel> houselist) {
        this.context = context;
        this.houselist = houselist;
    }


    @NonNull
    @Override
    public CatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CatAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardhome_verticall,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatAdapter.ViewHolder holder, int position) {

        String title,price,duration,location,description,availability,url_image;
        title = houselist.get(position).getTitle();
        availability = houselist.get(position).getAvailability();
        description = houselist.get(position).getDescription();
        location = houselist.get(position).getLocation();
        duration = houselist.get(position).getDuration();
        url_image = houselist.get(position).getUrl_image();
        price = houselist.get(position).getPrice() + " MAD";


        Glide.with(context).load(houselist.get(position).getUrl_image()).into(holder.imageView);
        holder.title.setText(houselist.get(position).getTitle());
        holder.price.setText(price);
//        holder.disponible.setText(houselist.get(position).getAvailability());
//        holder.duration.setText(houselist.get(position).getDuration());
        holder.localisation.setText(houselist.get(position).getLocation());







        holder.cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("title", title);
                intent.putExtra("duration", duration);
                intent.putExtra("location", location);
                intent.putExtra("description", description);
                intent.putExtra("availability", availability);
                intent.putExtra("url_image", url_image);




                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return houselist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView availability,duration,localisation,price,title;
        ImageView imageView;
        ConstraintLayout cardclick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById((R.id.imagecard));
            localisation = itemView.findViewById((R.id.location));
            price = itemView.findViewById((R.id.price));
            title = itemView.findViewById((R.id.title));
            availability = itemView.findViewById((R.id.availability));
            duration = itemView.findViewById((R.id.duration));
            cardclick = itemView.findViewById((R.id.cardclick));



        }
    }
}
