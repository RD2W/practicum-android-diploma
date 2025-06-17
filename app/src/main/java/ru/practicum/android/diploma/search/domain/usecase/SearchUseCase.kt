package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult

/**
 * Кейс для осуществления поискового запроса с переданными параметрами
 */
interface SearchUseCase {
    suspend operator fun invoke(searchParameters: SearchParameters): Flow<RequestResult<SearchResult>>
}
