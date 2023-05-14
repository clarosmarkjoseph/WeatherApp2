package com.example.weatherapp.data.usecase

import com.example.weatherapp.common.constants.Constants
import com.example.weatherapp.common.utils.UtilityHelper
import com.example.weatherapp.data.dao.WeatherHistoryDao
import com.example.weatherapp.data.entity.WeatherItem
import com.example.weatherapp.data.entity.db.WeatherHistoryEntity
import com.example.weatherapp.domain.usecase.ManageWeatherHistory
import javax.inject.Inject

class ManageWeatherHistoryImpl @Inject constructor(
    private val weatherHistoryDao: WeatherHistoryDao,
    private val utilityHelper: UtilityHelper
) : ManageWeatherHistory {

    override suspend fun saveItem(
        description: String?,
        temperature: String?,
        icon: String?,
        dateTimeUpdated: Long?,
    ) {
        weatherHistoryDao.insertData(
            WeatherHistoryEntity(
                description = description,
                temperature = temperature,
                icon = icon,
                dateTimeStamp = dateTimeUpdated,
            )
        )
    }

    override suspend fun getHistoryList(): List<WeatherItem> {
        return weatherHistoryDao.getHistory().map {
            WeatherItem(
                description = it.description,
                temperature = it.temperature,
                icon = it.icon,
                dateUpdated = utilityHelper.convertUnixToTime(
                    it.dateTimeStamp,
                    Constants.MMMM_DD_YYYY_hh_mm_a
                ),
            )
        }
    }

}