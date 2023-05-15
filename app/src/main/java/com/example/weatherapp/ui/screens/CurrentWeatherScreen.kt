package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weatherapp.data.entity.CurrentWeather
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.uistate.CurrentWeatherState
import com.example.weatherapp.ui.viewmodel.CurrentWeatherViewModel


@Composable
fun CurrentWeatherScreen(
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    when (val result = uiState) {
        is CurrentWeatherState.OnDisplayData -> {
            ShowWeatherScreen(result.data)
        }
        is CurrentWeatherState.OnError -> {
            // Show error
            ShowErrorMessage(
                text = result.message,
                actionMessage = "Refresh"
            ) {
                viewModel.getCurrentLocation()
            }
        }
        is CurrentWeatherState.OnLoading -> {
            ShowLoading()
        }
        else -> {
//            ShowErrorMessage(text = result.message)
        }
    }
}

@Composable
private fun ShowWeatherScreen(result: CurrentWeather?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .weight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val desc = result?.description.toString()
                Text(
                    text = result?.temperature?.temp ?: "",
                    style = MaterialTheme.typography.h3,
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.h5,
                )
            }
            result?.description?.let {
                println("weather icon: ${it.weatherIcon}")
                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .weight(0.4f),
                    model = it.weatherIcon,
                    contentDescription = null,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            val locationName = "${result?.cityName}, ${result?.sunRiseAndSet?.country}"
            Text(
                text = locationName,
                style = MaterialTheme.typography.subtitle1,
            )
            Icon(
                modifier = Modifier.height(15.dp),
                imageVector = Icons.Default.LocationOn,
                contentDescription = "",
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            val tempFeelsLike = "Feels like ${result?.temperature?.temp_feels_like}"
            Text(
                text = tempFeelsLike,
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 4.dp,
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val highLow =
                        "${result?.temperature?.min_temp ?: "--"}/${result?.temperature?.max_temp ?: "--"}"
                    Text(
                        text = "High/Low",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = highLow,
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))

            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 4.dp,
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Humidity",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = result?.temperature?.humidity ?: "",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 4.dp,
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sunrise",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = result?.sunRiseAndSet?.sunRise ?: "",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 4.dp,
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sunset",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = result?.sunRiseAndSet?.sunSet ?: "",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 4.dp,
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Cloudiness",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = result?.cloudPercent ?: "",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 4.dp,
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Visibility",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = result?.visibility ?: "",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                shape = RoundedCornerShape(6.dp),
                elevation = 4.dp,
                modifier = Modifier.weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Wind",
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = result?.windSpeed ?: "",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }

            result?.rain?.let {
                if (it.rainInOneHr?.isNotEmpty() == true) {

                    Spacer(modifier = Modifier.width(10.dp))

                    Card(
                        shape = RoundedCornerShape(6.dp),
                        elevation = 4.dp,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Rain",
                                style = MaterialTheme.typography.h6,
                            )
                            Text(
                                text = it.rainInOneHr,
                                style = MaterialTheme.typography.subtitle1,
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrentWeatherScreen() {
    WeatherAppTheme {
        CurrentWeatherScreen()
    }
}