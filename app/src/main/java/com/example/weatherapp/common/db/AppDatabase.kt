package com.example.weatherapp.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.entity.db.WeatherHistoryEntity
import com.example.weatherapp.data.dao.WeatherHistoryDao

@Database(entities = [WeatherHistoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherHistoryDao
}