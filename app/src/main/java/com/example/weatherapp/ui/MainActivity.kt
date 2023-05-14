package com.example.weatherapp.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.common.constants.Constants
import com.example.weatherapp.ui.screens.AppSurface
import com.example.weatherapp.ui.screens.ShowErrorMessage
import com.example.weatherapp.ui.screens.findActivity
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.viewmodel.CurrentWeatherViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            WeatherAppTheme {
                // Location permission state
                val multiplePermissionsState = rememberMultiplePermissionsState(
                    listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
                AppSurface {
                    AskLocationPermission(multiplePermissionsState = multiplePermissionsState)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AskLocationPermission(multiplePermissionsState: MultiplePermissionsState) {
    LaunchedEffect(multiplePermissionsState) {
        multiplePermissionsState.launchMultiplePermissionRequest()
    }

    when {
        multiplePermissionsState.allPermissionsGranted -> {
            // Permission Granted
            MainScreen()
        }
        multiplePermissionsState.shouldShowRationale -> {
//            Text(text = "shouldShowRationale", color = Color.White)
            LaunchedEffect(key1 = multiplePermissionsState) {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        }
        multiplePermissionsState.revokedPermissions.isNotEmpty() -> {
            // Denied some permission
            ShowErrorMessage(Constants.PERMISSION_DENIED_MESSAGE)
        }
//        else -> {
//            LaunchedEffect(multiplePermissionsState) {
//                multiplePermissionsState.launchMultiplePermissionRequest()
//            }
//        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainScreen() {

    val tabList = CurrentWeatherViewModel.tabRowItems
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    BackHandler {
        context.findActivity()?.finish()
    }

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPosition ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPosition),
                color = Color.White
            )
        }
    ) {
        // Add content
        tabList.forEachIndexed { index, tabRowItem ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, icon = {
                    Icon(
                        imageVector = tabRowItem.icon,
                        contentDescription = tabRowItem.title
                    )
                }, text = {
                    Text(text = tabRowItem.title)
                }
            )
        }
    }
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        count = tabList.size,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) {

        CurrentWeatherViewModel.tabRowItems[it].screen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        AppSurface {
            MainScreen()
        }
    }
}