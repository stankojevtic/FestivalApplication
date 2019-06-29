package com.example.festivalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalItemRecyclerAdapter;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalsActivity extends AppCompatActivity implements FestivalItemRecyclerAdapter.OnFestivalItemClickListener {

    private DrawerLayout drawer;
    private RecyclerView festivalItemRecyclerView;
    private RecyclerView.LayoutManager festivalItemLayoutManager;
    private List<Festival> festivalItemsList;
    private int festivalTypeId;
    private FestivalItemRecyclerAdapter festivalItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festivals);

        Toolbar toolbar = findViewById(R.id.toolbar);
        String festivalTypeName = getIntent().getStringExtra("festivalTypeName");
        toolbar.setTitle(festivalTypeName);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        setUsernameInDrawer();

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        festivalItemRecyclerView = findViewById(R.id.festival_items_rv);
        festivalItemLayoutManager = new LinearLayoutManager(this);
        festivalItemRecyclerView.setLayoutManager(festivalItemLayoutManager);
        festivalTypeId = getIntent().getIntExtra("festivalTypeId", 0);

        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<List<Festival>> call = service.getAllFestivalsByType(festivalTypeId);

        final FestivalsActivity thisActivity = this;

        call.enqueue(new Callback<List<Festival>>() {
            @Override
            public void onResponse(@NonNull Call<List<Festival>> call, @NonNull Response<List<Festival>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }
                festivalItemsList = response.body();
                festivalItemAdapter = new FestivalItemRecyclerAdapter(festivalItemsList, thisActivity);
                festivalItemRecyclerView.setHasFixedSize(true);
                festivalItemRecyclerView.setAdapter(festivalItemAdapter);
            }

            @Override
            public void onFailure(Call<List<Festival>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setUsernameInDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_info);
        navUsername.setText(getCurrentUserName());
    }

    private String getCurrentUserName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("pref_username", "");
    }

    @Override
    public void onItemClick(int position) {
        Festival festival = festivalItemsList.get(position);
        Intent intent = new Intent(getApplicationContext(), FestivalDetailTabsActivity.class);
        intent.putExtra("Festival", festival);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                festivalItemAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}