package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.constants.AppConstants.PAGE_SIZE
import ru.practicum.android.diploma.common.constants.AppConstants.SEARCH_AREA_KEY
import ru.practicum.android.diploma.common.constants.AppConstants.SEARCH_CURRENCY_KEY
import ru.practicum.android.diploma.common.constants.AppConstants.SEARCH_INDUSTRY_KEY
import ru.practicum.android.diploma.common.constants.AppConstants.SEARCH_ONLY_WITH_SALARY_KEY
import ru.practicum.android.diploma.common.constants.AppConstants.SEARCH_SALARY_KEY
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.utils.FormatStrings
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.model.dto.VacancyDto
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

private const val TEXT_KEY = "text"
private const val PAGE_KEY = "page"
private const val PER_PAGE_KEY = "per_page"

fun SearchParameters.toMap(): Map<String, String> {
    val area = filter?.workplace?.areaId
    val industry = filter?.industry?.id
    val salary = filter?.salary?.from
    val currency = filter?.salary?.currency
    val onlyWithSalary = filter?.onlyWithSalary
    return buildMap {
        put(TEXT_KEY, query)
        put(PAGE_KEY, page.toString())
        put(PER_PAGE_KEY, PAGE_SIZE.toString())
        area?.let { put(SEARCH_AREA_KEY, area) }
        industry?.let { put(SEARCH_INDUSTRY_KEY, industry) }
        salary?.let { put(SEARCH_SALARY_KEY, salary.toString()) }
        currency?.let { put(SEARCH_CURRENCY_KEY, currency) }
        onlyWithSalary?.let { put(SEARCH_ONLY_WITH_SALARY_KEY, onlyWithSalary.toString()) }
    }
}

fun HHApiResponse.Vacancies.toSearchResult() = SearchResult(
    resultsFound = this.found,
    totalPages = this.pages,
    currentPage = this.page,
    vacancies = this.items.map { it.toVacancy() },
)

fun HHApiResponse.VacancyDetails.toVacancyDetails() = VacancyDetails(
    id = this.id,
    titleOfVacancy = this.name,
    regionName = this.area.name,
    address = this.address?.let { addressDto ->
        buildString {
            append(addressDto.city)
            addressDto.street?.let { append(", $it") }
            addressDto.building?.let { append(", $it") }
        }
    },
    salary = FormatStrings.formatSalary(this.salaryRange),
    employerName = this.employer?.name.orEmpty(),
    employerLogoUrl = this.employer?.logoUrls?.size90,
    experience = this.experience?.name,
    employmentType = this.employmentForm?.name,
    scheduleType = this.workFormat.firstOrNull()?.name,
    keySkills = this.keySkills?.joinToString(separator = ", ") { it.name },
    description = this.description,
    alternateUrl = this.alternateUrl,
)

fun VacancyDto.toVacancy() = Vacancy(
    id = this.id,
    titleOfVacancy = this.name,
    regionName = this.area?.name,
    salary = FormatStrings.formatSalary(this.salary),
    employerName = this.employer.name,
    employerLogoUrl = this.employer.logoUrls?.original
)
