package com.example.festivalapp;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.festivalapp.dummy.DummyContent;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private RecyclerView festivalTypeRecyclerView;
    private RecyclerView.LayoutManager festivalTypeLayoutManager;
    private List<DummyContent.DummyItem> festivalTypesList;
    private FestivalTypeRecyclerAdapter festivalTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        festivalTypeRecyclerView = findViewById(R.id.festival_types_rv);
        festivalTypeLayoutManager = new LinearLayoutManager(this);
        festivalTypeRecyclerView.setLayoutManager(festivalTypeLayoutManager);
        festivalTypesList = DummyContent.ITEMS;
        festivalTypeAdapter = new FestivalTypeRecyclerAdapter(festivalTypesList);
        festivalTypeRecyclerView.setHasFixedSize(true);
        festivalTypeRecyclerView.setAdapter(festivalTypeAdapter);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}