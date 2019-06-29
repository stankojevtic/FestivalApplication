package com.example.festivalapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.R;

import java.util.List;

public class FestivalTypeRecyclerAdapter extends RecyclerView.Adapter<FestivalTypeRecyclerAdapter.FestivalTypeViewHolder> {

    private List<FestivalType> festivalTypes;
    private OnFestivalTypeClickListener listener;

    public FestivalTypeRecyclerAdapter(List<FestivalType> festivalTypes, OnFestivalTypeClickListener listener)
    {
        this.festivalTypes = festivalTypes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FestivalTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.festival_type_view_layout, parent, false);

            FestivalTypeViewHolder festivalTypeViewHolder = new FestivalTypeViewHolder(view, listener);

            return festivalTypeViewHolder;
    }

    @Override
    public void onBindViewHolder(FestivalTypeViewHolder holder, int i) {
            holder.festivalType.setText(festivalTypes.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return festivalTypes.size();
    }


    public static class FestivalTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView festivalType;
        OnFestivalTypeClickListener listener;
        ImageView festivalAddToFavorites;

        public FestivalTypeViewHolder(@NonNull View itemView, final OnFestivalTypeClickListener listener) {
            super(itemView);
            festivalType = itemView.findViewById(R.id.festival_type_rv_username);
            festivalAddToFavorites = itemView.findViewById(R.id.festival_type_rv_favorite);
            this.listener = listener;
            itemView.setOnClickListener(this);
            festivalAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFavoriteClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnFestivalTypeClickListener {
        void onItemClick(int position);
        void onFavoriteClick (int position);
    }
}
