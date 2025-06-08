package ru.practicum.android.diploma.search.domain.usecase

import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.repository.VacanciesGetter

class GetVacanciesUseCase(private val vacanciesGetter: VacanciesGetter) {
    fun execute(
        filter: Filter
    ): List<Vacancy> {
        return vacanciesGetter.getVacancies(filter)
    }
}
