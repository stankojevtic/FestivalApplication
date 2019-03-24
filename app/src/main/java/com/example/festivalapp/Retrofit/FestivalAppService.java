package com.example.festivalapp.Retrofit;

import com.example.festivalapp.Models.FestivalType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FestivalAppService {

    @GET("api/festival-types/")
    Call<List<FestivalType>> getAllFestivalTypes();
}
