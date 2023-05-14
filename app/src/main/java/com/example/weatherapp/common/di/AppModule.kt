package com.example.weatherapp.common.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.common.constants.Constants
import com.example.weatherapp.common.db.AppDatabase
import com.example.weatherapp.common.utils.UtilityHelper
import com.example.weatherapp.data.dao.WeatherHistoryDao
import com.example.weatherapp.data.dao.WeatherHistoryDao_Impl
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.GetWeatherInfoImpl
import com.example.weatherapp.data.usecase.ManageWeatherHistoryImpl
import com.example.weatherapp.data.usecase.ManageWeatherUsecaseImpl
import com.example.weatherapp.domain.repository.GetWeatherInfo
import com.example.weatherapp.domain.usecase.ManageWeatherHistory
import com.example.weatherapp.domain.usecase.ManageWeatherUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("okHttp:", message)
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkhttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpBuilder
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "weather_db"
        ).build()
    }

    @Provides
    fun provideDao(appDatabase: AppDatabase): WeatherHistoryDao {
        return WeatherHistoryDao_Impl(appDatabase)
    }

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    fun provideWeatherInfo(weatherApi: WeatherApi): GetWeatherInfo {
        return GetWeatherInfoImpl(
            weatherApi = weatherApi
        )
    }

    @Provides
    fun provideManageWeatherHistory(
        weatherHistoryDao: WeatherHistoryDao,
        utilityHelper: UtilityHelper
    ): ManageWeatherHistory {
        return ManageWeatherHistoryImpl(
            weatherHistoryDao,
            utilityHelper
        )
    }

    @Provides
    fun provideUtilityHelper(): UtilityHelper {
        return UtilityHelper()
    }

    @Provides
    fun provideManageWeatherUsecase(
        getWeatherInfo: GetWeatherInfo,
        utilityHelper: UtilityHelper,
    ): ManageWeatherUsecase {
        return ManageWeatherUsecaseImpl(
            getWeatherInfo = getWeatherInfo,
            utilityHelper = utilityHelper
        )
    }

}