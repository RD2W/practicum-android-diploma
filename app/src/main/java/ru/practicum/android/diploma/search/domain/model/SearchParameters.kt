package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.filter.domain.model.Filter

/**
 * Дата класс для передачи параметров поиска с главной страницы
 */
data class SearchParameters(
    val query: String, // Поисковый запрос
    val page: Int = 0, // Номер страницы
    val filter: Filter? = null, // Фильтр поиска
)
