package com.example.festivalapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalTypeRecyclerAdapter;
import com.example.festivalapp.Adapters.UserFavoriteTypesRecyclerAdapter;
import com.example.festivalapp.Models.Attend;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalDetailTabsActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Festival festival;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_detail_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        festival = (Festival) getIntent().getSerializableExtra("Festival");
        toolbar.setTitle(festival.getName());
        setSupportActionBar(toolbar);

    /*    drawer = findViewById(R.id.drawer_layout);

        setUsernameInDrawer();

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();*/

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_festival_details_attend_rate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rate_festival:
                rateFestival();
                return true;
            case R.id.attend_festival:
                attendFestival();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attendFestival() {
        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

        Attend attend = new Attend(festival.getId(), getCurrentUserName());

        Call<ResponseBody> call = service.attendFestival(attend);

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
                Toast.makeText(getApplicationContext(), "Prijava za festival uspešna.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rateFestival() {
        Bundle args = new Bundle();
        args.putInt("festivalId", festival.getId());
        FestivalRateDialog dialog = new FestivalRateDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "Rate festival");
       /* getSupportFragmentManager().executePendingTransactions();
        dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
                Call<Festival> call = service.getFestival(festival.getId());

                call.enqueue(new Callback<Festival>() {
                    @Override
                    public void onResponse(@NonNull Call<Festival> call, @NonNull Response<Festival> response) {
                        if (!response.isSuccessful()) {
                            //
                            return;
                        }
                        Festival festForUpdate = response.body();
                        FestivalItemDetailsFragment.UpdateRateBar(festForUpdate.getRating());
                    }

                    @Override
                    public void onFailure(Call<Festival> call, Throwable t) {
                        //Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
//                frag.getFragmentManager().beginTransaction().detach(frag).commit();
//                frag.getFragmentManager().beginTransaction().attach(frag).commit();
//                FestivalItemDetailsFragment.newInstance(festival).getFragmentManager().beginTransaction()
//                        .detach(FestivalItemDetailsFragment.newInstance(festival)).commit();
//
//                FestivalItemDetailsFragment.newInstance(festival).getFragmentManager().beginTransaction()
//                        .attach(FestivalItemDetailsFragment.newInstance(festival)).commit();

                       *//* getSupportFragmentManager().beginTransaction().detach().commit();*//*
            }
        });*/
    }
/*
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = FestivalItemDetailsFragment.newInstance(festival);
                    break;
                case 1:
                    fragment = FestivalItemMapFragment.newInstance(festival);
                    break;
                case 2:
                    fragment = UserAttendingsFragment.newInstance(festival);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
