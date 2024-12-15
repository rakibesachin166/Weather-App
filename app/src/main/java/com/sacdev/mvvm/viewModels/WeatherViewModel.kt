package com.sacdev.mvvm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sacdev.mvvm.repository.WeatherRepository
import com.sacdev.mvvm.response.ApiResult
import com.sacdev.mvvm.response.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository : WeatherRepository ) :ViewModel() {
     val weatherState = MutableLiveData<ApiResult<WeatherResponse>>()
    fun fetchWeather( city :String ) {


        viewModelScope.launch {
            weatherState.value = ApiResult.Loading
            val result = weatherRepository.getWeather(city)
            weatherState.value = result
        }
    }
}