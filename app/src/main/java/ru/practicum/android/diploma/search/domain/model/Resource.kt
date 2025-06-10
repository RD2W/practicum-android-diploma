package ru.practicum.android.diploma.search.domain.model

sealed class Resource<T>(val searchResult: SearchResult? = null, val errorType: Int? = null) {
    class Success<T>(searchResult: SearchResult) : Resource<T>(searchResult, null)
    class Error<T>(errorType: Int) : Resource<T>(null, errorType)
}
