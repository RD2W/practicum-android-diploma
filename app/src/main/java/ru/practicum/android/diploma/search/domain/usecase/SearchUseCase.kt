package ru.practicum.android.diploma.search.domain.usecase

import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

/**
 * Кейс для осуществления поискового запроса с переданными параметрами
 */
class SearchUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(searchParameters: SearchParameters) = repository.searchVacancies(searchParameters)
}
