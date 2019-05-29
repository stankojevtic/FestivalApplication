package com.example.festivalapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.festivalapp.Models.Festival;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FestivalItemMapFragment extends Fragment implements OnMapReadyCallback {


    private Festival festival;
    private GoogleMap map;
    private MapView mapView;
    private View rootView;
    private FusedLocationProviderClient mFusedLocationClient;

    public static FestivalItemMapFragment newInstance(Festival festival) {
        FestivalItemMapFragment fragment = new FestivalItemMapFragment();
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("Festival", festival);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_festival_map, container, false);
        festival = (Festival) getArguments().getSerializable("Festival");
        return rootView;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);
        if(mapView != null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        LatLng location = new LatLng(Double.parseDouble(festival.getLocationLatitude()),
                                     Double.parseDouble(festival.getLocationLongitude()));

        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.addMarker(new MarkerOptions().position(location).title(festival.getName())).showInfoWindow();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(13).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
