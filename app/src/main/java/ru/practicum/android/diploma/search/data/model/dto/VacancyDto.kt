package ru.practicum.android.diploma.search.data.model.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val address: AddressDto? = null,
    val area: VacancyAreaDto? = null,
    val salary: SalaryDto? = null,
    val employer: EmployerDto,
)
