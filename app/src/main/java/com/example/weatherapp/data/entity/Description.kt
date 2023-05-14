package com.example.weatherapp.data.entity

import com.example.weatherapp.common.utils.ExtentionFunction.capitalizeWords

data class Description(
    val mainTitle: String,
    val mainDesc: String,
    val weatherIcon: String
) {
    override fun toString(): String {
        return mainDesc.capitalizeWords()
    }
}