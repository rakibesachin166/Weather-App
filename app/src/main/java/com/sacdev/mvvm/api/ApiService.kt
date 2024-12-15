package com.sacdev.mvvm.api


import com.sacdev.mvvm.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current")
    suspend fun getTodayWeather(
        @Query("access_key") accessKey: String,
        @Query("query") city: String
    ): Response<WeatherResponse>

}
