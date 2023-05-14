package com.example.weatherapp.data.usecase

import com.example.weatherapp.common.utils.UtilityHelper
import com.example.weatherapp.data.dao.WeatherHistoryDao
import com.example.weatherapp.data.entity.WeatherItem
import com.example.weatherapp.data.entity.db.WeatherHistoryEntity
import com.example.weatherapp.domain.usecase.ManageWeatherHistory
import com.google.gson.Gson
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ManageWeatherHistoryImplTest {

    private val weatherHistoryDao: WeatherHistoryDao = mockk(relaxed = true)
    private val utilityHelper: UtilityHelper = mockk(relaxed = true)
    private lateinit var manageWeatherHistoryImpl: ManageWeatherHistory

    @Before
    fun setUp() {
        manageWeatherHistoryImpl = ManageWeatherHistoryImpl(
            weatherHistoryDao, utilityHelper
        )
    }

    @Test
    fun `should run saveItem in database`(): Unit = runBlocking {
        coEvery {
            weatherHistoryDao.insertData(any())
        } just runs

        manageWeatherHistoryImpl.saveItem(
            "", "", "", 0L
        )
        coVerify {
            weatherHistoryDao.insertData(any())
        }
    }

    @Test
    fun `should return 1 item list from database`(): Unit = runBlocking {
        val item = mockk<WeatherHistoryEntity>() {
            every { id } returns 1
            every { dateTimeStamp } returns 0L
            every { description } returns ""
            every { icon } returns ""
            every { temperature } returns ""
        }
        val mockkList = listOf(item)
        val expectedReturn = listOf(mockk<WeatherItem>() {
            every { dateUpdated } returns ""
            every { description } returns ""
            every { icon } returns ""
            every { temperature } returns ""
        })

        coEvery {
            weatherHistoryDao.getHistory()
        } returns mockkList

        coEvery {
            utilityHelper.convertUnixToTime(any(), any())
        } returns ""

        println("expected data: " + Gson().toJson(expectedReturn))
        println("actual data: " + Gson().toJson(manageWeatherHistoryImpl.getHistoryList()))
        val actualResult = manageWeatherHistoryImpl.getHistoryList()
        assert(actualResult == expectedReturn)
        coVerify {
            weatherHistoryDao.getHistory()
        }
    }

}