package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.common.domain.model.Vacancy

sealed interface SearchVacanciesResult {
    data class Success(val vacancies: List<Vacancy>) : SearchVacanciesResult
    object NoInternet : SearchVacanciesResult
    object NoVacancies : SearchVacanciesResult
    object ServerError : SearchVacanciesResult
}
