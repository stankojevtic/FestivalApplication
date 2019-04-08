package com.example.festivalapp;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalItemRecyclerAdapter;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalItemsAdminActivity extends AppCompatActivity implements FestivalItemRecyclerAdapter.OnFestivalItemClickListener {

    private RecyclerView festivalItemRecyclerView;
    private RecyclerView.LayoutManager festivalItemLayoutManager;
    private List<Festival> festivalItemsList;
    private FestivalItemRecyclerAdapter festivalItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_items_admin);

        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        toolbar.setTitle("All festivals");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.festivals_admin_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFestivalDialog();
            }
        });

        festivalItemRecyclerView = findViewById(R.id.festival_items_admin_rv);
        festivalItemLayoutManager = new LinearLayoutManager(this);
        festivalItemRecyclerView.setLayoutManager(festivalItemLayoutManager);

        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<List<Festival>> call = service.getAllFestivals();

        final FestivalItemsAdminActivity thisActivity = this;

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

    public void openFestivalDialog()
    {
        FestivalEditDialog dialog = new FestivalEditDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onItemClick(int position) {

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
