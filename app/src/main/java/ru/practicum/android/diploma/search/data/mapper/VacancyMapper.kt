package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.utils.FormatStrings
import ru.practicum.android.diploma.search.data.model.dto.VacancyDto

class VacancyMapper {
    fun toDomain(dto: VacancyDto): Vacancy {
        return Vacancy(
            id = dto.id,
            titleOfVacancy = dto.name,
            regionName = dto.area.name,
            salary = FormatStrings.formatSalary(dto.salary),
            employerName = dto.employer.name,
            employerLogoUrl = dto.employer.logoUrls?.size90,
        )
    }
}
