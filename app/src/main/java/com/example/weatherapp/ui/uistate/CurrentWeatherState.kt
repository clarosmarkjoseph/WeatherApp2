package com.example.weatherapp.ui.uistate

import com.example.weatherapp.data.entity.CurrentWeather

sealed class CurrentWeatherState {
    data class OnDisplayData(val data: CurrentWeather?) : CurrentWeatherState()
    data class OnError(val message: String? = "") : CurrentWeatherState()
    object OnLoading : CurrentWeatherState()
}
