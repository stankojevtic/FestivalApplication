package com.example.festivalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festivalapp.Models.User;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText firstnameText;
    private EditText lastnameText;
    private EditText countryText;
    private EditText cityText;
    private EditText addressText;
    private EditText ageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeEditText();
        initializeRegistratonButton();
    }

    private void initializeRegistratonButton() {
        Button registration  = (Button)findViewById(R.id.button_registration);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isValidEmail(emailText.getText().toString())) {
                    emailText.setError("Invalid e-mail");
                } else if (usernameText.getText().toString().length() == 0) {
                    usernameText.setError("Enter username");
                } else if (passwordText.getText().toString().length() == 0) {
                    passwordText.setError("Enter password");
                } else if (firstnameText.getText().toString().length() == 0) {
                    firstnameText.setError("Enter firstname");
                } else if (lastnameText.getText().toString().length() == 0) {
                    lastnameText.setError("Enter lastname");
                } else if (ageText.getText().toString().length() == 0) {
                    ageText.setError("Enter age");
                } else {

                    User user = new User(
                            usernameText.getText().toString(),
                            emailText.getText().toString(),
                            passwordText.getText().toString(),
                            firstnameText.getText().toString(),
                            lastnameText.getText().toString(),
                            cityText.getText().toString(),
                            countryText.getText().toString(),
                            Integer.parseInt(ageText.getText().toString()),
                            addressText.getText().toString());

                    FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                    Call<ResponseBody> call = service.createUser(user);

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
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Uspešno ste se registrovali!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }

    private void initializeEditText(){
        emailText = (EditText) findViewById(R.id.email);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        firstnameText = (EditText) findViewById(R.id.firstname);
        lastnameText = (EditText) findViewById(R.id.lastname);
        countryText = (EditText) findViewById(R.id.country);
        cityText = (EditText) findViewById(R.id.city);
        addressText = (EditText) findViewById(R.id.address);
        ageText = (EditText) findViewById(R.id.age);
    }

    public boolean isValidEmail(CharSequence target) {
        if (target == null || target.length() == 0)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
