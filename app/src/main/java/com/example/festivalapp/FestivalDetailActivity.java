package com.example.festivalapp;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.festivalapp.Models.Festival;

public class FestivalDetailActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private Festival festival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_festival_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        festival = (Festival) getIntent().getSerializableExtra("Festival");
        toolbar.setTitle(festival.getName());
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        ((TextView)findViewById(R.id.festival_detail_description)).setText(festival.getDescription());
        ((RatingBar)findViewById(R.id.festival_detail_rating)).setRating(Float.parseFloat(festival.getRating()));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
