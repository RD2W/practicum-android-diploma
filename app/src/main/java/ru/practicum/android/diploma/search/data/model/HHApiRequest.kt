package ru.practicum.android.diploma.search.data.model

sealed interface HHApiRequest {

    data class Vacancies(val options: Map<String, String>) : HHApiRequest
    data class VacancyDetails(val id: String) : HHApiRequest
    data object Countries : HHApiRequest
    data class Areas(val id: String) : HHApiRequest
    data object Industries : HHApiRequest

}
