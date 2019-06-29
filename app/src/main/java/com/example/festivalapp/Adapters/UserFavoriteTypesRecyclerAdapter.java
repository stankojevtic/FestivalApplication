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
import com.example.festivalapp.UserFavoriteFestivalTypesActivity;

import java.util.List;

public class UserFavoriteTypesRecyclerAdapter extends RecyclerView.Adapter<UserFavoriteTypesRecyclerAdapter.UserFavoriteViewHolder> {

    private List<FestivalType> festivalTypes;
    private OnUserFestivalTypeClickListener listener;

    public UserFavoriteTypesRecyclerAdapter(List<FestivalType> festivalTypes, OnUserFestivalTypeClickListener listener)
    {
        this.festivalTypes = festivalTypes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_festival_types_favorites_view_layout, parent, false);

        UserFavoriteViewHolder userFavoriteViewHolder = new UserFavoriteViewHolder(view, listener);

        return userFavoriteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserFavoriteViewHolder holder, int i) {
        holder.festivalType.setText(festivalTypes.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return festivalTypes.size();
    }

    public static class UserFavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView festivalType;
        OnUserFestivalTypeClickListener listener;
        ImageView festivalRemovedFromFavorites;

        public UserFavoriteViewHolder(@NonNull View itemView, final OnUserFestivalTypeClickListener listener) {
            super(itemView);
            festivalType = itemView.findViewById(R.id.user_festival_type_rv_username);
            festivalRemovedFromFavorites = itemView.findViewById(R.id.user_festival_type_rv_favorite);
            this.listener = listener;
            itemView.setOnClickListener(this);
            festivalRemovedFromFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRemoveFavoriteClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnUserFestivalTypeClickListener {
        void onItemClick(int position);
        void onRemoveFavoriteClick (int position);
    }

    public void removeItem(int position) {
        festivalTypes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, festivalTypes.size());
    }
}
