package com.example.festivalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalTypeRecyclerAdapter;
import com.example.festivalapp.Adapters.UserFavoriteTypesRecyclerAdapter;
import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.Models.UserFestivalType;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFavoriteFestivalTypesActivity extends AppCompatActivity implements UserFavoriteTypesRecyclerAdapter.OnUserFestivalTypeClickListener {

    private DrawerLayout drawer;
    private RecyclerView userFavoriteFestivalTypeRecyclerView;
    private RecyclerView.LayoutManager userFavoriteFestivalTypeLayoutManager;
    private List<FestivalType> festivalTypesList = new ArrayList<>();
    private UserFavoriteTypesRecyclerAdapter userFavoriteFestivalTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favorite_festival_types);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Favorite festival types");
        setSupportActionBar(toolbar);

       /* drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();*/

//        setUsernameInDrawer();
//
//        drawerFuncionality();

        userFavoriteFestivalTypeRecyclerView = findViewById(R.id.user_favorites_festival_types_rv);
        userFavoriteFestivalTypeLayoutManager = new LinearLayoutManager(this);
        userFavoriteFestivalTypeRecyclerView.setLayoutManager(userFavoriteFestivalTypeLayoutManager);

        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<List<FestivalType>> call = service.getAllFavoriteFestivalTypes(getCurrentUserName());

        final UserFavoriteFestivalTypesActivity main = this;

        call.enqueue(new Callback<List<FestivalType>>() {
            @Override
            public void onResponse(@NonNull Call<List<FestivalType>> call, @NonNull Response<List<FestivalType>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }
                festivalTypesList = response.body();
                userFavoriteFestivalTypeAdapter = new UserFavoriteTypesRecyclerAdapter(festivalTypesList, main);
                userFavoriteFestivalTypeRecyclerView.setHasFixedSize(true);
                userFavoriteFestivalTypeRecyclerView.setAdapter(userFavoriteFestivalTypeAdapter);
            }

            @Override
            public void onFailure(Call<List<FestivalType>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentUserName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("pref_username", "");
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRemoveFavoriteClick(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Remove from favorites")
                .setMessage("Do you really want to remove this festival type from favorites?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final FestivalType festivalType = festivalTypesList.get(position);
                        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                        UserFestivalType userFestivalType = new UserFestivalType(
                                festivalType.getId(),
                                getCurrentUserName());

                        Call<ResponseBody> call = service.removeFavorite(userFestivalType);

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
                                userFavoriteFestivalTypeAdapter.removeItem(position);
                                Toast.makeText(getApplicationContext(), "Festival type removed from favorites.", Toast.LENGTH_SHORT).show();
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
