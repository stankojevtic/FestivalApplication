package com.example.festivalapp.Settings;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.festivalapp.LoginActivity;
import com.example.festivalapp.Models.ChangePassword;
import com.example.festivalapp.Models.User;
import com.example.festivalapp.R;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordDialog extends DialogFragment {

    private LayoutInflater inflater;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private String oldPasswordValue;
    private String newPasswordValue;
    private String confirmPasswordValue;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_settings_change_password, null))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangePasswordDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null)
        {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    oldPasswordEditText = (EditText) getDialog().findViewById(R.id.old_password);
                    oldPasswordValue = oldPasswordEditText.getText().toString();
                    newPasswordEditText = (EditText) getDialog().findViewById(R.id.new_password);
                    newPasswordValue = newPasswordEditText.getText().toString();
                    confirmPasswordEditText = (EditText) getDialog().findViewById(R.id.conf_password);
                    confirmPasswordValue = confirmPasswordEditText.getText().toString();

                    if (oldPasswordValue.length() == 0) {
                        oldPasswordEditText.setError("Enter value");
                    } else if (newPasswordValue.length() == 0) {
                        newPasswordEditText.setError("Enter value");
                    } else if (confirmPasswordValue.length() == 0) {
                        confirmPasswordEditText.setError("Enter value");
                    } else if (!newPasswordValue.equals(confirmPasswordValue)) {
                        confirmPasswordEditText.setError("Passwords must match.");
                    } else {
                        ChangePassword changePasswordDTO = new ChangePassword(
                                getCurrentUsername(),
                                oldPasswordValue,
                                newPasswordValue,
                                confirmPasswordValue
                        );

                        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                        Call<ResponseBody> call = service.changePassword(changePasswordDTO);

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
                                Toast.makeText(inflater.getContext(), "Password succesfully changed!", Toast.LENGTH_SHORT).show();
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

    private String getCurrentUsername() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        return preferences.getString("pref_username", "");
    }
}


