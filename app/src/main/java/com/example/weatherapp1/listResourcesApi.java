package com.example.weatherapp1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface listResourcesApi {

    @GET("weather?q={City}&units=metric&appid={AppID}")
    Call<MainWeatherClass> getMainWeatherClassParam(
            @Path("City") String City,
            @Path("AppId") String AppId);

    @GET("weather?q=London&units=metric&appid=b60c7e86a1a0721e4f380436455f7f25")
    Call<MainWeatherClass> getMainWeatherClass();
}