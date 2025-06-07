package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetailsDto(
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
)
