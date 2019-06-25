package com.example.festivalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalTypeRecyclerAdapter;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private Boolean userSuccess;
    private String usernameValue;
    private String passwordValue;
    private SharedPreferences preferences;
    private String usernamePrefValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        usernamePrefValue = preferences.getString("pref_username", "");

        if(usernamePrefValue.equals("admin"))
        {
            goToAdminMainPage();
        }
        else if(!usernamePrefValue.equals(""))
        {
            goToAdminMainPage();
        }

        TextView registrationButton = (TextView) findViewById(R.id.registration);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        TextView loginButton = (TextView) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameValue = usernameText.getText().toString();
                passwordValue = passwordText.getText().toString();

                if (usernameText.length() == 0) {
                    usernameText.setError("Enter username");
                } else if (passwordText.length() == 0) {
                    passwordText.setError("Enter password");
                } else {
                    FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                    Call<Boolean> call = service.userLogin(usernameValue, passwordValue);

                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            userSuccess = response.body();
                            String adminLogged = getString(R.string.adminLogged);
                            if (userSuccess && !usernameValue.equals(adminLogged)) {
                                updateSharedPreferences(usernameValue);
                                goToUserMainPage();
                            } else if (userSuccess) {
                                updateSharedPreferences(usernameValue);
                                goToAdminMainPage();
                            } else {
                                Toast.makeText(getApplicationContext(), "Username or password are not valid.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void updateSharedPreferences(String usernameValue) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("pref_username", usernameValue);
        edit.commit();
    }

    private void goToUserMainPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    private void goToAdminMainPage() {
        Intent intent = new Intent(getApplicationContext(), FestivalItemsAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }
}
