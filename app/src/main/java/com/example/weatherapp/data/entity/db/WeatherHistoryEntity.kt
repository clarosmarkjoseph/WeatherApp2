package com.example.weatherapp.data.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history")
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "weather_desc") val description: String?,
    @ColumnInfo(name = "temperature") val temperature: String?,
    @ColumnInfo(name = "weather_icon") val icon: String?,
    @ColumnInfo(name = "date_fetched") val dateTimeStamp: Long?,
)
