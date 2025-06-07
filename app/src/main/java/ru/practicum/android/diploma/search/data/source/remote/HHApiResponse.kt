package ru.practicum.android.diploma.search.data.source.remote

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.model.dto.AddressDto
import ru.practicum.android.diploma.search.data.model.dto.AreaDto
import ru.practicum.android.diploma.search.data.model.dto.CountryDto
import ru.practicum.android.diploma.search.data.model.dto.EmployerDto
import ru.practicum.android.diploma.search.data.model.dto.EmploymentDto
import ru.practicum.android.diploma.search.data.model.dto.ExperienceDto
import ru.practicum.android.diploma.search.data.model.dto.IndustryDto
import ru.practicum.android.diploma.search.data.model.dto.SalaryDto
import ru.practicum.android.diploma.search.data.model.dto.VacancyDto
import ru.practicum.android.diploma.search.data.model.dto.WorkFormatDto

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
        val id: String,
        val area: AreaDto,
        val address: AddressDto? = null,
        val description: String,
        val employer: EmployerDto? = null,
        @SerializedName("employment_form")
        val employmentForm: EmploymentDto? = null, // Тип занятости
        val experience: ExperienceDto? = null,
        @SerializedName("key_skills")
        val keySkills: List<String>? = null,
        val name: String,
        @SerializedName("salary_range")
        val salaryRange: SalaryDto? = null,
        @SerializedName("work_format")
        val workFormat: WorkFormatDto? = null, // Формат работы
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
