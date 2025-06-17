package ru.practicum.android.diploma.search.data.source.remote

import ru.practicum.android.diploma.search.data.model.HHApiResponse

interface NetworkClient {
    suspend fun doRequest(request: Any): HHApiResponse
}
