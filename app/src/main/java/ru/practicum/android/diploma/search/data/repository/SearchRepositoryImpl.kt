package ru.practicum.android.diploma.search.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.search.data.mapper.toMap
import ru.practicum.android.diploma.search.data.mapper.toSearchResult
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
) : SearchRepository {
    override suspend fun searchVacancies(searchParameters: SearchParameters): Flow<RequestResult<SearchResult>> = flow {
        val request = HHApiRequest.Vacancies(searchParameters.toMap())
        when (val response = networkClient.doRequest(request)) {
            is HHApiResponse.Vacancies -> {
                emit(RequestResult.Success(response.toSearchResult()))
            }

            else -> {
                emit(RequestResult.Error(response.responseCode))
            }
        }
    }
}
