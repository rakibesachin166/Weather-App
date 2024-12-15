package com.sacdev.mvvm.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.sacdev.mvvm.R
import com.sacdev.mvvm.databinding.ActivityWeatherBinding
import com.sacdev.mvvm.response.ApiResult
import com.sacdev.mvvm.response.WeatherResponse
import com.sacdev.mvvm.utils.SharedPrefManager
import com.sacdev.mvvm.utils.isNetworkAvailable
import com.sacdev.mvvm.utils.makeVisible
import com.sacdev.mvvm.utils.showToast
import com.sacdev.mvvm.viewModels.WeatherViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private val weatherViewModel: WeatherViewModel by viewModel()
    private val sharedPrefManager: SharedPrefManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeWeatherData()


        val savedWeatherData = sharedPrefManager.getWeatherData()

        if (isNetworkAvailable()) {
            if (savedWeatherData != null) {

                binding.cityEditId.setText(savedWeatherData.location.name)
                weatherViewModel.fetchWeather(savedWeatherData.location.name)
            } else {
                changeNoDataAvailableVisibility(
                    false, "Please search for a city to see its weather information.", false
                )

            }

        }
        else {
            if (savedWeatherData != null) {
                showToast("You are currently offline. Displaying cached weather data.")
                changeNoDataAvailableVisibility(true, "", false)
                setWeatherData(savedWeatherData)
            }
            else {
                changeNoDataAvailableVisibility(
                    false, "Please search for a city to see its weather information.", false
                )
            }
        }

        binding.cityEditId.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                startWeatherSearch()
                false
            } else {
                false
            }
        }
        binding.searchBtnId.setOnClickListener {

            startWeatherSearch()

        }
        binding.retryButton.setOnClickListener {
            startWeatherSearch()
        }

    }

    private fun startWeatherSearch() {
        val cityName = binding.cityEditId.text.toString()

        if (cityName.isEmpty()) {
            binding.cityEditId.requestFocus()
            binding.cityEditId.error = "Please Enter City Name"
            return
        }

        weatherViewModel.fetchWeather(cityName)
    }


    private fun observeWeatherData() {

        weatherViewModel.weatherState.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.progressBar.makeVisible(true)
                }

                is ApiResult.Success -> {
                    sharedPrefManager.saveWeatherData(result.data)
                    binding.progressBar.makeVisible(false)
                    setWeatherData(result.data)
                }

                is ApiResult.Error ->
                    {
                    binding.progressBar.makeVisible(false)
                    changeNoDataAvailableVisibility(
                        false,
                        result.message,
                        true
                    )
                }

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherData(data: WeatherResponse) {
        changeNoDataAvailableVisibility(true, "", false)

        binding.dateTxt.text = data.location.localtime
        binding.cityEditId.setText(data.location.name)
        when (data.current.weatherDescriptions[0]) {
            "Haze" -> binding.weatherDescriptionIcon.setImageResource(R.drawable.icon_cloudy)
            "Sunny" -> binding.weatherDescriptionIcon.setImageResource(R.drawable.icon_sunny)
            "Overcast" -> binding.weatherDescriptionIcon.setImageResource(R.drawable.icon_snowy)
            "Rainy" -> binding.weatherDescriptionIcon.setImageResource(R.drawable.icon_rainy)
            else -> binding.weatherDescriptionIcon.setImageResource(R.drawable.icon_normals)
        }

        binding.weatherDescriptionTittleTxt.text = data.current.weatherDescriptions[0]
        binding.tempratureTxt.text = data.current.temperature.toString() + "\u00B0C"
        binding.humidityTxt.text = data.current.humidity.toString()
        binding.windSpeed.text = data.current.windSpeed.toString()
    }

    private fun changeNoDataAvailableVisibility(
        isDataAvailable: Boolean,
        message: String,
        isRetryButtonVisible: Boolean = false
    ) {
        binding.noDataLy.makeVisible(!isDataAvailable)
        binding.dataLy.makeVisible(isDataAvailable)
        binding.messageTxt.text = message

        binding.errorImage.makeVisible(isRetryButtonVisible)
        binding.oppsTxtId.makeVisible(isRetryButtonVisible)
        binding.retryButton.makeVisible(isRetryButtonVisible)
    }
}