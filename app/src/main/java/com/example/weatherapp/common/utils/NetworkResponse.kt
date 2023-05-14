package com.example.weatherapp.common.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String = "",
    val statusCode: Int? = null
) {

    class Success<T>(data: T) : NetworkResponse<T>(data)

    class Error<T>(message: String, data: T? = null) :
        NetworkResponse<T>(message = message, data = data)

    class Loading<T> : NetworkResponse<T>()
}