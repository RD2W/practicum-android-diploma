package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Resource
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult

/**
 * Интерфейс репозитория для осуществления поисковых запросов
 */
interface SearchRepository {
    suspend fun searchVacancies(searchParameters: SearchParameters): Flow<Resource<SearchResult>>
}
