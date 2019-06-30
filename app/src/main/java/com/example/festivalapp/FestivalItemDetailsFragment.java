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
    private static RatingBar ratingBar;

    public static FestivalItemDetailsFragment newInstance(Festival festival) {
        FestivalItemDetailsFragment fragment = new FestivalItemDetailsFragment();
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("Festival", festival);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static void UpdateRateBar(String rating) {
        ratingBar.setRating(Float.parseFloat(rating));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_festival_detail, container, false);
        festival = (Festival) getArguments().getSerializable("Festival");
        ratingBar = ((RatingBar)rootView.findViewById(R.id.festival_detail_rating));
        ((TextView)rootView.findViewById(R.id.festival_detail_description)).setText(festival.getDescription());
        ((TextView)rootView.findViewById(R.id.festival_detail_address)).setText(festival.getAddress());
        ((TextView)rootView.findViewById(R.id.festival_detail_start_date)).setText(festival.getStartDate().toString());
        ((TextView)rootView.findViewById(R.id.festival_detail_end_date)).setText(festival.getEndDate().toString());
        ((TextView)rootView.findViewById(R.id.festival_detail_start_time)).setText(festival.getTimeStart());
        ((TextView)rootView.findViewById(R.id.festival_detail_attendees_number)).setText(String.valueOf(festival.getUserAttendings().size()));
        ratingBar.setRating(Float.parseFloat(festival.getRating()));

        return rootView;
    }


}
