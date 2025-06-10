package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.search.domain.repository.VacanciesGetter

class GetVacanciesUseCase(private val vacanciesGetter: VacanciesGetter) {
    fun execute(
        vacancyExpression: String,
        filter: Filter
    ): Flow<SearchVacanciesResult> {
        return vacanciesGetter.getVacancies(vacancyExpression, filter)
    }
}
