package ru.practicum.android.diploma.search.data.source.remote

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.model.dto.AreaDto
import ru.practicum.android.diploma.search.data.model.dto.CountryDto
import ru.practicum.android.diploma.search.data.model.dto.IndustryDto
import ru.practicum.android.diploma.search.data.model.dto.VacancyDetailsDto
import ru.practicum.android.diploma.search.data.model.dto.VacancyDto

sealed class HHApiResponse {

    var responseCode: Int = 0

    data class Vacancies(
        val items: List<VacancyDto>, // Список вакансий
        val found: Int, // Найдено результатов
        val page: Int, // Номер страницы
        val pages: Int, // Всего страниц
        @SerializedName("per_page")
        val perPage: Int, // Результатов на странице
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
