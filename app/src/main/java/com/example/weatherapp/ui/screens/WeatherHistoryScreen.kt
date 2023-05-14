package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.uistate.WeatherHistoryState
import com.example.weatherapp.ui.viewmodel.WeatherHistoryViewModel

@Composable
fun WeatherHistoryScreen(
    viewModel: WeatherHistoryViewModel = hiltViewModel()
) {
    val result by viewModel.historyState.collectAsState()
    LaunchedEffect(key1 = viewModel) {
        viewModel.getWeatherHistory()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Weather History",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        when (val data = result) {
            is WeatherHistoryState.OnEmpty -> {
                ShowEmptyList()
            }
            is WeatherHistoryState.OnDisplayData -> {
                val list = data.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    userScrollEnabled = true
                ) {

                    items(list) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Column(
                                    modifier = Modifier.weight(0.7f)
                                ) {
                                    Text(
                                        text = it.temperature ?: "",
                                        style = MaterialTheme.typography.h5,
                                    )
                                    Text(
                                        text = it.description ?: "",
                                        style = MaterialTheme.typography.body1,
                                    )
                                    Text(
                                        text = it.dateUpdated ?: "",
                                        style = MaterialTheme.typography.subtitle1,
                                    )
                                }
                                it.icon?.let { icon ->
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .weight(0.3f),
                                        model = icon,
                                        contentDescription = null,
                                    )
                                }
                            }
                            Divider(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .height(1.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
            else -> ShowLoading()
        }
    }
}


@Composable
private fun ShowEmptyList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "List is Empty",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherHistoryScreen() {
    WeatherAppTheme {
        WeatherHistoryScreen()
    }
}