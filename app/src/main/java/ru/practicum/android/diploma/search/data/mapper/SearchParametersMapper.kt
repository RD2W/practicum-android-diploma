package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.common.constants.AppConstants.PAGE_SIZE
import ru.practicum.android.diploma.search.domain.model.SearchParameters

fun SearchParameters.toMap(): Map<String, String> {
    val area = filter?.workplace?.areaId
    val industry = filter?.industry?.id
    val salary = filter?.salary?.from
    val currency = filter?.salary?.currency
    val onlyWithSalary = filter?.salaryMustHaveFlag
    return buildMap {
        put(AppConstants.SEARCH_QUERY_KEY, query)
        put(AppConstants.SEARCH_PAGE_KEY, page.toString())
        put(AppConstants.SEARCH_PER_PAGE_KEY, PAGE_SIZE.toString())

        area?.let { put(AppConstants.SEARCH_AREA_KEY, area) }
        industry?.let { put(AppConstants.SEARCH_INDUSTRY_KEY, industry) }
        salary?.let { put(AppConstants.SEARCH_SALARY_KEY, salary.toString()) }
        currency?.let { put(AppConstants.SEARCH_CURRENCY_KEY, currency) }
        onlyWithSalary?.let { put(AppConstants.SEARCH_ONLY_WITH_SALARY_KEY, onlyWithSalary.toString()) }
    }
}
