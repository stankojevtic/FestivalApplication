package com.example.festivalapp.Settings;

import android.app.DialogFragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.example.festivalapp.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Preference changePassword = findPreference("ChangePassword");
        changePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialog = new ChangePasswordDialog();
                dialog.show(getFragmentManager(), "ChangePassword");
                return true;
            }
        });

        Preference updateUser = findPreference("ChangeProfileDetails");
        updateUser.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialog = new ChangeUserDetailsFragment();
                dialog.show(getFragmentManager(), "ChangeProfileDetails");
                return true;
            }
        });
    }
}
