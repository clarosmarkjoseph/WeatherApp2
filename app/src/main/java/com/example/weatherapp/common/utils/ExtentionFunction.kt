package com.example.weatherapp.common.utils

import java.util.*

object ExtentionFunction {
    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.capitalize(Locale.ROOT) }
}