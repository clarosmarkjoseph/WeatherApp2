package com.example.weatherapp.data.usecase

import com.example.weatherapp.common.constants.Constants
import com.example.weatherapp.common.utils.UtilityHelper
import com.example.weatherapp.domain.repository.GetWeatherInfo
import com.example.weatherapp.domain.usecase.ManageWeatherUsecase
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ManageWeatherUsecaseImplTest {

    private val getWeatherInfo: GetWeatherInfo = mockk(relaxed = true)
    private val utilityHelper: UtilityHelper = mockk(relaxed = true)
    private lateinit var manageWeatherHistory: ManageWeatherUsecase

    @Before
    fun setUp() {
        mockkStatic(Constants::class)
        manageWeatherHistory = ManageWeatherUsecaseImpl(
            getWeatherInfo, utilityHelper
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get report should get error`(): Unit = runBlocking {
//        val mockReponse = mockk<CurrentWeatherResponse>(relaxed = true) {
//            every { base } returns ""
//            every { clouds } returns mockk("")
//            every { cod } returns 0
//            every { coord } returns mockk(relaxed = true)
//            every { dateTimeUpdated } returns 0L
//            every { main } returns mockk {
//                every { feels_like } returns 0.0
//                every { temp } returns 0.0
//                every { temp_max } returns 0.0
//                every { temp_min } returns 0.0
//                every { humidity } returns 0
//            }
//            every { rain } returns mockk() {
//                every { rainInOneHr } returns 0.0
//                every { rainInThreeHrs } returns 0.0
//            }
//            every { sys } returns mockk() {
//                every { country } returns ""
//                every { sunrise } returns 0L
//                every { sunset } returns 0L
//            }
//            every { timezone } returns 0L
//            every { weather } returns listOf()
//            every { clouds } returns mockk {
//                every { all } returns 0
//            }
//        }
//        coEvery { getWeatherInfo.getWeatherReport(any(), any()) } returns mockReponse
//        every { utilityHelper.convertUnixToTime(any()) } returns ""
//        every { utilityHelper.calculateWind(any()) } returns ""
//        every { utilityHelper.convertToKm(any()) } returns ""
//        every { utilityHelper.convertKelvinToCelsius(any()) } returns ""
//
//        val expectedResponse = mockk<CurrentWeather> {
//            every { temperature } returns mockk(relaxed = true) {
//                every { humidity } returns ""
////                every {  }
//            }
//            every { description } returns mockk(relaxed = true)
//            every { visibility } returns ""
//            every { temperature } returns mockk(relaxed = true)
//            every { cloudPercent } returns "%"
//            every { cityName } returns ""
//            every { timeZone } returns 0L
//            every { rain } returns mockk(relaxed = true)
//            every { sunRiseAndSet } returns mockk(relaxed = true)
//            every { dateTimeUpdated } returns 0L
//        }
//        val data = manageWeatherHistory.getWeatherReport(0.0, 0.0).collect().
//
//        data.first()
//        coVerify {
//            getWeatherInfo.getWeatherReport(0.0, 0.0)
//        }
//        assert( data.first().data == expectedResponse)
//        assert(data.drop().first().data)
    }

}