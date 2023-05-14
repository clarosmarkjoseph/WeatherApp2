package com.example.weatherapp.data.entity


data class CurrentWeather(
    val temperature: Temperature?,
    val description: Description?,
    val visibility: String?,
    val windSpeed: String?,
    val cloudPercent: String,
    val cityName: String?,
    val timeZone: Long?,
    val rain: Rain?,
    val sunRiseAndSet: SunRiseAndSet?,
    val dateTimeUpdated: Long?,
)
