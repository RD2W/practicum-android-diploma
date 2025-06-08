package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.model.SearchVacanciesResult

interface VacanciesGetter {
    fun getVacancies(
        vacancyExpression: String,
        filter: Filter
    ): Flow<SearchVacanciesResult>
}
