package com.example.festivalapp.Retrofit;

import com.example.festivalapp.Models.Attend;
import com.example.festivalapp.Models.ChangePassword;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.Models.FestivalType;
import com.example.festivalapp.Models.User;
import com.example.festivalapp.Models.UserFestivalType;
import com.example.festivalapp.Models.UserUpdate;

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

    @GET("api/festivals/{id}")
    Call<Festival> getFestival(@Path("id")int id);

    @GET("api/users/username")
    Call<User> getUser(@Query("username")String username);

    @GET("api/festivals/festival-type/{id}")
    Call<List<Festival>> getAllFestivalsByType(@Path("id")int id);

    @DELETE("api/festivals/{id}")
    Call<ResponseBody> deleteFestival(@Path("id")int id);

    @POST("api/festival-types/add-favorite")
    Call<ResponseBody> createFavorite(@Body UserFestivalType userFestivalTypeDTO);

    @POST("api/festivals")
    Call<ResponseBody> createFestival(@Body Festival festivalDTO);

    @POST("api/users")
    Call<ResponseBody> createUser(@Body User userDTO);

    @PUT("api/festivals")
    Call<ResponseBody> updateFestival(@Body Festival festivalDTO);

    @GET("api/users/login/")
    Call<Boolean> userLogin(@Query("username")String username, @Query("password")String password);

    @GET("api/festival-types/favorites")
    Call<List<FestivalType>> getAllFavoriteFestivalTypes(@Query("username")String username);

    @POST("api/festival-types/delete-favorite")
    Call<ResponseBody> removeFavorite(@Body UserFestivalType userFestivalTypeDTO);

    @PUT("api/users/change-password")
    Call<ResponseBody> changePassword(@Body ChangePassword changePasswordDTO);

    @PUT("api/users")
    Call<ResponseBody> updateUser(@Body UserUpdate userUpdateDTO);

    @POST("api/festivals/attend")
    Call<ResponseBody> attendFestival(@Body Attend attendDTO);

    @PUT("api/festivals/rate")
    Call<ResponseBody> rateFestival(@Query("rate")float rate, @Query("festivalId")int festivalId, @Query("username")String username);
}
