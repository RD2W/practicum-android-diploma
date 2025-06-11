package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Resource
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Интерфейс репозитория для осуществления поисковых запросов
 */
interface SearchRepository {
    suspend fun searchVacancies(searchParameters: SearchParameters): Flow<Resource<SearchResult>>
    suspend fun getVacancyDetails(vacancyId: String): Flow<Resource<VacancyDetails>>
}
