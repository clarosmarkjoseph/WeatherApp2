package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.entity.WeatherItem

interface ManageWeatherHistory {

    suspend fun saveItem(
        description: String?,
        temperature: String?,
        icon: String?,
        dateTimeUpdated: Long?,
    )

    suspend fun getHistoryList(): List<WeatherItem>
}