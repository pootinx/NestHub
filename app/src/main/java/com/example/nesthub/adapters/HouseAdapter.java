package com.example.nesthub.adapters;

import android.content.Context;
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
import com.example.nesthub.activities.DetailsActivity;
import com.example.nesthub.models.HouseModel;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> {
    private Context context;
    private List<HouseModel> houseList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HouseAdapter(Context context, List<HouseModel> houseList) {
        this.context = context;
        this.houseList = houseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardhomesmall, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HouseModel house = houseList.get(position);

        Glide.with(context).load(house.getUrl_image()).into(holder.urlImage);
        holder.title.setText(house.getTitle());
        holder.price.setText(house.getPrice() + " MAD");
        holder.availability.setText(house.getAvailability());
        holder.duration.setText(house.getDuration());
        holder.location.setText(house.getLocation());
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView availability, duration, location, price, title;
        ImageView urlImage;
        ConstraintLayout cardClick;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            urlImage = itemView.findViewById(R.id.url_image);
            availability = itemView.findViewById(R.id.availability);
            duration = itemView.findViewById(R.id.duration);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);
            cardClick = itemView.findViewById(R.id.cardclick);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
