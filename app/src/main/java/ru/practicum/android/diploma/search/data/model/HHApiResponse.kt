package ru.practicum.android.diploma.search.data.model

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.model.dto.AddressDto
import ru.practicum.android.diploma.search.data.model.dto.AreaDto
import ru.practicum.android.diploma.search.data.model.dto.EmployerDto
import ru.practicum.android.diploma.search.data.model.dto.EmploymentDto
import ru.practicum.android.diploma.search.data.model.dto.ExperienceDto
import ru.practicum.android.diploma.search.data.model.dto.IndustryDto
import ru.practicum.android.diploma.search.data.model.dto.SalaryDto
import ru.practicum.android.diploma.search.data.model.dto.SkillDto
import ru.practicum.android.diploma.search.data.model.dto.VacancyDto
import ru.practicum.android.diploma.search.data.model.dto.WorkFormatDto

sealed class HHApiResponse {

    var responseCode: Int = 0

    data class Vacancies(
        val items: List<VacancyDto>, // Список вакансий
        val found: Int, // Найдено результатов
        val page: Int, // Номер страницы
        val pages: Int, // Всего страниц
    ) : HHApiResponse()

    data class VacancyDetails(
        val id: String, // Идентификатор вакансии
        val area: AreaDto, // Регион
        val address: AddressDto? = null, // Адрес
        val description: String, // Описание в html
        val employer: EmployerDto? = null, // Поля работодателя в вакансии
        @SerializedName("employment_form")
        val employmentForm: EmploymentDto? = null, // Тип занятости
        val experience: ExperienceDto? = null, // Опыт работы
        @SerializedName("key_skills")
        val keySkills: List<SkillDto>? = null, // Список ключевых навыков
        val name: String, // Название
        @SerializedName("salary_range")
        val salaryRange: SalaryDto? = null, // Зарплата
        @SerializedName("work_format")
        val workFormat: List<WorkFormatDto?> = emptyList(), // Формат работы
        @SerializedName("alternate_url")
        val alternateUrl: String? = null, // Ссылка на представление вакансии на сайте
    ) : HHApiResponse()

    data class Countries(
        val countries: List<AreaDto>
    ) : HHApiResponse()

    data class Areas(
        val chosenArea: AreaDto
    ) : HHApiResponse()

    data class Industries(
        val industries: List<IndustryDto>
    ) : HHApiResponse()

    data class BadResponse(val errorMessage: String = "") : HHApiResponse()

}
