package com.example.weatherapp.common.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int? = null
) {

    class Success<T>(data: T) : NetworkResponse<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetworkResponse<T>(data)

    class Loading<T> : NetworkResponse<T>()
}