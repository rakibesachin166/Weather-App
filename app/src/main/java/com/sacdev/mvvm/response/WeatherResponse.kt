package com.sacdev.mvvm.response

import com.google.gson.annotations.SerializedName
import com.sacdev.mvvm.models.Current
import com.sacdev.mvvm.models.Location
import com.sacdev.mvvm.models.Request


data class WeatherResponse(
    @SerializedName("success")
    val success: Boolean? = true,
    @SerializedName("request") val request: Request,
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current,
    @SerializedName("error") val errorModel: WeatherErrorModel?,
)




