package com.example.festivalapp;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.festivalapp.Models.User;

public class UserAttendingProfileActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_attending_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        user = (User) getIntent().getSerializableExtra("User");
        toolbar.setTitle(user.getUsername());
        setSupportActionBar(toolbar);
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView)findViewById(R.id.user_attending_profile_full_name)).setText(user.getFirstname() + ' ' + user.getLastname());
        ((TextView)findViewById(R.id.user_attending_profile_location)).setText(user.getCity());

    }
}
