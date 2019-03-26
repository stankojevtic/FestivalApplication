package com.example.festivalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.festivalapp.Models.Festival;

public class FestivalItemDetailsFragment extends Fragment {

    private Festival festival;

    public static FestivalItemDetailsFragment newInstance(Festival festival) {
        FestivalItemDetailsFragment fragment = new FestivalItemDetailsFragment();
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("Festival", festival);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_festival_detail, container, false);
        festival = (Festival) getArguments().getSerializable("Festival");
        ((TextView)rootView.findViewById(R.id.festival_detail_name)).setText(festival.getDescription());
        ((RatingBar)rootView.findViewById(R.id.festival_detail_rating)).setRating(Float.parseFloat(festival.getRating()));

        return rootView;
    }


}
