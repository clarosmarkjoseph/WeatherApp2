package com.example.weatherapp.data.repository

import com.example.weatherapp.common.constants.WeatherMapApiConstants
import com.example.weatherapp.data.dto.CurrentWeatherResponse
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.GetWeatherInfo
import javax.inject.Inject

class GetWeatherInfoImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : GetWeatherInfo {

    override suspend fun getWeatherReport(
        lat: Double,
        lng: Double
    ): CurrentWeatherResponse? {
        val apiKey = WeatherMapApiConstants.API_ID
        return weatherApi.getCurrentWeather(lat = lat, lng = lng, appid = apiKey)
    }

}