package com.example.festivalapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.festivalapp.Adapters.FestivalTypeRecyclerAdapter;
import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.Retrofit.FestivalAppService;
import com.example.festivalapp.Retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalEditDialog extends AppCompatDialogFragment {

    private EditText editTextFestivalName;
    private EditText editTextFestivalStartDate;
    private Spinner spinnerFestivalTypes;
    private List<FestivalType> festivalTypes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_admin_festivals, null);

        editTextFestivalName = view.findViewById(R.id.dialog_festival_name);
        editTextFestivalStartDate = view.findViewById(R.id.dialog_festival_start_date);
        spinnerFestivalTypes = view.findViewById(R.id.dialog_festival_type);

        FestivalAppService service = RetrofitInstance.getInstance().create(FestivalAppService.class);
        Call<List<FestivalType>> call = service.getAllFestivalTypes();

        final FestivalEditDialog thisActivity = this;

        call.enqueue(new Callback<List<FestivalType>>() {
            @Override
            public void onResponse(@NonNull Call<List<FestivalType>> call, @NonNull Response<List<FestivalType>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }
                festivalTypes = response.body();
                ArrayAdapter<FestivalType> festivalTypesAdapter = new
                        ArrayAdapter<FestivalType>(getActivity(), android.R.layout.simple_spinner_item, festivalTypes);
                festivalTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFestivalTypes.setAdapter(festivalTypesAdapter);
            }

            @Override
            public void onFailure(Call<List<FestivalType>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view).setTitle("Save festival").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                FestivalType ft = (FestivalType) spinnerFestivalTypes.getSelectedItem();
//                Log.e("MyTagGoesHere", String.valueOf(ft.getId()));
            }
        });

        return builder.create();
    }
}
