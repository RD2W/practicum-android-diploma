package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.common.domain.model.Vacancy

sealed interface SearchVacanciesResult {
    data class Success(val vacancies: List<Vacancy>) : SearchVacanciesResult
    data class NetworkError(val message: String) : SearchVacanciesResult
    data class BadRequest(val message: String) : SearchVacanciesResult
    data class Unauthorized(val message: String) : SearchVacanciesResult
    data class Forbidden(val message: String) : SearchVacanciesResult
    data class ServerError(val message: String) : SearchVacanciesResult
    object NoVacancies : SearchVacanciesResult
}
