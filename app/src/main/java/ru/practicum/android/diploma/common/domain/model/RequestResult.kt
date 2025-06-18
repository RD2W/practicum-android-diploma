package ru.practicum.android.diploma.common.domain.model

sealed class RequestResult<T>(val searchResult: T? = null, val errorType: Int? = null) {
    class Success<T>(searchResult: T) : RequestResult<T>(searchResult, null)
    class Error<T>(errorType: Int) : RequestResult<T>(null, errorType)
}
