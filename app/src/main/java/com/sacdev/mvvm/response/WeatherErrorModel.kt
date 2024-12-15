package com.sacdev.mvvm.response

import com.google.gson.annotations.SerializedName



data class WeatherErrorModel(
    @SerializedName("code") val code: Int,
    @SerializedName("type") val type: String,
    @SerializedName("info") val info: String
)