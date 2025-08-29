package com.ad_coding.plannettestapp.domain.util

sealed class DataResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : DataResult<T>(data)
    class Error<T>(error: String) : DataResult<T>(error = error)
}