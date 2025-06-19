package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.common.constants.AppConstants.PAGE_SIZE
import ru.practicum.android.diploma.search.domain.model.SearchParameters

fun SearchParameters.toMap(): Map<String, String> {
    return buildMap {
        put(AppConstants.SEARCH_QUERY_KEY, query)
        put(AppConstants.SEARCH_PAGE_KEY, page.toString())
        put(AppConstants.SEARCH_PER_PAGE_KEY, PAGE_SIZE.toString())
        put(AppConstants.SEARCH_AREA_KEY, filter?.workplace?.areaId.toString())
        put(AppConstants.SEARCH_INDUSTRY_KEY, filter?.industry?.id.toString())
        put(AppConstants.SEARCH_SALARY_KEY, filter?.salary?.from.toString())
        put(AppConstants.SEARCH_CURRENCY_KEY, filter?.salary?.currency.toString())
        put(AppConstants.SEARCH_ONLY_WITH_SALARY_KEY, filter?.salaryMustHaveFlag.toString())
    }
}
