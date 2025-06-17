package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.utils.FormatStrings
import ru.practicum.android.diploma.search.data.model.dto.VacancyDto

fun VacancyDto.toVacancy() = Vacancy(
    id = this.id,
    titleOfVacancy = this.name,
    regionName = this.area?.name,
    salary = FormatStrings.formatSalary(this.salary),
    employerName = this.employer.name,
    employerLogoUrl = this.employer.logoUrls?.original
)
