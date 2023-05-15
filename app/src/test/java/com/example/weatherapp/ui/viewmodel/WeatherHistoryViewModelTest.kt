package com.example.weatherapp.ui.viewmodel

import com.example.weatherapp.data.entity.WeatherItem
import com.example.weatherapp.domain.usecase.ManageWeatherHistory
import com.example.weatherapp.ui.uistate.WeatherHistoryState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
//@HiltAndroidTest
class WeatherHistoryViewModelTest {

    private val manageWeatherHistory: ManageWeatherHistory = mockk(relaxed = true)
    private lateinit var weatherHistoryViewModel: WeatherHistoryViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        weatherHistoryViewModel = WeatherHistoryViewModel(manageWeatherHistory)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        testDispatcher.cancel()
    }


    @Test
    fun `get list should return empty list`(): Unit = runBlocking {
        coEvery { manageWeatherHistory.getHistoryList() } returns emptyList()
        weatherHistoryViewModel.getWeatherHistory()
        assert(weatherHistoryViewModel.historyState.value == WeatherHistoryState.OnEmpty)
    }

    @Test
    fun `get list should return 1 item list`(): Unit = runBlocking {
        val mockItem = mockk<WeatherItem>(relaxed = true)
        coEvery { manageWeatherHistory.getHistoryList() } returns listOf(mockItem)
        weatherHistoryViewModel.getWeatherHistory()
        assert(
            weatherHistoryViewModel.historyState.value == WeatherHistoryState.OnDisplayData(
                listOf(mockItem)
            )
        )
    }

}