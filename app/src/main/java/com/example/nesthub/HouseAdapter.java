package com.example.nesthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> {
    Context context;
    List<HouseModel> houselist;

    public HouseAdapter(Context context, List<HouseModel> houselist) {
        this.context = context;
        this.houselist = houselist;
    }

    @NonNull
    @Override
    public HouseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardhomesmall,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HouseAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(houselist.get(position).getUrl_image()).into(holder.imageView);
        holder.title.setText(houselist.get(position).getTitle());
        holder.price.setText(houselist.get(position).getPrice());
        holder.disponible.setText(houselist.get(position).getAvailability());
        holder.duration.setText(houselist.get(position).getDuration());
        holder.localisation.setText(houselist.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return houselist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView disponible,duration,localisation,price,title;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById((R.id.imagecard));
            disponible = itemView.findViewById((R.id.disponible));
            duration = itemView.findViewById((R.id.duration));
            localisation = itemView.findViewById((R.id.localisation));
            price = itemView.findViewById((R.id.price));
            title = itemView.findViewById((R.id.title));

        }
    }
}
