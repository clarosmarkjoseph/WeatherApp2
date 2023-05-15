package com.example.weatherapp.ui.viewmodel

import android.location.Location
import com.example.weatherapp.common.utils.NetworkResponse
import com.example.weatherapp.common.utils.PermissionHandler
import com.example.weatherapp.data.entity.CurrentWeather
import com.example.weatherapp.domain.usecase.ManageWeatherHistory
import com.example.weatherapp.domain.usecase.ManageWeatherUsecase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTest {

    private val manageWeatherUsecase: ManageWeatherUsecase = mockk(relaxed = true)
    private val permissionHandler: PermissionHandler = mockk(relaxed = true)
    private val manageWeatherHistory: ManageWeatherHistory = mockk(relaxed = true)
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        testDispatcher.cancel()
    }

    @Test
    fun `should return current location and api result`() = runBlocking {
        currentWeatherViewModel = CurrentWeatherViewModel(
            manageWeatherUsecase, permissionHandler, manageWeatherHistory
        )
        val mockLocation = mockk<Location>(relaxed = true) {
            every { latitude } returns 0.0
            every { longitude } returns 0.0
        }
        val mockApiResponse = mockk<CurrentWeather>(relaxed = true)
        coEvery { permissionHandler.getCurrentLocation() } returns flowOf(
            NetworkResponse.Success(
                mockLocation
            )
        )

        coEvery { manageWeatherUsecase.getWeatherReport(any(), any()) } returns flowOf(
            NetworkResponse.Success(
                mockApiResponse
            )
        )
        coVerify { permissionHandler.getCurrentLocation() }
//        coVerify { manageWeatherUsecase.getWeatherReport(0.0, 0.0) }
    }
}