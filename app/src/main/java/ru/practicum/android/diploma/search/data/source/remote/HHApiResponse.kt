package ru.practicum.android.diploma.search.data.source.remote

import ru.practicum.android.diploma.search.data.model.dto.AreaDto
import ru.practicum.android.diploma.search.data.model.dto.CountryDto
import ru.practicum.android.diploma.search.data.model.dto.IndustryDto
import ru.practicum.android.diploma.search.data.model.dto.SearchResultDto
import ru.practicum.android.diploma.search.data.model.dto.VacancyDetailsDto

sealed class HHApiResponse {

    var responseCode: Int = 0

    data class Vacancies(
        val searchResult: SearchResultDto
    ) : HHApiResponse()

    data class VacancyDetails(
        val vacancyDetails: VacancyDetailsDto
    ) : HHApiResponse()

    data class Countries(
        val countries: List<CountryDto>
    ) : HHApiResponse()

    data class Areas(
        val areas: List<AreaDto>
    ) : HHApiResponse()

    data class Industries(
        val industries: List<IndustryDto>
    ) : HHApiResponse()

    data class BadResponse(val errorMessage: String = "") : HHApiResponse()

}
