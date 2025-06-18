package ru.practicum.android.diploma.search.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.search.data.mapper.toMap
import ru.practicum.android.diploma.search.data.mapper.toSearchResult
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import java.net.HttpURLConnection

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
) : SearchRepository {
    override suspend fun searchVacancies(searchParameters: SearchParameters): Flow<Result<SearchResult>> = flow {
        val request = HHApiRequest.Vacancies(searchParameters.toMap())
        when (val response = networkClient.doRequest(request)) {
            is HHApiResponse.Vacancies -> {
                emit(Result.Success(response.toSearchResult()))
            }

            is HHApiResponse.BadResponse -> {
                val result = when (response.responseCode) {
                    HttpURLConnection.HTTP_NOT_FOUND -> Result.NotFound
                    RetrofitClient.NETWORK_ERROR -> Result.NoInternet
                    else -> Result.ServerError(
                        Exception(response.errorMessage.takeIf { it.isNotBlank() } ?: "Server error")
                    )
                }
                emit(result)
            }

            else -> {
                emit(Result.ServerError(IllegalStateException("Unexpected error")))
            }
        }
    }
}
