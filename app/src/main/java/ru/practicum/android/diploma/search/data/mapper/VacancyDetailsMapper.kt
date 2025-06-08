package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.domain.model.VacancyDetails
import ru.practicum.android.diploma.common.utils.FormatStrings
import ru.practicum.android.diploma.search.data.model.dto.VacancyDetailsDto

class VacancyDetailsMapper {

    fun toDomain(dto: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            id = dto.id,
            titleOfVacancy = dto.name,
            regionName = dto.area.name,
            salary = FormatStrings.formatSalary(dto.salaryRange),
            employerName = dto.employer?.name.orEmpty(),
            employerLogoUrl = dto.employer?.logoUrls?.size90,
            experience = dto.experience?.name,
            employmentType = dto.employmentForm?.name,
            scheduleType = dto.workFormat?.name,
            keySkills = dto.keySkills?.joinToString(", "),
            alternateUrl = dto.alternateUrl
        )
    }
}
