package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.utils.FormatStrings
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

fun HHApiResponse.VacancyDetails.toVacancyDetails() = VacancyDetails(
    id = this.id,
    titleOfVacancy = this.name,
    regionName = this.area.name,
    salary = FormatStrings.formatSalary(this.salaryRange),
    employerName = this.employer?.name.orEmpty(),
    employerLogoUrl = this.employer?.logoUrls?.size90,
    experience = this.experience?.name,
    employmentType = this.employmentForm?.name,
    scheduleType = this.workFormat.firstOrNull()?.name,
    keySkills = this.keySkills?.joinToString(", "),
    description = this.description,
    alternateUrl = this.alternateUrl,
)
