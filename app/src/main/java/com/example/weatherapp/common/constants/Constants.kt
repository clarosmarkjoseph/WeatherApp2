package com.example.weatherapp.common.constants

import java.util.*

object Constants {

    const val BASE_URL = "https://api.openweathermap.org"
    const val BASE_ICON_URL = "https://openweathermap.org/img/wn"

    const val PERMISSION_DENIED_MESSAGE = "Permission Denied. Please allow us to get your current location in order to use this feature"
    const val NO_CONNECTION_MESSAGE = "There's something wrong with the response. Please check your internet connection and try again"
    const val FAILED_FETCH_USER_LOCATION = "Failed to get the current location. Please restart the app"

    const val MMMM_DD_YYYY_hh_mm_a = "MMMM dd, yyyy hh:mm a"
    const val HH_MM_A = "hh:mm a"

//    fun String.capitalizeWords(): String =
//        split(" ").joinToString(" ") { it.capitalize(Locale.ROOT) }
}