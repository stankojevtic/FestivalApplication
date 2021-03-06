package com.example.festivalapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.festivalapp.Models.User;
import com.example.festivalapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAttendingRecyclerAdapter extends RecyclerView.Adapter<UsersAttendingRecyclerAdapter.UsersAttendingViewHolder> {

    private List<User> users;
    private OnUserAttendingClickListener listener;

    public UsersAttendingRecyclerAdapter(List<User> users, OnUserAttendingClickListener listener)
    {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsersAttendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_attending_view_layout, parent, false);

        UsersAttendingViewHolder usersAttendingViewHolder = new UsersAttendingViewHolder(view, listener);

        return usersAttendingViewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAttendingViewHolder holder, int i) {
        holder.userAttendingUsername.setText(users.get(i).getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class UsersAttendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userAttendingUsername;
        CircleImageView userAttendingImage;
        RelativeLayout userAttendingsLayout;
        OnUserAttendingClickListener listener;
        public UsersAttendingViewHolder(@NonNull View itemView, OnUserAttendingClickListener listener) {
            super(itemView);
            userAttendingImage = itemView.findViewById(R.id.user_attending_rv_image);
            userAttendingUsername = itemView.findViewById(R.id.user_attending_rv_username);
            userAttendingsLayout = itemView.findViewById(R.id.user_attending_rv_layout);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnUserAttendingClickListener {
        void onItemClick(int position);
    }
}
