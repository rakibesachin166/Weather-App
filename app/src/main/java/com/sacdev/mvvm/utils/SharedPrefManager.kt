package com.sacdev.mvvm.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sacdev.mvvm.response.WeatherResponse

class SharedPrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)


    private val gson = Gson()

    fun saveWeatherData(weatherData: WeatherResponse ) {
        val json = gson.toJson(weatherData)
        sharedPreferences.edit()
            .putString("weatherData", json)
            .apply()
    }

    fun getWeatherData(): WeatherResponse? {
        val json = sharedPreferences.getString("weatherData", null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<WeatherResponse>() {}.type)
        } else {
            null
        }
    }

    fun clearWeatherData() {
        sharedPreferences.edit().remove("weatherData").apply()
    }
}
