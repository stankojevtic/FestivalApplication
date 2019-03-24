package com.example.festivalapp;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalTypeRecyclerAdapter;
import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;
import com.example.festivalapp.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FestivalTypeRecyclerAdapter.OnFestivalTypeClickListener {

    private DrawerLayout drawer;
    private RecyclerView festivalTypeRecyclerView;
    private RecyclerView.LayoutManager festivalTypeLayoutManager;
    private List<FestivalType> festivalTypesList = new ArrayList<>();
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

        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<List<FestivalType>> call = service.getAllFestivalTypes();

        final MainActivity main = this;

        call.enqueue(new Callback<List<FestivalType>>() {
            @Override
            public void onResponse(@NonNull Call<List<FestivalType>> call,@NonNull Response<List<FestivalType>> response) {
                if(!response.isSuccessful())
                {
                   Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }
                festivalTypesList = response.body();
                festivalTypeAdapter = new FestivalTypeRecyclerAdapter(festivalTypesList, main);
                festivalTypeRecyclerView.setHasFixedSize(true);
                festivalTypeRecyclerView.setAdapter(festivalTypeAdapter);
            }

            @Override
            public void onFailure(Call<List<FestivalType>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
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
        int festivalTypeId = festivalTypesList.get(position).getId();
        Intent intent = new Intent(getApplicationContext(), FestivalsActivity.class);
        intent.putExtra("festivalTypeId", festivalTypeId);
        startActivity(intent);
    }
}