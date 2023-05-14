package com.example.weatherapp.data.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val base: String?,
    val clouds: Clouds?,
    val cod: Int?,
    val coord: Coord?,
    @SerializedName("dt")
    val dateTimeUpdated: Long?,
    val id: Int?,
    val main: Main?,
    val name: String?,
    val rain: Rain?,
    val sys: Sys?,
    val timezone: Long?,
    val visibility: Int?,
    val weather: List<Weather>,
    val wind: Wind
)