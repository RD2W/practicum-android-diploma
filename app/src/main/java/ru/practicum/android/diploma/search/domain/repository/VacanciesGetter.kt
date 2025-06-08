package ru.practicum.android.diploma.search.domain.repository

import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.filter.domain.model.Filter

interface VacanciesGetter {
    fun getVacancies(
        filter: Filter
    ): List<Vacancy>
}
