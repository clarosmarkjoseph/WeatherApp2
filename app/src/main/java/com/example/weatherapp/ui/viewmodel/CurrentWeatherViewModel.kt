package com.example.weatherapp.ui.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.common.constants.Constants
import com.example.weatherapp.common.utils.ExtentionFunction.capitalizeWords
import com.example.weatherapp.common.utils.NetworkResponse
import com.example.weatherapp.common.utils.PermissionHandler
import com.example.weatherapp.domain.usecase.ManageWeatherHistory
import com.example.weatherapp.domain.usecase.ManageWeatherUsecase
import com.example.weatherapp.ui.screens.CurrentWeatherScreen
import com.example.weatherapp.ui.screens.WeatherHistoryScreen
import com.example.weatherapp.ui.uistate.CurrentWeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val manageWeatherUsecase: ManageWeatherUsecase,
    private val permissionHandler: PermissionHandler,
    private val manageWeatherHistory: ManageWeatherHistory
) : ViewModel() {

    private val _uiState = MutableStateFlow<CurrentWeatherState?>(null)
    val uiState: StateFlow<CurrentWeatherState?> get() = _uiState

    init {
        getCurrentLocation()
        BuildConfig.APPLICATION_ID
    }

    private fun getCurrentLocation() {
        val task = permissionHandler.getCurrentLocation()
        if (task != null) {
            _uiState.value = CurrentWeatherState.OnLoading
            // TODO rewrite this
            task.addOnSuccessListener {
                getCurrentWeather(it.latitude, it.longitude)
            }.addOnFailureListener {
                _uiState.value = CurrentWeatherState.OnError(Constants.FAILED_FETCH_USER_LOCATION)
            }
        } else {
            _uiState.value = CurrentWeatherState.OnError(Constants.PERMISSION_DENIED_MESSAGE)
        }
    }

    private fun getCurrentWeather(lat: Double, lng: Double) {
        viewModelScope.launch {
            manageWeatherUsecase.getWeatherReport(lat, lng).collectLatest {
                when (val response = it) {
                    is NetworkResponse.Success -> {
                        manageWeatherHistory.saveItem(
                            description = response.data?.description?.mainDesc?.capitalizeWords(),
                            temperature = response.data?.temperature?.temp,
                            icon = response.data?.description?.weatherIcon,
                            dateTimeUpdated = response.data?.dateTimeUpdated
                        )
                        _uiState.value = CurrentWeatherState.OnDisplayData(response.data)
                    }
                    is NetworkResponse.Error -> {
                        _uiState.value = CurrentWeatherState.OnError(response.message)
                    }
                    else -> {
                        _uiState.value = CurrentWeatherState.OnLoading
                    }
                }
            }
        }
    }

    companion object {
        data class TabRowItem(
            val title: String,
            val screen: @Composable () -> Unit,
            val icon: ImageVector
        )

        val tabRowItems = listOf(
            TabRowItem(
                title = "Home",
                screen = { CurrentWeatherScreen() },
                icon = Icons.Rounded.Home,
            ),
            TabRowItem(
                title = "History",
                screen = { WeatherHistoryScreen() },
                icon = Icons.Rounded.List,
            ),
        )
    }

}