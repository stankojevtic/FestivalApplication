package com.example.festivalapp.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

//    public static final String  = "http://192.168.1.108:8080/api/";
//    public static final String USERS = "/users/";

    private static Retrofit retrofit;

    private static final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    private static final String BASE_URL = "http://192.168.0.17/";

    //private static final String BASE_URL = "http://10.0.2.2:3000/";


    public static Retrofit getInstance() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(httpLoggingInterceptor);
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.writeTimeout(120, TimeUnit.SECONDS);

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
