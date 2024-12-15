package com.sacdev.mvvm.repository

import android.util.Log
import com.sacdev.mvvm.api.ApiService
import com.sacdev.mvvm.response.ApiResult
import com.sacdev.mvvm.response.WeatherResponse
import com.sacdev.mvvm.utils.getErrorMessage

class WeatherRepository(private val apiService: ApiService, private val apiKey: String) {

    suspend fun getWeather(city: String): ApiResult<WeatherResponse> {
        return try {
            val response = apiService.getTodayWeather(apiKey, city)
            if (response.isSuccessful) {
                val weatherResponse = response.body();
                if (weatherResponse != null) {
                    if (weatherResponse.success == null || weatherResponse.success == true) {
                        ApiResult.Success(weatherResponse)
                    }
                    else {
                        when (weatherResponse.errorModel?.code) {
                            615 -> {
                                ApiResult.Error("The entered city does not exist. Enter a valid city name.")
                            }
                            429 -> {
                                ApiResult.Error("To Many Request ")
                            }
                            else -> {
                                ApiResult.Error(weatherResponse.errorModel?.info.toString())
                            }
                        }
                    }
                } else {
                    ApiResult.Error("No Weather Data Found.")

                }

            } else {
                ApiResult.Error("Failed To Fetch Weather Data")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("sachin", e.message.toString())
            ApiResult.Error(e.getErrorMessage())
        }
    }

}