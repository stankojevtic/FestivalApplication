package com.example.festivalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.festivalapp.Adapters.FestivalItemRecyclerAdapter;
import com.example.festivalapp.dummy.DummyContent;

import java.util.List;

public class FestivalsActivity extends AppCompatActivity implements FestivalItemRecyclerAdapter.OnFestivalItemClickListener {

    private DrawerLayout drawer;
    private RecyclerView festivalItemRecyclerView;
    private RecyclerView.LayoutManager festivalItemLayoutManager;
    private List<DummyContent.DummyItem> festivalItemsList;
    private FestivalItemRecyclerAdapter festivalItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festivals);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        festivalItemRecyclerView = findViewById(R.id.festival_items_rv);
        festivalItemLayoutManager = new LinearLayoutManager(this);
        festivalItemRecyclerView.setLayoutManager(festivalItemLayoutManager);
        festivalItemsList = DummyContent.ITEMS;
        festivalItemAdapter = new FestivalItemRecyclerAdapter(festivalItemsList, this);
        festivalItemRecyclerView.setHasFixedSize(true);
        festivalItemRecyclerView.setAdapter(festivalItemAdapter);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(int position) {
        Log.i("blabla123", festivalItemsList.get(position).content);
        Intent intent = new Intent(getApplicationContext(), Activity_Register.class);
        startActivity(intent);
    }
}