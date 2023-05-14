package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.usecase.ManageWeatherHistory
import com.example.weatherapp.ui.uistate.WeatherHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val manageWeatherHistory: ManageWeatherHistory
) : ViewModel() {

    private val _historyState = MutableStateFlow<WeatherHistoryState?>(null)
    val historyState: StateFlow<WeatherHistoryState?> get() = _historyState


    fun getWeatherHistory() {
        viewModelScope.launch {
            val data = manageWeatherHistory.getHistoryList()
            _historyState.value = WeatherHistoryState.OnLoading
            if (data.isNotEmpty()) {
                _historyState.value = WeatherHistoryState.OnDisplayData(data)
            } else {
                _historyState.value = WeatherHistoryState.OnEmpty
            }
//            data.collectLatest {
//
//            }
        }
    }

}