package com.example.festivalapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.festivalapp.Settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Settings");

        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState!=null)
                return;

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new SettingsFragment()).commit();
        }

    }



    /*@Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.settings_activity);

        Preference changePassword = findPreference("ChangePassword");
        changePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialog = new ChangePasswordDialog();
                dialog.show(, "ChangePassword");
                return true;
            }
        });
    }*/
}
