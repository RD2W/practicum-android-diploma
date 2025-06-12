package ru.practicum.android.diploma.search.data.source.remote

interface NetworkClient {
    suspend fun doRequest(request: Any): HHApiResponse
}
