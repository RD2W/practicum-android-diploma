package ru.practicum.android.diploma.search.data.model.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val address: AddressDto,
    val area: AreaDto,
    val salary: SalaryDto,
    val employer: EmployerDto,
)
