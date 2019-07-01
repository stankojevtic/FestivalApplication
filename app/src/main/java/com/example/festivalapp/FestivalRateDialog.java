package com.example.festivalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalRateDialog extends DialogFragment {

    private RatingBar ratingBar;
    private int festivalId;
    private LayoutInflater inflater;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rate_festival, null);
        ratingBar = view.findViewById(R.id.user_rate_ratingbar);
        ratingBar.setRating(3);

        builder.setView(view)
                .setTitle("Rate this festival")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Rate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float rate = ratingBar.getRating();
                        if (rate == 0) {
                            dialog.dismiss();
                        } else {
                            FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);

                            festivalId = getArguments().getInt("festivalId");
                            Call<ResponseBody> call = service.rateFestival(rate, festivalId, getCurrentUserName());

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
                                    Toast.makeText(inflater.getContext(), "Festival successfully rated!", Toast.LENGTH_SHORT).show();
                                    updateRate();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(inflater.getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
        return builder.create();
    }

    private String getCurrentUserName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        return preferences.getString("pref_username", "");
    }

    private void updateRate() {
        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<Festival> call = service.getFestival(festivalId);

        call.enqueue(new Callback<Festival>() {
            @Override
            public void onResponse(@NonNull Call<Festival> call, @NonNull Response<Festival> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                final Festival festForUpdate = response.body();
                FestivalItemDetailsFragment.UpdateRateBar(festForUpdate.getRating());
            }

            @Override
            public void onFailure(Call<Festival> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*@Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<Festival> call = service.getFestival(festivalId);

        call.enqueue(new Callback<Festival>() {
            @Override
            public void onResponse(@NonNull Call<Festival> call, @NonNull Response<Festival> response) {
                if (!response.isSuccessful()) {
                    //
                    return;
                }
                final Festival festForUpdate = response.body();
                FestivalItemDetailsFragment.UpdateRateBar(festForUpdate.getRating());
            }

            @Override
            public void onFailure(Call<Festival> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
