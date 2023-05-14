package com.example.weatherapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.data.entity.db.WeatherHistoryEntity

@Dao
interface WeatherHistoryDao {

    @Query("SELECT * from weather_history order by date_fetched DESC")
    suspend fun getHistory(): List<WeatherHistoryEntity>

    @Insert
    suspend fun insertData(vararg weatherHistory: WeatherHistoryEntity)

    @Delete
    suspend fun delete(weatherHistory: WeatherHistoryEntity)
}