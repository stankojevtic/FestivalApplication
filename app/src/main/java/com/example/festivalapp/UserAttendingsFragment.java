package com.example.festivalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.festivalapp.Adapters.UsersAttendingRecyclerAdapter;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Models.User;

import java.util.List;

public class UserAttendingsFragment extends Fragment implements UsersAttendingRecyclerAdapter.OnUserAttendingClickListener {

    private List<User> users;
    private Festival festival;
    private RecyclerView userAttendingRecyclerView;
    private RecyclerView.LayoutManager userAttendingLayoutManager;
    private UsersAttendingRecyclerAdapter usersAttendingAdapter;

    public static UserAttendingsFragment newInstance(Festival festival) {
        UserAttendingsFragment fragment = new UserAttendingsFragment();
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("Festival", festival);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users_attending, container, false);

        festival = (Festival) getArguments().getSerializable("Festival");
        users = festival.getUserAttendings();
        userAttendingRecyclerView = (RecyclerView) rootView.findViewById(R.id.users_attending_rv);
        userAttendingLayoutManager = new LinearLayoutManager(this.getContext());
        userAttendingRecyclerView.setLayoutManager(userAttendingLayoutManager);
        usersAttendingAdapter = new UsersAttendingRecyclerAdapter(users, this);
        userAttendingRecyclerView.setHasFixedSize(true);
        userAttendingRecyclerView.setAdapter(usersAttendingAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(int position) {
        User user = users.get(position);
        Intent intent = new Intent(getActivity(), UserAttendingProfileActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}
