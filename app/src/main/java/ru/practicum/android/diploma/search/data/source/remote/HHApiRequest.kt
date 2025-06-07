package ru.practicum.android.diploma.search.data.source.remote

sealed interface HHApiRequest {

    data class Vacancies(val options: Map<String, String>) : HHApiRequest
    data class VacancyDetails(val id: String) : HHApiRequest
    data object Countries : HHApiRequest
    data class Areas(val id: String) : HHApiRequest
    data object Industries : HHApiRequest

}
