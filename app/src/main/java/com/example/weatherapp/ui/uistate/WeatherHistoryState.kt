package com.example.weatherapp.ui.uistate

import com.example.weatherapp.data.entity.WeatherItem

sealed class WeatherHistoryState {

    data class OnDisplayData(val data: List<WeatherItem>) : WeatherHistoryState()
    object OnEmpty : WeatherHistoryState()
    object OnLoading : WeatherHistoryState()

}