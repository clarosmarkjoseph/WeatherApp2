package com.example.weatherapp.data.dto

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1hr")
    val rainInOneHr: Double,
    @SerializedName("3hr")
    val rainInThreeHrs: Double
)