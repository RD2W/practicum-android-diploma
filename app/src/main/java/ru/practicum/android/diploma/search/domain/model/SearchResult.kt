package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.common.domain.model.Vacancy

/**
 * Модель возвращаемых данных после осуществления поискового запроса
 */
data class SearchResult(
    val resultsFound: Int,
    val totalPages: Int,
    val currentPage: Int,
    val vacancies: List<Vacancy>,
)
