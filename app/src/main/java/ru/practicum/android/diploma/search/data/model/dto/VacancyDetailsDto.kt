package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetailsDto(
    val id: String, // Идентификатор вакансии
    val area: AreaDto,  // Регион
    val address: AddressDto? = null,    // Адрес
    val description: String,    // Описание в html
    val employer: EmployerDto? = null,  // Поля работодателя в вакансии
    @SerializedName("employment_form")
    val employmentForm: EmploymentDto? = null, // Тип занятости
    val experience: ExperienceDto? = null,  // Опыт работы
    @SerializedName("key_skills")
    val keySkills: List<String>? = null,    // Список ключевых навыков
    val name: String,   // Название
    @SerializedName("salary_range")
    val salaryRange: SalaryDto? = null, // Зарплата
    @SerializedName("work_format")
    val workFormat: WorkFormatDto? = null, // Формат работы
    @SerializedName("alternate_url")
    val alternateUrl: String? = null,   // Ссылка на представление вакансии на сайте
)
