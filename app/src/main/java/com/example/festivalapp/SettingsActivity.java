package com.example.festivalapp;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.settings_activity);

//        Preference changePassword = findPreference("ChangePassword");
//        changePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                DialogFragment dialog = new ChangePasswordDialog();
//                dialog.show(getSupportFragmentManager(), );
//                return true;
//            }
//        });
    }
}
