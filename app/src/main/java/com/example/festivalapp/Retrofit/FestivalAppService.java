package com.example.festivalapp.Retrofit;

import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Models.FestivalType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FestivalAppService {

    @GET("api/festival-types/")
    Call<List<FestivalType>> getAllFestivalTypes();

    @GET("api/festivals")
    Call<List<Festival>> getAllFestivals();

    @GET("api/festivals/festival-type/{id}")
    Call<List<Festival>> getAllFestivalsByType(@Path("id")int id);
}
