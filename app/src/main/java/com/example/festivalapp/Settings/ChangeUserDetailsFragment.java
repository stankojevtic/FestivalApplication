package com.example.festivalapp.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.festivalapp.FestivalItemDetailsFragment;
import com.example.festivalapp.Models.ChangePassword;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Models.User;
import com.example.festivalapp.Models.UserUpdate;
import com.example.festivalapp.R;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUserDetailsFragment extends DialogFragment {

    private LayoutInflater inflater;
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText ageEditText;
    private EditText cityEditText;
    private EditText countryEditText;
    private EditText addressEditText;
    private EditText aboutMeEditText;
    private String firstnameValue;
    private String lastnameValue;
    private String ageValue;
    private String cityValue;
    private String countryValue;
    private String addressValue;
    private String aboutMeValue;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_settings_change_profile_details, null);

        openInitializeValues();


        builder.setView(view)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeUserDetailsFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    getValues();

                    if (firstnameValue.length() == 0) {
                        firstnameEditText.setError("Enter firstname");
                    } else if (lastnameValue.length() == 0) {
                        lastnameEditText.setError("Enter lastname");
                    } else if (ageValue.length() == 0) {
                        ageEditText.setError("Enter value");
                    } else {
                        UserUpdate userUpdateDTO = new UserUpdate(
                                getCurrentUsername(),
                                firstnameValue,
                                lastnameValue,
                                Integer.parseInt(ageValue),
                                cityValue,
                                countryValue,
                                addressValue,
                                aboutMeValue
                        );

                        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                        Call<ResponseBody> call = service.updateUser(userUpdateDTO);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                if (!response.isSuccessful()) {
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        Toast.makeText(inflater.getContext(), jObjError.getString("Message"), Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(inflater.getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                }
                                Toast.makeText(inflater.getContext(), "User succesfully updated!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(inflater.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    private void openInitializeValues() {
        firstnameEditText = (EditText) view.findViewById(R.id.change_firstname);
        lastnameEditText = (EditText) view.findViewById(R.id.change_lastname);
        ageEditText = (EditText) view.findViewById(R.id.change_age);
        cityEditText = (EditText) view.findViewById(R.id.change_city);
        countryEditText = (EditText) view.findViewById(R.id.change_country);
        addressEditText = (EditText) view.findViewById(R.id.change_address);
        aboutMeEditText = (EditText) view.findViewById(R.id.change_aboutme);

        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<User> call = service.getUser(getCurrentUsername());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                User user = response.body();
                firstnameEditText.setText(user.getFirstname());
                lastnameEditText.setText(user.getLastname());
                ageEditText.setText(Integer.toString(user.getAge()));
                cityEditText.setText(user.getCity());
                countryEditText.setText(user.getCountry());
                addressEditText.setText(user.getAddress());
                aboutMeEditText.setText(user.getAboutMe());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    private void getValues() {
        firstnameValue = firstnameEditText.getText().toString();
        lastnameValue = lastnameEditText.getText().toString();
        ageValue = ageEditText.getText().toString();
        cityValue = cityEditText.getText().toString();
        countryValue = countryEditText.getText().toString();
        addressValue = addressEditText.getText().toString();
        aboutMeValue = aboutMeEditText.getText().toString();
    }

    private String getCurrentUsername() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        return preferences.getString("pref_username", "");
    }
}



