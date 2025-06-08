package ru.practicum.android.diploma.search.data.repository

import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.repository.VacanciesGetter

class VacanciesGetterImpl : VacanciesGetter {
    override fun getVacancies(filter: Filter): List<Vacancy> {
        TODO("Not yet implemented")
    }
}
