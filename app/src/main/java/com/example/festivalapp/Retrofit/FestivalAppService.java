package com.example.festivalapp.Retrofit;

import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Models.FestivalType;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FestivalAppService {

    @GET("api/festival-types/")
    Call<List<FestivalType>> getAllFestivalTypes();

    @GET("api/festivals")
    Call<List<Festival>> getAllFestivals();

    @GET("api/festivals/festival-type/{id}")
    Call<List<Festival>> getAllFestivalsByType(@Path("id")int id);

    @DELETE("api/festivals/{id}")
    Call<ResponseBody> deleteFestival(@Path("id")int id);

    @POST("api/festivals")
    Call<ResponseBody> createFestival(@Body Festival festivalDTO);

    @PUT("api/festivals")
    Call<ResponseBody> updateFestival(@Body Festival festivalDTO);

    @GET("api/users/login/")
    Call<Boolean> userLogin(@Query("username")String username, @Query("password")String password);
}
