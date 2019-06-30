package com.example.festivalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.AlertDialog;
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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.TextView;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalTypeRecyclerAdapter;
import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.Models.UserFestivalType;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FestivalTypeRecyclerAdapter.OnFestivalTypeClickListener {

    private DrawerLayout drawer;
    private RecyclerView festivalTypeRecyclerView;
    private RecyclerView.LayoutManager festivalTypeLayoutManager;
    private List<FestivalType> festivalTypesList = new ArrayList<>();
    private FestivalTypeRecyclerAdapter festivalTypeAdapter;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All festivals");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        setUsernameInDrawer();

        drawerFuncionality();

        festivalTypeRecyclerView = findViewById(R.id.festival_types_rv);
        festivalTypeLayoutManager = new LinearLayoutManager(this);
        festivalTypeRecyclerView.setLayoutManager(festivalTypeLayoutManager);

        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<List<FestivalType>> call = service.getAllFestivalTypes();

        final MainActivity main = this;

        call.enqueue(new Callback<List<FestivalType>>() {
            @Override
            public void onResponse(@NonNull Call<List<FestivalType>> call, @NonNull Response<List<FestivalType>> response) {
                if (!response.isSuccessful()) {
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

    private void drawerFuncionality() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawer.closeDrawers();
                        //navigationView.getMenu().findItem(R.id.nav_festivals).setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_logout:
                                logOutUser();
                                break;
                            case R.id.nav_festivals:
                                //goToAllFestivals();
                                break;
                            case R.id.nav_settings:
                                goToSettings();
                                break;
                            case R.id.nav_favorites:
                                goToUserFavorites();
                                break;
                        }
                        return true;
                    }
                });
    }

    private void goToUserFavorites() {
        Intent intent = new Intent(getApplicationContext(), UserFavoriteFestivalTypesActivity.class);
        startActivity(intent);
    }

    private void goToSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }


    private void logOutUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("pref_username", "");
        edit.commit();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.drawer_menu);
        navigationView.getMenu().findItem(R.id.nav_festivals).setChecked(true);
    }

    @Override
    public void onItemClick(int position) {
        FestivalType festivalType = festivalTypesList.get(position);
        Intent intent = new Intent(getApplicationContext(), FestivalsActivity.class);
        intent.putExtra("festivalTypeId", festivalType.getId());
        intent.putExtra("festivalTypeName", festivalType.getName());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Add to favorites")
                .setMessage("Do you really want to add this festival type to favorites?")
                .setIcon(R.drawable.ic_favorite_festivals)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final FestivalType festivalType = festivalTypesList.get(position);
                        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                        UserFestivalType userFestivalType = new UserFestivalType(
                                festivalType.getId(),
                                getCurrentUserName());

                        Call<ResponseBody> call = service.createFavorite(userFestivalType);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                if (!response.isSuccessful()) {
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        Toast.makeText(getApplicationContext(), jObjError.getString("Message"), Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                }
                                Toast.makeText(getApplicationContext(), "Festival type added to favorites.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null).show();
    }
}