package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.dto.CurrentWeatherResponse

interface GetWeatherInfo {

    suspend fun getWeatherReport(
        lat: Double,
        lng: Double
    ): CurrentWeatherResponse?
}