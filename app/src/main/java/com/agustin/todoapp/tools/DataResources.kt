package com.agustin.todoapp.tools

import kotlin.Exception

sealed class DataResources<T> {
    class Loading <T>: DataResources<T>()
    data class Success<T>(val data: T) : DataResources<T>()
    data class Failure<T>(val error: Exception): DataResources<T>()

}